/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.digilib.services.impl;

import cz.muni.ics.digilib.domain.Issue;
import cz.muni.ics.digilib.domain.Monography;
import cz.muni.ics.dspace5.core.HandleService;
import cz.muni.ics.dspace5.core.MetadatumFactory;
import cz.muni.ics.dspace5.core.ObjectMapper;
import cz.muni.ics.dspace5.core.ObjectWrapper;
import cz.muni.ics.dspace5.core.post.CollectionPostProcessor;
import cz.muni.ics.dspace5.impl.ContextWrapper;
import cz.muni.ics.dspace5.impl.MetadataWrapper;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dozer.Mapper;
import org.dspace.authorize.AuthorizeException;
import org.dspace.content.Collection;
import org.dspace.content.Item;
import org.dspace.content.ItemIterator;
import org.dspace.content.Metadatum;
import org.dspace.handle.HandleManager;
import org.dspace.services.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Component
public class CollectionPostProcessorImpl implements CollectionPostProcessor
{
    private static final Logger logger = Logger.getLogger(CollectionPostProcessorImpl.class);
    private static final String COVER_FILENAME = "cover_thumb.png";

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private Mapper mapper;
    @Autowired
    private MetadatumFactory metadatumFactory;
    @Autowired
    private HandleService handleService;
    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private ContextWrapper contextWrapper;
    
    @Override
    public List<Metadatum> processMetadata(ObjectWrapper objectWrapper, List<ObjectWrapper> parents, Map<String,Object> dataMap) throws IllegalArgumentException
    {
        MetadataWrapper metadataWrapper = new MetadataWrapper();
        
        if(objectWrapper.getPath().toString().contains("serial"))
        {
            Issue issue = null;
        
            try
            {
                issue = objectMapper.convertPathToObject(objectWrapper.getPath(), "detail.xml");
            }
            catch(FileNotFoundException ex)
            {
                logger.error(ex,ex.getCause());
            }

            if(issue != null)
            {
                mapper.map(issue,metadataWrapper);
            }
            else
            {
                logger.info("'detail.xml' at path [" + objectWrapper.getPath() + "] was not found");
                metadataWrapper.getMetadata().add(metadatumFactory.createMetadatum("dc", "title", null, null, "NO-TITLE"));
            }
        }
        else if(objectWrapper.getPath().toString().contains("monograph"))
        {
            Monography mono = null;
            
            try
            {
                mono = objectMapper.convertPathToObject(objectWrapper.getPath(), "detail.xml");
            }
            catch(FileNotFoundException nfe)
            {
                logger.error(nfe,nfe.getCause());
            }
            
            if(mono != null)
            {
                mapper.map(mono, metadataWrapper);
            }
            else
            {
                logger.info("'detail.xml' at path [" + objectWrapper.getPath() + "] was not found");
                metadataWrapper.getMetadata().add(metadatumFactory.createMetadatum("dc", "title", null, null, "NO-TITLE"));
            }
        }
        else
        {
            throw new IllegalArgumentException("Given input is not a valid path. Path should contain [serial/monography] but it did not.");
        }
        
        return metadataWrapper.getMetadata();
    }

    @Override
    public void processCollection(ObjectWrapper objectWrapper, Collection collection, List<ObjectWrapper> parents, Map<String,Object> dataMap) throws IllegalArgumentException, UnsupportedOperationException
    {
        if(Files.exists(objectWrapper.getPath()))
        {
            try(FileInputStream fis = new FileInputStream(objectWrapper.getPath().resolve(COVER_FILENAME).toFile()))
            {
                collection.setLogo(fis);
            }
            catch(IOException | AuthorizeException | SQLException ex)
            {
                logger.error(ex, ex.getCause());
            }
        }
        else
        {
            logger.info("There is no file on given path. Bundle will be not set for ["+objectWrapper.getHandle()+"] at path "+objectWrapper.getPath().toString());
        }
        
        if(objectWrapper.getPath().toString().contains("serial"))
        {
            Issue issue = null;
        
            try
            {
                issue = objectMapper.convertPathToObject(objectWrapper.getPath(), "detail.xml");
            }
            catch(FileNotFoundException ex)
            {
                logger.error(ex,ex.getCause());
            }

            if(issue != null)
            {
                if(!StringUtils.isEmpty(issue.getLinkToReal()))
                {
                    String realHandle = handleService.getHandleForPath(Paths
                            .get(configurationService.getProperty("meditor.rootbase"))
                            .resolve(issue.getLinkToReal()), true);

                    Collection realCollection = null;
                    try
                    {
                        realCollection = (Collection) HandleManager.resolveToObject(contextWrapper.getContext(), realHandle);
                    }
                    catch(SQLException | ClassCastException ex)
                    {
                        logger.error(ex,ex.getCause());
                    }

                    if(realCollection != null)
                    {
                        try
                        {
                            ItemIterator ii = realCollection.getAllItems();
                            while(ii.hasNext())
                            {
                                Item next = ii.next();
                                logger.debug("$next" + next);

                                collection.addItem(next);
                                logger.info("VIRTUAL:: "+next.getHandle()+" mapped to "+collection.getHandle());
                            }
                        }
                        catch(SQLException | AuthorizeException ex)
                        {
                            logger.error(ex,ex.getCause());
                        }
                    }
                    else
                    {
                        logger.warn("For "+objectWrapper.getHandle()+" @path: "+objectWrapper.getPath()
                                +" there is no real target Collection imported yet. Target handle is ["
                                +realHandle+"] but returned object is null. No subitems from real Collection will be attached to this one.");
                    }
                }
            }
        }
        else if(objectWrapper.getPath().toString().contains("monograph"))
        {
            //TODO
        }
    }    
}
