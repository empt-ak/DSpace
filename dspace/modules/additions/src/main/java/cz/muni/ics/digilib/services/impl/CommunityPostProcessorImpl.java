/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.digilib.services.impl;

import cz.muni.ics.digilib.domain.Periodical;
import cz.muni.ics.digilib.domain.Volume;
import cz.muni.ics.dspace5.comparators.ComparatorFactory;
import cz.muni.ics.dspace5.core.MetadatumFactory;
import cz.muni.ics.dspace5.core.ObjectMapper;
import cz.muni.ics.dspace5.core.ObjectWrapper;
import cz.muni.ics.dspace5.core.post.CommunityPostProcessor;
import cz.muni.ics.dspace5.impl.MetadataWrapper;
import cz.muni.ics.dspace5.impl.io.FolderProvider;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import org.apache.log4j.Logger;
import org.dozer.Mapper;
import org.dspace.authorize.AuthorizeException;
import org.dspace.content.Community;
import org.dspace.content.Metadatum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Component
public class CommunityPostProcessorImpl implements CommunityPostProcessor
{

    private static final Logger logger = Logger.getLogger(CommunityPostProcessorImpl.class);
    private static final String COVER_FILENAME = "cover_thumb.png";
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private Mapper mapper;
    @Autowired
    private MetadatumFactory metadatumFactory;
    @Autowired
    private FolderProvider folderProvider;
    @Autowired
    private ComparatorFactory comparatorFactory;

    @Override
    public List<Metadatum> processMetadata(ObjectWrapper objectWrapper, List<ObjectWrapper> parents) throws IllegalArgumentException
    {
        //@meditor: need for same resolver which will decide what will be converted
        //based on path
        boolean isTopCommunity = (parents == null || parents.isEmpty());

        MetadataWrapper metadataWrapper = new MetadataWrapper();

        if (isTopCommunity)
        {
            Periodical p = null;
            try
            {
                p = objectMapper.convertPathToObject(objectWrapper.getPath(), "detail.xml");
            }
            catch (FileNotFoundException ex)
            {
                logger.error(ex, ex.getCause());
            }
            if (p != null)
            {
                mapper.map(p, metadataWrapper);
            }
            else
            {
                logger.info("Object at path [" + objectWrapper.getPath() + "] was not found");
                metadataWrapper.getMetadata().add(metadatumFactory.createMetadatum("dc", "title", null, null, "NO-TITLE"));
            }
        }
        else
        {
            Volume v = null;
            try
            {
                v = objectMapper.convertPathToObject(objectWrapper.getPath(), "");
            }
            catch (FileNotFoundException nfe)
            {
                logger.error(nfe, nfe.getCause());
            }

            if (v != null)
            {
                mapper.map(v, metadataWrapper);
            }
            else
            {
                logger.info("Object at path [" + objectWrapper.getPath() + "] was not found");
                metadataWrapper.getMetadata().add(metadatumFactory.createMetadatum("dc", "title", null, null, "NO-TITLE"));
            }
        }

        return metadataWrapper.getMetadata();
    }

    @Override
    public void processCommunity(ObjectWrapper objectWrapper, Community community) throws IllegalArgumentException
    {
        Path coverPath = null;
        if (objectWrapper.getLevel().equals(ObjectWrapper.LEVEL.COM))
        {
            coverPath = objectWrapper.getPath();
        }
        else if (objectWrapper.getLevel().equals(ObjectWrapper.LEVEL.SUBCOM))
        {
            // this is the case of volume
            List<Path> issues = folderProvider.getIssuesFromPath(objectWrapper.getPath());

            if (!issues.isEmpty())
            {
                Collections.sort(issues, comparatorFactory.provideIssuePathComparator());

                coverPath = issues.get(0);
            }
            else
            {
                // this should not occur since if volume exists, then at least one 
                // issue exists
                logger.error("There are no issues for given volume.");
            }
        }
        else
        {
            throw new IllegalArgumentException("Given objectWrapper does not have supported level [COM/SUBCOM], but was [" + objectWrapper.getLevel() + "]");
        }

        if (coverPath != null)
        {
            coverPath = coverPath.resolve(COVER_FILENAME);
            try
            {
                setCover(coverPath, community);
            }
            catch (IllegalArgumentException iax)
            {
                logger.info("For handle@" + objectWrapper.getHandle() + iax.getMessage());
            }
        }
    }

    /**
     * Sets cover for given community. Cover is specified by {@code coverPath}.
     *
     * @param coverPath path to cover file.
     * @param community target community for which we are setting the cover
     *
     * @throws IllegalArgumentException if cover does not exist on given
     *                                  {@code coverPath}
     */
    private void setCover(Path coverPath, Community community) throws IllegalArgumentException
    {
        if (Files.exists(coverPath))
        {
            try (FileInputStream fis = new FileInputStream(coverPath.toFile()))
            {
                community.setLogo(fis);
            }
            catch (IOException | AuthorizeException | SQLException ex)
            {
                logger.error(ex, ex.getCause());
            }
        }
        else
        {
            throw new IllegalArgumentException("Cover was not found @path [" + coverPath + "]");
        }
    }
}
