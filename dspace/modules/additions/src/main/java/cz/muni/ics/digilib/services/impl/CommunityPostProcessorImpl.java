/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.digilib.services.impl;

import cz.muni.ics.digilib.domain.Periodical;
import cz.muni.ics.digilib.domain.Volume;
import cz.muni.ics.dspace5.core.MetadatumFactory;
import cz.muni.ics.dspace5.core.ObjectMapper;
import cz.muni.ics.dspace5.core.ObjectWrapper;
import cz.muni.ics.dspace5.core.post.CommunityPostProcessor;
import cz.muni.ics.dspace5.impl.DSpaceTools;
import cz.muni.ics.dspace5.impl.MetadataWrapper;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
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
    private DSpaceTools dSpaceTools;

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
            logger.info("its volume!");
            logger.info("huehue$ "+objectWrapper.getPath());
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
        if (Files.exists(objectWrapper.getPath()))
        {
            try (FileInputStream fis = new FileInputStream(objectWrapper.getPath().resolve(COVER_FILENAME).toFile()))
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
            logger.info("There is no file on given path. Bundle will be not set for [" + objectWrapper.getHandle() + "] at path " + objectWrapper.getPath().toString());
        }
    }
}
