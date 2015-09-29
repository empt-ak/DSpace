/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dmlcz5.modules.celebrity;

import cz.muni.ics.dmlcz5.domain.Journal;
import cz.muni.ics.dmlcz5.domain.Volume;
import cz.muni.ics.dmlcz5.movingwall.MovingWallFactoryBean;
import cz.muni.ics.dspace5.api.ObjectMapper;
import cz.muni.ics.dspace5.api.module.CommunityProcessor;
import cz.muni.ics.dspace5.api.module.ObjectWrapper;
import cz.muni.ics.dspace5.exceptions.MovingWallException;
import cz.muni.ics.dspace5.impl.DSpaceTools;
import cz.muni.ics.dspace5.impl.InputDataMap;
import cz.muni.ics.dspace5.metadata.MetadataWrapper;
import cz.muni.ics.dspace5.metadata.MetadatumFactory;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
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
    @Autowired
    private InputDataMap inputDataMap;

    private ObjectWrapper currentWrapper;
    private Journal journal;
    private Volume volume;
    private boolean isVolume = false;

    @Override
    public void setup(ObjectWrapper objectWrapper) throws IllegalStateException, IllegalArgumentException
    {
        if (objectWrapper == null)
        {
            throw new IllegalArgumentException("");
        }
        //TODO exceptions
        this.currentWrapper = objectWrapper;
        if (objectWrapper.getLevel().equals(ObjectWrapper.LEVEL.COM))
        {
            try
            {
                this.journal = objectMapper.convertPathToObject(objectWrapper.getPath(), "meta.xml");
                // this is workaround for volumes see comment in next else branch
                this.inputDataMap.put("volumeList", journal.getVolume());
            }
            catch (FileNotFoundException ex)
            {
                logger.error(ex, ex.getCause());
            }
        }
        else if (objectWrapper.getLevel().equals(ObjectWrapper.LEVEL.SUBCOM))
        {
            // this will do nothing because volume is stored in parent branch
            // volume will be set up in #processMetadata. its a nasty hack
            // since faculty of arts editor works slightly different and this
            // is sort of unexpected behaviour. we just set flag #isVolume
            // to true so process method will know what to do
            isVolume = true;
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public List<Metadatum> processMetadata(List<ObjectWrapper> parents) throws IllegalArgumentException, IllegalStateException
    {
        MetadataWrapper metadataWrapper = new MetadataWrapper();
        if (this.journal != null)
        {
            mapper.map(this.journal, metadataWrapper);
            
            metadataWrapper.getMetadata().add(metadatumFactory.createMetadatum("dc", "type", null, null, "celebrity"));
        }
        else if(isVolume)
        {
            List<Volume> volumes = inputDataMap.getTypedValue("volumeList", List.class);
            BigInteger currentVolume = new BigInteger(StringUtils.substringBefore(this.currentWrapper.getPath().getFileName().toString(),".xml"));
            for(Volume v : volumes)
            {
                if(v.getNumber().equals(currentVolume))
                {
                    this.volume = v;
                    break;
                }
            }
            mapper.map(this.volume, metadataWrapper);
        }
        else
        {
            // TODO
            throw new IllegalStateException();
        }
        
        //metadataWrapper.getMetadata().add(metadatumFactory.createMetadatum("muni", "mepath", null, null, dSpaceTools.getOnlyMEPath(currentWrapper.getPath()).toString()));

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
        this.journal = null;
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
        movingWallFactoryBean.parse(journal);
    }
}
