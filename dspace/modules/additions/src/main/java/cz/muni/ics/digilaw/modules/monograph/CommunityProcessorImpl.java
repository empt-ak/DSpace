/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.digilaw.modules.monograph;

import cz.muni.ics.digilaw.domain.MonographicSeries;
import cz.muni.ics.digilaw.movingwall.MovingWallFactoryBean;
import cz.muni.ics.dspace5.api.ObjectMapper;
import cz.muni.ics.dspace5.api.module.CommunityProcessor;
import cz.muni.ics.dspace5.api.module.ObjectWrapper;
import cz.muni.ics.dspace5.exceptions.MovingWallException;
import cz.muni.ics.dspace5.impl.DSpaceTools;
import cz.muni.ics.dspace5.metadata.MetadataWrapper;
import cz.muni.ics.dspace5.metadata.MetadatumFactory;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
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
    private DSpaceTools dSpaceTools;
    @Autowired
    private MovingWallFactoryBean movingWallFactoryBean;

    private ObjectWrapper currentWrapper;
    private MonographicSeries monographicSeries;

    @Override
    public void setup(ObjectWrapper objectWrapper) throws IllegalStateException, IllegalArgumentException
    {
        if(objectWrapper == null)
        {
            throw new IllegalArgumentException("");
        }
        //TODO exceptions
        this.currentWrapper = objectWrapper;
        
        try
        {
            this.monographicSeries = objectMapper.convertPathToObject(objectWrapper.getPath(), "detail.xml");
        }
        catch (FileNotFoundException ex)
        {
            logger.error(ex, ex.getCause());
        }
    }

    @Override
    public List<Metadatum> processMetadata(List<ObjectWrapper> parents) throws IllegalArgumentException, IllegalStateException
    {
        MetadataWrapper metadataWrapper = new MetadataWrapper();
        if (this.monographicSeries != null)
        {
            mapper.map(this.monographicSeries, metadataWrapper);
        }
        else
        {
            // TODO
            throw new IllegalStateException();
        }
        
        metadataWrapper.getMetadata().add(metadatumFactory.createMetadatum("digilaw", "mepath", null, null, dSpaceTools.getOnlyMEPath(currentWrapper.getPath()).toString()));

        return metadataWrapper.getMetadata();
    }

    @Override
    public void processCommunity(Community community, List<ObjectWrapper> parents) throws IllegalStateException, IllegalArgumentException
    {
        try
        {
            setCover(currentWrapper.getPath().resolve(COVER_FILENAME), community);
        }
        catch (IllegalArgumentException iax)
        {
            logger.info("For handle@" + currentWrapper.getHandle() + iax.getMessage());
        }
    }

    @Override
    public void clear()
    {
        this.currentWrapper = null;
        this.monographicSeries = null;
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
        movingWallFactoryBean.parse(monographicSeries);
    }
}
