/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.digilib.services.impl;

import cz.muni.ics.digilib.domain.Periodical;
import cz.muni.ics.digilib.domain.Volume;
import cz.muni.ics.dspace5.core.ObjectMapper;
import cz.muni.ics.dspace5.core.post.CommunityPostProcessor;
import cz.muni.ics.dspace5.impl.MetadataRow;
import cz.muni.ics.dspace5.core.ObjectWrapper;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
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

    @Override
    public List<MetadataRow> processMetadata(ObjectWrapper objectWrapper, boolean isTopCommunity) throws IllegalArgumentException
    {
        List<MetadataRow> resultList = new ArrayList<>();

        if (isTopCommunity)
        {
            Periodical p = null;
            try
            {
                p = objectMapper.convertPathToObject(objectWrapper.getPath(), "detail.xml");
            }
            catch (FileNotFoundException ex)
            {
                logger.error(ex,ex.getCause());
            }
            if(p != null)
            {
                resultList.add(new MetadataRow("dc", "title", null, null, p.getTitleMain()));
                
                for(String publisher : p.getPublisher())
                {
                    resultList.add(new MetadataRow("dc", "publisher", null, null, publisher));
                }
                
                for(String s : p.getTitleVariant())
                {
                    resultList.add(new MetadataRow("dc", "title","alternative",null,s));
                }
                
                for(String issn : p.getISSN())
                {
                    resultList.add(new MetadataRow("dc", "identifier", "issn", null, issn));
                }
            }         
            else
            {
                logger.info("Object at path ["+objectWrapper.getPath()+"] was not found");
            }
        }
        else
        {
            Volume v = objectWrapper.getObject();
            resultList.add(new MetadataRow("dc", "title", null, null, v.getVolumeName()));
        }

        return resultList;
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
