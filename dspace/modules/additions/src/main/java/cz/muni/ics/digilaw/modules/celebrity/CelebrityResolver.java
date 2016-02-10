/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.digilaw.modules.celebrity;

import cz.muni.ics.dspace5.api.HandleService;
import cz.muni.ics.dspace5.api.ObjectWrapperFactory;
import cz.muni.ics.dspace5.api.module.ObjectWrapper;
import cz.muni.ics.dspace5.api.module.ObjectWrapperResolver;
import cz.muni.ics.dspace5.impl.DSpaceTools;
import cz.muni.ics.dspace5.impl.InputDataMap;
import cz.muni.ics.dspace5.impl.io.FolderProvider;
import java.io.FileNotFoundException;
import java.nio.file.Files;
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
 * @author Vlastimil Krejcir - krejcir at ics.muni.cz
 */
public class CelebrityResolver implements ObjectWrapperResolver
{

    private static final Logger LOGGER = Logger.getLogger(CelebrityResolver.class);
    @Autowired
    private DSpaceTools dspaceTools;
    @Autowired
    private InputDataMap inputDataMap;
    @Autowired
    private HandleService handleService;
    @Autowired
    private FolderProvider folderProvider;
    @Autowired
    private ObjectWrapperFactory objectWrapperFactory;

    @Override
    public ObjectWrapper resolveObjectWrapper(ObjectWrapper objectWrapper, boolean mainCall) throws FileNotFoundException
    {
        int level = dspaceTools.getPathLevel(objectWrapper.getPath());
        boolean updateMode = inputDataMap.getValue("method").equals("update");
        
        if(inputDataMap.containsKey("precheck"))
        {
            if(!Files.exists(objectWrapper.getPath().resolve("detail.xml")))
            {
                throw new FileNotFoundException(objectWrapper.getPath().resolve("detail.xml")+ " is missing.");
            }
            
            if(!Files.exists(objectWrapper.getPath().resolve("meta.xml")))
            {
                throw new FileNotFoundException(objectWrapper.getPath().resolve("meta.xml") + " is missing.");
            }
        }
        
        ObjectWrapper topLevelResult = null;

        if (level == 0)
        {
            objectWrapper.setHandle(handleService.getHandleForPath(objectWrapper.getPath(), true));
            objectWrapper.setLevel(ObjectWrapper.LEVEL.COM);

            LOGGER.debug("@level " + level + " @path [" + objectWrapper.getPath() + "] resolved as " + ObjectWrapper.LEVEL.COM + " with handle @" + objectWrapper.getHandle());

            if (updateMode)
            {
                List<Path> monographyPaths = folderProvider.getFoldersFromPath(objectWrapper.getPath());
                List<ObjectWrapper> monographies = new ArrayList<>(monographyPaths.size());
								SortedSet<ObjectWrapper> workTypes = new TreeSet<>();

                for (Path monoPath : monographyPaths)
                {
                    ObjectWrapper monography = objectWrapperFactory.createObjectWrapper(monoPath, false, true, true);

                    resolveObjectWrapper(monography, false);

                    monographies.add(monography);

										ObjectWrapper workType = objectWrapperFactory.createObjectWrapper(monoPath, true, true, true);

										workTypes.add(workType);

                }

				for (ObjectWrapper workType : workTypes) {

					LOGGER.debug("Mapping celebrity work type " + workType.getPath() + " @handle [" + workType.getHandle() + "]");

					List<ObjectWrapper> workTypeMonographies = new ArrayList<>();

					String workTypeNumber = StringUtils.substringBefore(workType.getPath().getFileName().toString(), ".xml");

					for (ObjectWrapper monography : monographies) {

						if (dspaceTools.getVolumeNumber(monography.getPath()).equals(workTypeNumber)) {

							LOGGER.debug("Work [" + monography.getPath() + "] belongs to workType " + workTypeNumber);

							workTypeMonographies.add(monography);
						}
					}

					workType.setChildren(workTypeMonographies);
				}

				objectWrapper.setChildren(new ArrayList<>(workTypes));
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
                
                ObjectWrapper rootObject = objectWrapperFactory.createObjectWrapper(root, false, true, true);
                
								ObjectWrapper workType = objectWrapperFactory.createObjectWrapper(objectWrapper.getPath(), true, true, true);



                resolveObjectWrapper(objectWrapper, false);
                
                List<ObjectWrapper> monographies = new ArrayList<>(1);
                List<ObjectWrapper> workTypes = new ArrayList<>();
							

				monographies.add(objectWrapper);

				workType.setChildren(monographies);

				workTypes.add(workType);

                rootObject.setChildren(workTypes);
                
                topLevelResult = rootObject;
            }
            else
            {
                LOGGER.debug("@level " + level + " @path [" + objectWrapper.getPath() + "] resolved as " + ObjectWrapper.LEVEL.COL + " with handle @" + objectWrapper.getHandle());

                if (updateMode)
                {
                    List<ObjectWrapper> monographyChapter = new ArrayList<>(1);
                    ObjectWrapper fakeChapter = objectWrapperFactory.createRootObjectWrapper(objectWrapper.getPath());
                    fakeChapter.setLevel(ObjectWrapper.LEVEL.ITEM);
                    fakeChapter.setHandle(handleService.getHandleForPath(fakeChapter.getPath(), true, "fake_item_dspace_id"));
                    monographyChapter.add(fakeChapter);
                    objectWrapper.setChildren(monographyChapter);
//                    List<Path> monoChapterPaths = folderProvider.getFoldersFromPath(objectWrapper.getPath());
//                    List<ObjectWrapper> monographyChapters = new ArrayList<>(monoChapterPaths.size());
//
//                    for (Path monoPath : monoChapterPaths)
//                    {
//                        ObjectWrapper monographyChapter = objectWrapperFactory.createObjectWrapper(monoPath, false, true, true);
//
//                        resolveObjectWrapper(monographyChapter, false);
//                        monographyChapters.add(monographyChapter);
//                    }
//                    objectWrapper.setChildren(monographyChapters);
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
                
                ObjectWrapper monoSeries = objectWrapperFactory.createObjectWrapper(root, false, true, true);
								ObjectWrapper workType = objectWrapperFactory.createObjectWrapper(monographyPath, true, true, true);
                ObjectWrapper mono = objectWrapperFactory.createObjectWrapper(monographyPath, false, true, true);
                
                List<ObjectWrapper> articles = new ArrayList<>(1);
                List<ObjectWrapper> monographies = new ArrayList<>(1);
								List<ObjectWrapper> workTypes = new ArrayList<>();

                articles.add(objectWrapper);
                mono.setChildren(articles);
				monographies.add(mono);
				workType.setChildren(monographies);
				workTypes.add(workType);
                
                monoSeries.setChildren(workTypes);
                
                topLevelResult = monoSeries;
            }

            LOGGER.debug("@level " + level + " @path [" + objectWrapper.getPath() + "] resolved as " + ObjectWrapper.LEVEL.ITEM + " with handle @" + objectWrapper.getHandle());
        }

        return topLevelResult;
    }
}
