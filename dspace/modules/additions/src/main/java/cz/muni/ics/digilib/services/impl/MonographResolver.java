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
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class MonographResolver implements ObjectWrapperResolver
{

    private static final Logger logger = Logger.getLogger(MonographResolver.class);
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
    public ObjectWrapper resolveObjectWrapper(ObjectWrapper objectWrapper, boolean mainCall)
    {
        int level = dspaceTools.getPathLevel(objectWrapper.getPath());
        boolean updateMode = inputArguments.getValue("mode").equals("update");
        ObjectWrapper topLevelResult = null;

        if (level == 0)
        {
            objectWrapper.setHandle(handleService.getHandleForPath(objectWrapper.getPath(), true));
            objectWrapper.setLevel(ObjectWrapper.LEVEL.COM);

            logger.debug("@level " + level + " @path [" + objectWrapper.getPath() + "] resolved as " + ObjectWrapper.LEVEL.COM + " with handle @" + objectWrapper.getHandle());

            if (updateMode)
            {
                List<Path> monographyPaths = folderProvider.getFoldersFromPath(objectWrapper.getPath());
                List<ObjectWrapper> monographies = new ArrayList<>(monographyPaths.size());

                for (Path monoPath : monographyPaths)
                {
                    ObjectWrapper monography = objectWrapperFactory
                            .createObjectWrapper(monoPath,
                                    false,
                                    handleService.getHandleForPath(monoPath, true));

                    resolveObjectWrapper(monography, false);

                    monographies.add(monography);
                }

                objectWrapper.setChildren(monographies);
            }
            topLevelResult = objectWrapper;
        }
        if (level == 1)
        {
            if (mainCall)
            {
                Path root = dspaceTools.getRoot(objectWrapper.getPath());
                objectWrapper.setLevel(ObjectWrapper.LEVEL.COL);
                objectWrapper.setHandle(handleService.getHandleForPath(objectWrapper.getPath(), true));
                
                ObjectWrapper rootObject  = objectWrapperFactory.createObjectWrapper(root, false, handleService.getHandleForPath(root, true));
                
                resolveObjectWrapper(objectWrapper, false);
                
                List<ObjectWrapper> monographies = new ArrayList<>(1);
                monographies.add(objectWrapper);
                
                rootObject.setChildren(monographies);
                
                topLevelResult = rootObject;
            }
            else
            {
                logger.debug("@level " + level + " @path [" + objectWrapper.getPath() + "] resolved as " + ObjectWrapper.LEVEL.COL + " with handle @" + objectWrapper.getHandle());

                if (updateMode)
                {
                    List<Path> monoChapterPaths = folderProvider.getFoldersFromPath(objectWrapper.getPath());
                    List<ObjectWrapper> monographyChapters = new ArrayList<>(monoChapterPaths.size());

                    for (Path monoPath : monoChapterPaths)
                    {
                        ObjectWrapper monographyChapter = objectWrapperFactory
                                .createObjectWrapper(monoPath,
                                        false,
                                        handleService.getHandleForPath(monoPath, true));

                        resolveObjectWrapper(monographyChapter, false);
                        monographyChapters.add(monographyChapter);
                    }
                    objectWrapper.setChildren(monographyChapters);
                }
            }
        }
        if (level == 2)
        {
            if (mainCall)
            {
                Path root = dspaceTools.getRoot(objectWrapper.getPath());
                Path monographyPath = dspaceTools.getIssue(objectWrapper.getPath());
                
                objectWrapper.setLevel(ObjectWrapper.LEVEL.ITEM);
                objectWrapper.setHandle(handleService.getHandleForPath(objectWrapper.getPath(), true));
                
                ObjectWrapper monoSeries = objectWrapperFactory.createObjectWrapper(root, false, handleService.getHandleForPath(root, true));
                ObjectWrapper mono = objectWrapperFactory.createObjectWrapper(monographyPath, false, handleService.getHandleForPath(monographyPath, true));
                
                List<ObjectWrapper> articles = new ArrayList<>(1);
                List<ObjectWrapper> monographies = new ArrayList<>(1);
                articles.add(objectWrapper);
                mono.setChildren(articles);
                monographies.add(mono);
                
                monoSeries.setChildren(monographies);
                
                topLevelResult = monoSeries;
            }

            logger.debug("@level " + level + " @path [" + objectWrapper.getPath() + "] resolved as " + ObjectWrapper.LEVEL.ITEM + " with handle @" + objectWrapper.getHandle());
        }

        return topLevelResult;
    }
}
