/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.digilib.movingwall;

import cz.muni.ics.dspace5.api.CommandLineService;
import cz.muni.ics.dspace5.api.ObjectWrapper;
import cz.muni.ics.dspace5.api.ObjectWrapperResolverFactory;
import cz.muni.ics.dspace5.exceptions.MovingWallException;
import cz.muni.ics.dspace5.impl.ContextWrapper;
import cz.muni.ics.dspace5.impl.DSpaceTools;
import cz.muni.ics.dspace5.impl.InputArguments;
import cz.muni.ics.dspace5.impl.ObjectWrapperFactory;
import cz.muni.ics.dspace5.movingwall.MWLockerProvider;
import cz.muni.ics.dspace5.movingwall.MovingWallService;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.Map;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.Logger;
import org.dspace.content.Bitstream;
import org.dspace.content.Collection;
import org.dspace.content.DSpaceObject;
import org.dspace.core.Constants;
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
    private MWLockerProvider mWLockerProvider;
    @Autowired
    private CommandLineService commandLineService;
    @Autowired
    private InputArguments inputArguments;
    @Autowired
    private ContextWrapper contextWrapper;
    @Autowired
    private DSpaceTools dSpaceTools;
    @Autowired
    private ObjectWrapperResolverFactory objectWrapperResolverFactory;
    @Autowired
    private ObjectWrapperFactory objectWrapperFactory;
//    @Autowired
//    private ImportCommunity importCommunity;
    
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
            contextWrapper.getContext().setCurrentUser(dSpaceTools.findEPerson(inputArguments.getValue("user")));
        }
        catch(SQLException ex)
        {
            logger.info(ex, ex.getCause());
        }
        contextWrapper.getContext().turnOffAuthorisationSystem();
        ObjectWrapper node = objectWrapperFactory.createObjectWrapper();
        node.setPath(inputArguments.getTypedValue("path", Path.class));
        
        ObjectWrapper realImport = objectWrapperResolverFactory.provideObjectWrapperResolver(node.getPath()).resolveObjectWrapper(node, true);
        
        // TODO causes circular dependency exception :)
       // importCommunity.importToDspace(realImport, new ArrayList<ObjectWrapper>(), new HashMap<String, Object>());
        
        contextWrapper.getContext().restoreAuthSystemState();
        contextWrapper.setContext(null);
    }

    @Override
    public void lock(DSpaceObject dSpaceObject, Map<String, Object> dataMap) throws IllegalArgumentException, MovingWallException
    {
        switch(dSpaceObject.getType())
        {
            case Constants.BITSTREAM:
                mWLockerProvider.getLocker(Bitstream.class).lockObject(dSpaceObject, dataMap);
                break;
            case Constants.COLLECTION:
                mWLockerProvider.getLocker(Collection.class).lockObject(dSpaceObject, dataMap);
                break;
            default:
                throw new IllegalArgumentException("Invalid type of dSpaceObject.");
        }
    }

    @Override
    public void unlock(DSpaceObject dSpaceObject, Map<String, Object> dataMap) throws IllegalArgumentException, MovingWallException
    {
        switch(dSpaceObject.getType())
        {
            case Constants.BITSTREAM:
                mWLockerProvider.getLocker(Bitstream.class).unlockObject(dSpaceObject, dataMap);
                break;
            case Constants.COLLECTION:
                mWLockerProvider.getLocker(Collection.class).unlockObject(dSpaceObject, dataMap);
                break;
            default:
                throw new IllegalArgumentException("Invalid type of dSpaceObject.");
        }
    }    
}
