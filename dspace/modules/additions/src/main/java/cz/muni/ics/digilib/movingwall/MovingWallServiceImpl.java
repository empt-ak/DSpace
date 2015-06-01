/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.digilib.movingwall;

import cz.muni.ics.dspace5.api.CommandLineService;
import cz.muni.ics.dspace5.api.ModuleManager;
import cz.muni.ics.dspace5.api.ObjectWrapper;
import cz.muni.ics.dspace5.impl.ContextWrapper;
import cz.muni.ics.dspace5.impl.DSpaceTools;
import cz.muni.ics.dspace5.impl.ImportDataMap;
import cz.muni.ics.dspace5.impl.ObjectWrapperFactory;
import cz.muni.ics.dspace5.imports.ImportCommunity;
import cz.muni.ics.dspace5.movingwall.MovingWallService;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.Logger;
import org.dspace.core.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Component(value = "mowingWallService")
public class MovingWallServiceImpl implements MovingWallService
{
    private static final Logger logger = Logger.getLogger(MovingWallServiceImpl.class);
    @Autowired
    private CommandLineService commandLineService;
    @Autowired
    private ImportDataMap importDataMap;
    @Autowired
    private ContextWrapper contextWrapper;
    @Autowired
    private DSpaceTools dSpaceTools;
    @Autowired
    private ObjectWrapperFactory objectWrapperFactory;
    @Autowired
    private ImportCommunity importCommunity;
    
    @Autowired
    private ModuleManager modulManager;
    
    @Override
    public void execute(String[] args)
    {
        // just follow the same scheme as is for importService.
        // TODO is it good approach ?
        // TODO check if it even works
        try
        {
            commandLineService.getCommandLine("movingwall").process(args);
        }
        catch(ParseException pe)
        {
            //
        }
        
        try
        {
            contextWrapper.setContext(new Context());
            contextWrapper.getContext().setCurrentUser(dSpaceTools.findEPerson(importDataMap.getValue("user")));
        }
        catch(SQLException ex)
        {
            logger.info(ex, ex.getCause());
        }
        contextWrapper.getContext().turnOffAuthorisationSystem();
        ObjectWrapper node = objectWrapperFactory.createObjectWrapper();
        node.setPath(importDataMap.getTypedValue("path", Path.class));
        
        ObjectWrapper realImport = null;
        
        try
        {
            realImport = modulManager.getModule(node.getPath()).getObjectWrapperResolver().resolveObjectWrapper(node, true);
        }
        catch(FileNotFoundException nfe)
        {
            logger.error(nfe);
        }
        if(realImport != null)
        {
            importCommunity.importToDspace(realImport, new ArrayList<ObjectWrapper>());
        }
        
//        contextWrapper.getContext().restoreAuthSystemState();
//        contextWrapper.setContext(null);
    }  
}
