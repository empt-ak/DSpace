/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.digilib.services.impl;

import cz.muni.ics.digilib.domain.Article;
import cz.muni.ics.digilib.domain.MonographyChapter;
import cz.muni.ics.dspace5.core.ObjectMapper;
import cz.muni.ics.dspace5.core.ObjectWrapper;
import cz.muni.ics.dspace5.core.post.ItemPostProcessor;
import cz.muni.ics.dspace5.impl.ContextWrapper;
import cz.muni.ics.dspace5.impl.DSpaceTools;
import cz.muni.ics.dspace5.impl.MetadataWrapper;
import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.dozer.Mapper;
import org.dspace.authorize.AuthorizeException;
import org.dspace.content.Bitstream;
import org.dspace.content.BitstreamFormat;
import org.dspace.content.Bundle;
import org.dspace.content.Item;
import org.dspace.content.Metadatum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Component
public class ItemPostProcessorImpl implements ItemPostProcessor
{
    private static final Logger logger = Logger.getLogger(ItemPostProcessorImpl.class);
    private static final String ORIGINAL = "ORIGINAL";
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private Mapper mapper;    
    @Autowired
    private DSpaceTools dSpaceTools;
    @Autowired
    private ContextWrapper contextWrapper;
    //@TODO set tru spring
    private final String[] pdfFileNames = {"enhanced.pdf", "source-enhanced.pdf", "source.pdf", "item.pdf"};
    
    @Override
    public List<Metadatum> processMetadata(ObjectWrapper objectWrapper, List<ObjectWrapper> parents, Map<String,Object> dataMap) throws IllegalArgumentException
    {
        MetadataWrapper metadataWrapper = new MetadataWrapper();
        
        if(objectWrapper.getPath().toString().contains("serial"))
        {
            Article article = null;
            try
            {
                article = objectMapper.convertPathToObject(objectWrapper.getPath(), "detail.xml");
            }
            catch(FileNotFoundException nfe)
            {
                logger.error(nfe,nfe.getCause());
            }

            if(article != null)
            {
                mapper.map(article, metadataWrapper);
            }
            else
            {
                logger.fatal("huehue");
            }
        }
        else if(objectWrapper.getPath().toString().contains("monograph"))
        {
            MonographyChapter chapter = null;
            try
            {
                chapter = objectMapper.convertPathToObject(objectWrapper.getPath(), "detail.xml");
            }
            catch(FileNotFoundException nfe)
            {
                logger.error(nfe,nfe.getCause());
            }
            if(chapter != null)
            {
                mapper.map(chapter,metadataWrapper);
            }
            else
            {
                logger.fatal("k");
            }
        }
        else
        {
            throw new IllegalArgumentException("Given input is not a valid path. Path should contain [serial/monography] but it did not.");
        }
        
        
        return metadataWrapper.getMetadata();
    }

    @Override
    public void processItem(ObjectWrapper objectWrapper, Item item, List<ObjectWrapper> parents, Map<String,Object> dataMap) throws IllegalArgumentException
    {
        // referencie, pdf atd, pre dmlcz .matematika a ine
        // @TODO improve by checking dates ?
        
        Bundle[] oldBundles = null;
        
        try
        {
            // there might be other files like license text
            // thus we remove only ORIGINAL
            oldBundles = item.getBundles(ORIGINAL);
        }
        catch(SQLException ex)
        {
            logger.debug(ex,ex.getCause());
        }
        
        if(oldBundles != null && oldBundles.length > 0)
        {
            try
            {
                for(Bundle oldBundle : oldBundles)
                {
                    item.removeBundle(oldBundle);
                }
            }
            catch(SQLException | AuthorizeException | IOException ex)
            {
                logger.debug(ex,ex.getCause());
            }            
        }
        
        Bundle bundle = null;
        try
        {
            bundle = item.createBundle(ORIGINAL);
        }
        catch(SQLException | AuthorizeException ex)
        {
            logger.error(ex,ex.getCause());
        }
        
        if(bundle != null)
        {
            Bitstream pdfBitstream = null;
            
            Path bitstreamPath = null;
            
            for(String pdfFileName : pdfFileNames)
            {
                Path possiblePath = objectWrapper.getPath().resolve(pdfFileName);
                if(!Files.exists(possiblePath))
                {
                    logger.debug("No pdf file found at path "+possiblePath);
                }
                else
                {
                    bitstreamPath = possiblePath;
                    break;
                }
            }
            
            if(bitstreamPath != null)
            {
                try(BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(bitstreamPath, StandardOpenOption.READ)))
                {
                    pdfBitstream = bundle.createBitstream(bis);
                    
                    pdfBitstream.setName(dSpaceTools.getNameForPDF(objectWrapper.getPath()));
                    pdfBitstream.setDescription("Full-text");
                    pdfBitstream.setFormat(BitstreamFormat.findByMIMEType(contextWrapper.getContext(), "application/pdf"));
                    pdfBitstream.update();
                }
                catch(IOException | AuthorizeException | SQLException ex)
                {
                    logger.error(ex,ex.getCause());
                }
            }
            else
            {
                //TODO
            }
        }
    }    
}
