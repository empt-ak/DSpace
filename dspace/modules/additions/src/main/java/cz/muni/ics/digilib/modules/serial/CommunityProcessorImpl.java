/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.digilib.modules.serial;

import cz.muni.ics.digilib.domain.Periodical;
import cz.muni.ics.digilib.domain.Volume;
import cz.muni.ics.digilib.movingwall.MovingWallFactoryBean;
import cz.muni.ics.dspace5.api.ObjectMapper;
import cz.muni.ics.dspace5.api.module.CommunityProcessor;
import cz.muni.ics.dspace5.api.module.ObjectWrapper;
import cz.muni.ics.dspace5.comparators.ComparatorFactory;
import cz.muni.ics.dspace5.exceptions.MovingWallException;
import cz.muni.ics.dspace5.impl.DSpaceTools;
import cz.muni.ics.dspace5.impl.io.FolderProvider;
import cz.muni.ics.dspace5.metadata.MetadataWrapper;
import cz.muni.ics.dspace5.metadata.MetadatumFactory;
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

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class CommunityProcessorImpl implements CommunityProcessor
{

    private static final Logger logger = Logger.getLogger(CommunityProcessorImpl.class);
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
    @Autowired
    private DSpaceTools dSpaceTools;
    @Autowired
    private MovingWallFactoryBean movingWallFactoryBean;

    private ObjectWrapper currentWrapper;
    private Periodical periodical;
    private Volume volume;

    @Override
    public void setup(ObjectWrapper objectWrapper) throws IllegalStateException, IllegalArgumentException
    {
        //TODO exceptions
        this.currentWrapper = objectWrapper;
        if (objectWrapper.getLevel().equals(ObjectWrapper.LEVEL.COM))
        {
            try
            {
                this.periodical = objectMapper.convertPathToObject(objectWrapper.getPath(), "detail.xml");
            }
            catch (FileNotFoundException ex)
            {
                logger.error(ex, ex.getCause());
            }
        }
        else if (currentWrapper.getLevel().equals(ObjectWrapper.LEVEL.SUBCOM))
        {
            try
            {
                this.volume = objectMapper.convertPathToObject(objectWrapper.getPath(), "");
            }
            catch (FileNotFoundException nfe)
            {
                logger.error(nfe, nfe.getCause());
            }
        }
        else
        {
            //TODO
            throw new IllegalArgumentException();
        }
    }

    @Override
    public List<Metadatum> processMetadata(List<ObjectWrapper> parents) throws IllegalArgumentException, IllegalStateException
    {
        MetadataWrapper metadataWrapper = new MetadataWrapper();
        if (this.periodical != null)
        {
            mapper.map(this.periodical, metadataWrapper);
        }
        else if (this.volume != null)
        {
            mapper.map(this.volume, metadataWrapper);
        }
        else
        {
            // TODO
            throw new IllegalStateException();
        }

        metadataWrapper.getMetadata().add(metadatumFactory.createMetadatum("muni", "mepath", null, null, dSpaceTools.getOnlyMEPath(currentWrapper.getPath()).toString()));

        return metadataWrapper.getMetadata();
    }

    @Override
    public void processCommunity(Community community, List<ObjectWrapper> parents) throws IllegalStateException, IllegalArgumentException
    {
        Path coverPath = null;
        if (currentWrapper.getLevel().equals(ObjectWrapper.LEVEL.COM))
        {
            coverPath = currentWrapper.getPath();
        }
        else if (currentWrapper.getLevel().equals(ObjectWrapper.LEVEL.SUBCOM))
        {
            // this is the case of volume
            List<Path> issues = folderProvider.getIssuesFromPath(currentWrapper.getPath());

            if (!issues.isEmpty())
            {
                Collections.sort(issues, comparatorFactory.provideIssuePathComparator());

                coverPath = issues.get(0);
            }
            else
            {
                // this should not occur since if volume exists, then at least one 
                // issue exists
                logger.warn("There are no issues for given volume.");
            }
        }
        else
        {
            throw new IllegalArgumentException("Given objectWrapper does not have supported level [COM/SUBCOM], but was [" + currentWrapper.getLevel() + "]");
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
                logger.info("For handle@" + currentWrapper.getHandle() + iax.getMessage());
            }
        }
    }

    @Override
    public void clear()
    {
        this.currentWrapper = null;
        this.periodical = null;
        this.volume = null;
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

    @Override
    public void movingWall(Community community) throws MovingWallException
    {
        if(periodical != null)
        {
            movingWallFactoryBean.parse(periodical);
        }
        else if(volume != null)
        {
            movingWallFactoryBean.parse(volume);
        }
    }
}
