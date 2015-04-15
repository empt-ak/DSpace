/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.digilib.postprocess;

import cz.muni.ics.digilib.domain.Article;
import cz.muni.ics.digilib.domain.MonographyChapter;
import cz.muni.ics.dspace5.api.ObjectMapper;
import cz.muni.ics.dspace5.api.ObjectWrapper;
import cz.muni.ics.dspace5.api.post.ItemPostProcessor;
import cz.muni.ics.dspace5.exceptions.MovingWallException;
import cz.muni.ics.dspace5.impl.ContextWrapper;
import cz.muni.ics.dspace5.impl.DSpaceTools;
import cz.muni.ics.dspace5.impl.ImportDataMap;
import cz.muni.ics.dspace5.impl.MetadataWrapper;
import cz.muni.ics.dspace5.movingwall.MWLockerProvider;
import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.SQLException;
import java.util.List;
import org.apache.log4j.Logger;
import org.dozer.Mapper;
import org.dspace.authorize.AuthorizeException;
import org.dspace.content.Bitstream;
import org.dspace.content.BitstreamFormat;
import org.dspace.content.Bundle;
import org.dspace.content.Item;
import org.dspace.content.Metadatum;
import org.dspace.core.Constants;
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
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private Mapper mapper;    
    @Autowired
    private DSpaceTools dSpaceTools;
    @Autowired
    private ImportDataMap importDataMap;
    @Autowired
    private ContextWrapper contextWrapper;
    @Autowired
    private MWLockerProvider mWLockerProvider;
    //@TODO set tru spring
    private final String[] pdfFileNames = {"enhanced.pdf", "source-enhanced.pdf", "source.pdf", "item.pdf"};
    
    private Article article;
    private MonographyChapter monographyChapter;
    private ObjectWrapper currentWrapper;
    
    
    @Override
    public void setup(ObjectWrapper objectWrapper) throws IllegalStateException, IllegalArgumentException
    {
        // TODO state exceptions
        this.currentWrapper = objectWrapper;
        if(objectWrapper.getPath().toString().contains("serial"))
        {
            try
            {
                this.article = objectMapper.convertPathToObject(objectWrapper.getPath(), "detail.xml");
            }
            catch(FileNotFoundException nfe)
            {
                logger.error(nfe,nfe.getCause());
            }
        }
        else if(objectWrapper.getPath().toString().contains("monograph"))
        {
            try
            {
                this.monographyChapter = objectMapper.convertPathToObject(objectWrapper.getPath(), "detail.xml");
            }
            catch(FileNotFoundException nfe)
            {
                logger.error(nfe,nfe.getCause());
            }
        }
        else
        {
            throw new IllegalArgumentException("Given input is not a valid path. Path should contain [serial/monography] but it did not.");
        }
    }

    @Override
    public List<Metadatum> processMetadata(List<ObjectWrapper> parents) throws IllegalArgumentException, IllegalStateException
    {
        MetadataWrapper metadataWrapper = new MetadataWrapper();
        
        if(article != null)
        {
            mapper.map(article, metadataWrapper);
        }
        else if(monographyChapter != null)
        {
            mapper.map(monographyChapter, metadataWrapper);
        }
        else
        {
            // TODO
            throw new IllegalStateException();
        }
        
        return metadataWrapper.getMetadata();
    }

    @Override
    public void processItem(Item item, List<ObjectWrapper> parents) throws IllegalStateException, IllegalArgumentException
    {
        importDataMap.put("itemHandle", currentWrapper.getHandle());
        
        Bundle[] oldBundles = null;
        
        try
        {
            // there might be other files like license text
            // thus we remove only DEFAULT
            oldBundles = item.getBundles(Constants.DEFAULT_BUNDLE_NAME);
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
            bundle = item.createBundle(Constants.DEFAULT_BUNDLE_NAME);
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
                Path possiblePath = currentWrapper.getPath().resolve(pdfFileName);
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
                    
                    pdfBitstream.setName(dSpaceTools.getNameForPDF(currentWrapper.getPath()));
                    pdfBitstream.setDescription("Full-text");
                    pdfBitstream.setFormat(BitstreamFormat.findByMIMEType(contextWrapper.getContext(), "application/pdf"));
                    pdfBitstream.update();
                    
                    try
                    {
                        mWLockerProvider.getLocker(Bitstream.class).lockObject(pdfBitstream);
                    }
                    catch(MovingWallException mwe)
                    {
                        logger.fatal(mwe.getMessage());
                    }
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

    @Override
    public void clear()
    {
        this.currentWrapper = null;
        this.monographyChapter = null;
        this.article = null;
    }
}
