/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.imports;

import cz.muni.ics.dspace5.api.CommandLineService;
import cz.muni.ics.dspace5.api.ImportService;
import cz.muni.ics.dspace5.api.ModuleManager;
import cz.muni.ics.dspace5.api.ObjectWrapper;
import cz.muni.ics.dspace5.impl.ContextWrapper;
import cz.muni.ics.dspace5.impl.DSpaceTools;
import cz.muni.ics.dspace5.impl.ImportDataMap;
import cz.muni.ics.dspace5.impl.ObjectWrapperFactory;
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
@Component(value = "importService")
public class ImportServiceImpl implements ImportService
{

    private static final Logger logger = Logger.getLogger(ImportServiceImpl.class);    
    //awires
    @Autowired
    private ObjectWrapperFactory objectWrapperFactory;
    @Autowired
    private CommandLineService commandLineService;    
    @Autowired
    private ImportCommunity importCommunity;
    @Autowired
    private ImportDataMap importDataMap;
    @Autowired
    private ContextWrapper contextWrapper;
    @Autowired
    private DSpaceTools dSpaceTools;
    @Autowired
    private ModuleManager moduleManager;

    @Override
    public void execute(String[] args)
    {
        boolean error = false;

        try
        {
            commandLineService.getCommandLine("import").process(args);
        }
        catch (ParseException pe)
        {
            error = true;
        }

        if (!error)
        {
            try
            {
                contextWrapper.setContext(new Context());
                contextWrapper.getContext().setCurrentUser(dSpaceTools.findEPerson(importDataMap.getValue("user")));
            }
            catch (SQLException ex)
            {
                logger.info(ex, ex.getCause());
            }
            contextWrapper.getContext().turnOffAuthorisationSystem();
            
            ObjectWrapper importTarget = objectWrapperFactory.createObjectWrapper();
            importTarget.setPath(importDataMap.getTypedValue("path", Path.class));
            
            ObjectWrapper realImport = null;
            
            String moduleName = moduleManager.getModuleName(importTarget.getPath());          
            
            try
            {
                realImport = moduleManager.getModule(importTarget.getPath())
                        .getObjectWrapperResolver().resolveObjectWrapper(importTarget, true);                
            }
            catch(FileNotFoundException nfe)
            {
                logger.error(nfe);
            }
            
            if(realImport != null)
            {
                importCommunity.importToDspace(realImport, new ArrayList<ObjectWrapper>());
            }
//            try
//            {
                
//            }
//            catch(Exception e)
//            {
//                logger.fatal(e,e.getCause());
              //  contextWrapper.getContext().abort();
//                
//            }
//            finally
//            {
                try
                {
                    contextWrapper.getContext().complete();
                }
                catch (SQLException ex)
                {
                    logger.error(ex, ex.getCause());
                }
//            }
            
            
            contextWrapper.getContext().restoreAuthSystemState();
            contextWrapper.setContext(null);
        }
    }
}
