/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.imports;

import cz.muni.ics.dspace5.core.CommandLineService;
import cz.muni.ics.dspace5.core.HandleService;
import cz.muni.ics.dspace5.core.ImportService;
import cz.muni.ics.dspace5.core.ObjectWrapper;
import cz.muni.ics.dspace5.impl.DSpaceTools;
import cz.muni.ics.dspace5.impl.InputArguments;
import cz.muni.ics.dspace5.impl.ObjectWrapperFactory;
import cz.muni.ics.dspace5.impl.io.FolderProvider;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;
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
    private Context context;
    
    //awires
    @Autowired
    private ObjectWrapperFactory objectWrapperFactory;
    @Autowired
    private CommandLineService commandLineService;    
    @Autowired
    private ImportCommunity importCommunity;
    @Autowired
    private ImportCollection importCollection;
    @Autowired
    private ImportItem importItem;
    @Autowired
    private InputArguments inputArguments;
    @Autowired
    private DSpaceTools dspaceTools;
    @Autowired
    private FolderProvider fileProvider;
    @Autowired
    private HandleService handleService;

    @Override
    public void execute(String[] args)
    {
        boolean error = false;

        try
        {
            commandLineService.parseInput(args, CommandLineService.Mode.IMPORT);
        }
        catch (ParseException pe)
        {
            error = true;
        }

        if (!error)
        {
            // this will be always null until i find out
            // how to call synchronized method in spring
            if (context == null)
            {
                try
                {
                    context = new Context();
                }
                catch (SQLException ex)
                {
                    logger.info(ex, ex.getCause());
                }
            }
            context.turnOffAuthorisationSystem();
            
            ObjectWrapper ow = objectWrapperFactory.createObjectWrapper();
            ow.setPath(inputArguments.getTypedValue("path", Path.class));
            
            resolveObjectWrapper(ow, true);
            
            
            context.restoreAuthSystemState();
            try
            {
                context.commit();
                context.complete();
            }
            catch (SQLException ex)
            {
                logger.error(ex, ex.getCause());
            }
        }
    }

    /**
     * Depth first search for recreating tree structure of root import object.
     *
     * @param objectWrapper target object of which children (in directory tree
     *                      structure) we would like to retrieve
     */
    private void resolveObjectWrapper(ObjectWrapper objectWrapper, boolean mainCall)
    {           
        int level = dspaceTools.getPathLevel(objectWrapper.getPath());
        boolean updateMode = inputArguments.getValue("mode").equals("update"); 
        
        if(level == 0)
        {
            objectWrapper.setHandle(handleService.getHandleForPath(objectWrapper.getPath(), context));
            //special handling of top comm
            objectWrapper.setLevel(ObjectWrapper.LEVEL.COM);
            
            logger.info("@level "+level+" @path ["+objectWrapper.getPath()+"] resolved as "+ObjectWrapper.LEVEL.COM+" with handle @"+objectWrapper.getHandle());
            
            if(updateMode)
            {
                List<Path> paths = fileProvider.getFoldersFromPath(objectWrapper.getPath());
                List<ObjectWrapper> issues = new ArrayList<>(paths.size());
                SortedSet<ObjectWrapper> volumes = new TreeSet<>();

                for(Path p : paths)
                {
                    ObjectWrapper issue = objectWrapperFactory.createObjectWrapper(p,
                            false,
                            handleService.getHandleForPath(objectWrapper.getPath(), context));
                    // recreate articles
                    resolveObjectWrapper(issue, false);

                    issues.add(issue);

                    ObjectWrapper volume = objectWrapperFactory.createObjectWrapper(p,
                            true,
                            handleService.getVolumeHandle(p, context));
                    volumes.add(volume);
                }
                
                for(ObjectWrapper volume : volumes)
                {
                    logger.info("Mapping volume "+volume.getPath()+" @handle ["+objectWrapper.getHandle()+"]");
                    List<ObjectWrapper> volumeIssues = new ArrayList<>();
                    
                    
                    String volumeNumber = StringUtils.substringBefore(volume.getPath().getFileName().toString(), ".xml");
                    
                    for(ObjectWrapper issue : issues)
                    {
                        if(dspaceTools.getVolumeNumber(issue.getPath()).equals(volumeNumber))
                        {
                            logger.info("Issue ["+issue.getPath()+"] belongs to volume "+volumeNumber);
                            volumeIssues.add(issue);
                        }
                    }
                    
                    volume.setChildren(volumeIssues);
                }
                
                //resolve volume to issue
                
                objectWrapper.setChildren(new ArrayList<>(volumes));
            }            
        }
        else if(level == 1)
        {
            if(mainCall)
            {
                // this means we called this from {#execute} method therefire we 
                // have to recreate Volume aswell.
                // TODO
                // from given object we create volume by redefining path and handle
                // and add children as issue
            }
            else
            {
                
                logger.info("@level "+level+" @path ["+objectWrapper.getPath()+"] resolved as "+ObjectWrapper.LEVEL.COL+" with handle @"+objectWrapper.getHandle());
                
                if(updateMode)
                {
                    List<Path> paths = fileProvider.getFoldersFromPath(objectWrapper.getPath());
                    List<ObjectWrapper> articles = new ArrayList<>(paths.size());

                    for(Path p : paths)
                    {
                        ObjectWrapper article = objectWrapperFactory.createObjectWrapper(p,
                                false,
                                handleService.getHandleForPath(objectWrapper.getPath(), context));

                        resolveObjectWrapper(article, false);

                        articles.add(article);
                    }

                    objectWrapper.setChildren(articles);
                }                
            }
        }
        else if(level == 2)
        {
            if(mainCall)
            {
                //TODO
            }
            
            logger.info("@level "+level+" @path ["+objectWrapper.getPath()+"] resolved as "+ObjectWrapper.LEVEL.ITEM+" with handle @"+objectWrapper.getHandle());
        }
    }
}
