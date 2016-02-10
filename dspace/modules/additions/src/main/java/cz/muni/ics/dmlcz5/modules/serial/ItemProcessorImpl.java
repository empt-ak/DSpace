/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dmlcz5.modules.serial;

import cz.muni.ics.dmlcz5.citation.CitationBuilder;
import cz.muni.ics.dmlcz5.domain.Article;
import cz.muni.ics.dmlcz5.math.MathService;
import cz.muni.ics.dmlcz5.movingwall.MovingWallFactoryBean;
import cz.muni.ics.dmlcz5.service.io.references.ReferenceService;
import cz.muni.ics.dspace5.api.ObjectMapper;
import cz.muni.ics.dspace5.api.module.ItemProcessor;
import cz.muni.ics.dspace5.api.module.ObjectWrapper;
import cz.muni.ics.dspace5.citation.CitationProviderFactory;
import cz.muni.ics.dspace5.exceptions.MovingWallException;
import cz.muni.ics.dspace5.impl.ContextWrapper;
import cz.muni.ics.dspace5.impl.DSpaceTools;
import cz.muni.ics.dspace5.impl.InputDataMap;
import cz.muni.ics.dspace5.metadata.MetadataWrapper;
import cz.muni.ics.dspace5.movingwall.MWLockerProvider;
import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import org.apache.log4j.Logger;
import org.dozer.Mapper;
import org.dspace.authorize.AuthorizeException;
import org.dspace.content.Bitstream;
import org.dspace.content.BitstreamFormat;
import org.dspace.content.Bundle;
import org.dspace.content.Item;
import org.dspace.content.Metadatum;
import org.dspace.core.Constants;
import org.dspace.services.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class ItemProcessorImpl implements ItemProcessor
{

    private static final Logger logger = Logger.getLogger(ItemProcessorImpl.class);
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private Mapper mapper;
    @Autowired
    private DSpaceTools dSpaceTools;
    @Autowired
    private InputDataMap inputDataMap;
    @Autowired
    private ContextWrapper contextWrapper;
    @Autowired
    private MWLockerProvider mWLockerProvider;
    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private MovingWallFactoryBean movingWallFactoryBean;
    @Autowired
    private ReferenceService referenceService;
    @Autowired
    private MathService mathService;
    @Autowired
    private CitationBuilder citationBuilder;
    private List<Metadatum> citations;
    @Autowired
    private CitationProviderFactory citationProviderFactory;

    private String[] itemFileNames;
    private Article article;
    private ObjectWrapper currentWrapper;

    @PostConstruct
    private void init()
    {
        this.itemFileNames = configurationService.getProperty("dspace.item.pdf.allowednames").split(",");
        logger.debug("Allowed names for Item files to be imported set to " + Arrays.toString(itemFileNames));
    }

    @Override
    public void setup(ObjectWrapper objectWrapper) throws IllegalStateException, IllegalArgumentException
    {
        // TODO state exceptions
        this.currentWrapper = objectWrapper;
        try
        {
            this.article = objectMapper.convertPathToObject(objectWrapper.getPath(), "meta.xml");
        }
        catch (FileNotFoundException nfe)
        {
            logger.error(nfe, nfe.getCause());
        }
    }

    @Override
    public List<Metadatum> processMetadata(List<ObjectWrapper> parents) throws IllegalArgumentException, IllegalStateException
    {
        MetadataWrapper metadataWrapper = new MetadataWrapper();

        if (article != null)
        {
            mapper.map(article, metadataWrapper);  
            try
            {
                metadataWrapper.push(mathService.loadMathFormulas(currentWrapper));
            }
            catch (IOException ex)
            {
                logger.error(ex);
            }
            
//            citationBuilder.process(article, currentWrapper);
//            for(Metadatum m : citationProviderFactory.getCitationsAsMetadatum(citationBuilder.build()))
//            {
//                logger.fatal(m.value);
//            }
        }
        else
        {
            // TODO
            throw new IllegalStateException();
        }
        
        metadataWrapper.push(referenceService.processReferences(currentWrapper));

        return metadataWrapper.getMetadata();
    }

    @Override
    public void processItem(Item item, List<ObjectWrapper> parents) throws IllegalStateException, IllegalArgumentException
    { 
        Bundle[] oldBundles = null;

        try
        {
            // there might be other files like license text
            // thus we remove only DEFAULT
            oldBundles = item.getBundles(Constants.DEFAULT_BUNDLE_NAME);
        }
        catch (SQLException ex)
        {
            logger.debug(ex, ex.getCause());
        }

        if (oldBundles != null && oldBundles.length > 0)
        {
            try
            {
                for (Bundle oldBundle : oldBundles)
                {
                    item.removeBundle(oldBundle);
                }
            }
            catch (SQLException | AuthorizeException | IOException ex)
            {
                logger.debug(ex, ex.getCause());
            }
        }

        Bundle bundle = null;
        try
        {
            bundle = item.createBundle(Constants.DEFAULT_BUNDLE_NAME);
        }
        catch (SQLException | AuthorizeException ex)
        {
            logger.error(ex, ex.getCause());
        }

        if (bundle != null)
        {
            Bitstream pdfBitstream = null;

            Path bitstreamPath = null;

            for (String pdfFileName : itemFileNames)
            {
                Path possiblePath = currentWrapper.getPath().resolve(pdfFileName);
                if (!Files.exists(possiblePath))
                {
                    logger.trace("No pdf file found at path " + possiblePath);
                }
                else
                {
                    bitstreamPath = possiblePath;
                    break;
                }
            }

            if (bitstreamPath != null)
            {
                try (BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(bitstreamPath, StandardOpenOption.READ)))
                {
                    pdfBitstream = bundle.createBitstream(bis);

                    pdfBitstream.setName(dSpaceTools.getNameForPDF(currentWrapper.getPath()));
                    pdfBitstream.setDescription("Full-text");
                    pdfBitstream.setFormat(BitstreamFormat.findByMIMEType(contextWrapper.getContext(), "application/pdf"));
                    pdfBitstream.update();
                }
                catch (IOException | AuthorizeException | SQLException ex)
                {
                    logger.error(ex, ex.getCause());
                }
            }
            else
            {
                logger.warn("No importable files found for " + currentWrapper.getHandle() + " @" + currentWrapper.getPath());
            }
        }
    }

    @Override
    public void clear()
    {
        this.currentWrapper = null;
        this.article = null;
    }

    @Override
    public void movingWall(Item item) throws MovingWallException
    {
        movingWallFactoryBean.parse(article);
        
        if (inputDataMap.containsKey("movingwall") && !inputDataMap.getValue("movingwall").equals("ignore"))
        {
            Bundle[] original = null;
            
            try
            {
                original = item.getBundles(Constants.DEFAULT_BUNDLE_NAME);
            }
            catch(SQLException ex)
            {
                throw new MovingWallException(ex);
            }
            
            if(original != null && original.length > 0)
            {
                for(Bitstream bs : original[0].getBitstreams())
                {
                    mWLockerProvider.getLocker(Bitstream.class).lockObject(bs, movingWallFactoryBean.build());
                }
            }
        }
    }
}
