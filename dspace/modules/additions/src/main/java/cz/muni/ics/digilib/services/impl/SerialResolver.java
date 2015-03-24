/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.digilib.services.impl;

import cz.muni.ics.dspace5.core.HandleService;
import cz.muni.ics.dspace5.core.ObjectWrapper;
import cz.muni.ics.dspace5.core.ObjectWrapperResolver;
import cz.muni.ics.dspace5.impl.DSpaceTools;
import cz.muni.ics.dspace5.impl.InputArguments;
import cz.muni.ics.dspace5.impl.ObjectWrapperFactory;
import cz.muni.ics.dspace5.impl.io.FolderProvider;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class SerialResolver implements ObjectWrapperResolver
{
    private static final Logger logger = Logger.getLogger(SerialResolver.class);
    @Autowired
    private DSpaceTools dspaceTools;
    @Autowired
    private InputArguments inputArguments;
    @Autowired
    private HandleService handleService;
    @Autowired
    private FolderProvider folderProvider;
    @Autowired
    private ObjectWrapperFactory objectWrapperFactory;

    @Override
    public void resolveObjectWrapper(ObjectWrapper objectWrapper, boolean mainCall)
    {
        int level = dspaceTools.getPathLevel(objectWrapper.getPath());
        boolean updateMode = inputArguments.getValue("mode").equals("update"); 
        
        if(level == 0)
        {
            objectWrapper.setHandle(handleService.getHandleForPath(objectWrapper.getPath(), true));
            //special handling of top comm
            objectWrapper.setLevel(ObjectWrapper.LEVEL.COM);
            
            logger.debug("@level "+level+" @path ["+objectWrapper.getPath()+"] resolved as "+ObjectWrapper.LEVEL.COM+" with handle @"+objectWrapper.getHandle());
            
            if(updateMode)
            {
                List<Path> paths = folderProvider.getFoldersFromPath(objectWrapper.getPath());
                List<ObjectWrapper> issues = new ArrayList<>(paths.size());
                SortedSet<ObjectWrapper> volumes = new TreeSet<>();

                for(Path p : paths)
                {
                    ObjectWrapper issue = objectWrapperFactory.createObjectWrapper(p,
                            false,
                            handleService.getHandleForPath(p, true));
                    // recreate articles
                    resolveObjectWrapper(issue, false);

                    issues.add(issue);

                    ObjectWrapper volume = objectWrapperFactory.createObjectWrapper(p,
                            true,
                            handleService.getVolumeHandle(p));
                    volumes.add(volume);
                }
                
                for(ObjectWrapper volume : volumes)
                {
                    logger.debug("Mapping volume "+volume.getPath()+" @handle ["+volume.getHandle()+"]");
                    List<ObjectWrapper> volumeIssues = new ArrayList<>();
                    
                    
                    String volumeNumber = StringUtils.substringBefore(volume.getPath().getFileName().toString(), ".xml");
                    
                    for(ObjectWrapper issue : issues)
                    {
                        if(dspaceTools.getVolumeNumber(issue.getPath()).equals(volumeNumber))
                        {
                            logger.debug("Issue ["+issue.getPath()+"] belongs to volume "+volumeNumber);
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
                
                logger.debug("@level "+level+" @path ["+objectWrapper.getPath()+"] resolved as "+ObjectWrapper.LEVEL.COL+" with handle @"+objectWrapper.getHandle());
                
                if(updateMode)
                {
                    List<Path> paths = folderProvider.getFoldersFromPath(objectWrapper.getPath());
                    List<ObjectWrapper> articles = new ArrayList<>(paths.size());

                    for(Path p : paths)
                    {
                        ObjectWrapper article = objectWrapperFactory.createObjectWrapper(p,
                                false,
                                handleService.getHandleForPath(p, true));

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
            
            logger.debug("@level "+level+" @path ["+objectWrapper.getPath()+"] resolved as "+ObjectWrapper.LEVEL.ITEM+" with handle @"+objectWrapper.getHandle());
        }
    }
    
}
