/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dmlcz5.modules.serial;

import cz.muni.ics.dmlcz5.domain.Issue;
import cz.muni.ics.dmlcz5.movingwall.MovingWallFactoryBean;
import cz.muni.ics.dspace5.api.HandleService;
import cz.muni.ics.dspace5.api.ObjectMapper;
import cz.muni.ics.dspace5.api.module.CollectionProcessor;
import cz.muni.ics.dspace5.api.module.ObjectWrapper;
import cz.muni.ics.dspace5.exceptions.MovingWallException;
import cz.muni.ics.dspace5.impl.DSpaceTools;
import cz.muni.ics.dspace5.metadata.MetadataWrapper;
import cz.muni.ics.dspace5.movingwall.MWLockerProvider;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dozer.Mapper;
import org.dspace.authorize.AuthorizeException;
import org.dspace.content.Collection;
import org.dspace.content.Item;
import org.dspace.content.ItemIterator;
import org.dspace.content.Metadatum;
import org.dspace.services.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class CollectionProcessorImpl implements CollectionProcessor
{

    private static final Logger logger = Logger.getLogger(CollectionProcessorImpl.class);
    private static final String COVER_FILENAME = "cover_thumb.png";

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private Mapper mapper;
    @Autowired
    private HandleService handleService;
    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private DSpaceTools dSpaceTools;
    @Autowired
    private MWLockerProvider mWLockerProvider;
    @Autowired
    private MovingWallFactoryBean movingWallFactoryBean;

    private Issue issue;
    private ObjectWrapper currentWrapper;
    private String[] collectionFileNames;

    @PostConstruct
    private void init()
    {
        this.collectionFileNames = configurationService.getProperty("dspace.collection.files").split(",");
        logger.debug("Allowed names for Collection files to be imported set to " + Arrays.toString(collectionFileNames));
    }

    @Override
    public void setup(ObjectWrapper objectWrapper) throws IllegalStateException, IllegalArgumentException
    {
        if (this.currentWrapper != null || this.issue != null)
        {
            throw new IllegalStateException("It seems that setup was already called before, or clear was not called properly.");
        }

        this.currentWrapper = objectWrapper;

        try
        {
            this.issue = objectMapper.convertPathToObject(objectWrapper.getPath(), "meta.xml");
        }
        catch (FileNotFoundException nfe)
        {
            this.currentWrapper = null;
            throw new IllegalStateException("detail.xml not found", nfe.getCause());
        }
    }

    @Override
    public List<Metadatum> processMetadata(List<ObjectWrapper> parents) throws IllegalArgumentException, IllegalStateException
    {
        MetadataWrapper metadataWrapper = new MetadataWrapper();

        if (this.issue != null)
        {
            mapper.map(issue, metadataWrapper);
        }
        else
        {
            throw new IllegalStateException("Monography is null did you called #setup() first ?.");
        }

        return metadataWrapper.getMetadata();
    }

    @Override
    public void processCollection(Collection collection, List<ObjectWrapper> parents) throws IllegalStateException, IllegalArgumentException
    {
        try
        {
            setCover(this.currentWrapper.getPath().resolve(COVER_FILENAME), collection);
        }
        catch (IllegalArgumentException iae)
        {
            logger.warn(iae.getMessage());
        }

        if (this.issue != null)
        {
            setupMonography(collection, parents);
        }
        else
        {
            throw new IllegalStateException("Issue and Monography, are null did you called #setup() first ?.");
        }
    }

    @Override
    public void clear()
    {
        this.currentWrapper = null;
        this.issue = null;
    }

    /**
     * Sets cover for given Collection. Cover is specified by {@code coverPath}.
     *
     * @param coverPath  path to cover file.
     * @param collection to which cover is set
     *
     * @throws IllegalArgumentException if cover does not exist on given
     *                                  {@code coverPath}
     */
    private void setCover(Path coverPath, Collection collection) throws IllegalArgumentException
    {
        if (Files.exists(coverPath))
        {
            try (FileInputStream fis = new FileInputStream(coverPath.toFile()))
            {
                collection.setLogo(fis);
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

    private void setupMonography(Collection collection, List<ObjectWrapper> parents)
    {
        try
        {
            resolveVirtual(collection);
        }
        catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex)
        {
            logger.error(ex);
        }

        for (String file : collectionFileNames)
        {
            Path extraContent = currentWrapper.getPath().resolve(file);
            if (Files.exists(extraContent))
            {
                Path extraStorage = dSpaceTools.getExtraStoragePath(currentWrapper.getPath());

                try
                {
                    Files.createDirectories(extraStorage);
                    Files.copy(extraContent, extraStorage.resolve(file), StandardCopyOption.REPLACE_EXISTING);
                    
                    collection.clearMetadata("muni", "externalcontent", "*", "*");
                    collection.addMetadata("muni", "externalcontent", null, null, file);
                    movingWallFactoryBean.setExtraStoragePath(extraStorage);
                }
                catch (IOException ex)
                {
                    logger.error(ex, ex.getCause());
                }
            }
        }        
    }

    private void resolveVirtual(Collection collection) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        //@DML
        String linkToReal = null; //l = issue.getLinkToReal();
        if (!StringUtils.isEmpty(linkToReal))
        {
            String realHandle = handleService.getHandleForPath(Paths
                    .get(configurationService.getProperty("meditor.rootbase"))
                    .resolve(linkToReal), true);

            logger.debug("Attempting to resolve virtual links of " + collection.getHandle() + " linked to " + linkToReal + " with handle " + realHandle + " .");

            Collection realCollection = null;
            try
            {
                realCollection = handleService.getObjectByHandle(realHandle);
            }
            catch (ClassCastException ex)
            {
                logger.error(ex, ex.getCause());
            }

            if (realCollection != null)
            {
                try
                {
                    ItemIterator ii = realCollection.getAllItems();
                    while (ii.hasNext())
                    {
                        Item next = ii.next();
                        collection.addItem(next);
                        logger.info("VIRTUAL:: " + next.getHandle() + " mapped to " + collection.getHandle());
                    }
                }
                catch (SQLException | AuthorizeException ex)
                {
                    logger.error(ex, ex.getCause());
                }
            }
            else
            {
                logger.warn("For " + currentWrapper.getHandle() + " @path: " + currentWrapper.getPath()
                        + " there is no real target Collection imported yet. Target handle is ["
                        + realHandle + "] but returned object is null. No subitems from real Collection will be attached to this one.");
            }
        }
    }
    
    @Override
    public void movingWall(Collection collection) throws MovingWallException
    {
        movingWallFactoryBean.parse(issue);

        try
        {
            mWLockerProvider.getLocker(Collection.class).lockObject(collection, movingWallFactoryBean.build());
        }
        catch (IllegalArgumentException | MovingWallException ex)
        {
            logger.fatal(ex, ex.getCause());
        }
    }
}
