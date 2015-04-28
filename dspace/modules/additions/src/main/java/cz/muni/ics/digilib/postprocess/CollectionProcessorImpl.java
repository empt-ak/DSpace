/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.digilib.postprocess;

import cz.muni.ics.digilib.domain.Issue;
import cz.muni.ics.digilib.domain.Monography;
import cz.muni.ics.dspace5.api.HandleService;
import cz.muni.ics.dspace5.api.ObjectMapper;
import cz.muni.ics.dspace5.api.ObjectWrapper;
import cz.muni.ics.dspace5.api.post.CollectionProcessor;
import cz.muni.ics.dspace5.exceptions.MovingWallException;
import cz.muni.ics.dspace5.impl.ContextWrapper;
import cz.muni.ics.dspace5.impl.DSpaceTools;
import cz.muni.ics.dspace5.impl.ImportDataMap;
import cz.muni.ics.dspace5.impl.MetadataWrapper;
import cz.muni.ics.dspace5.movingwall.MWLockerProvider;
import cz.muni.ics.dspace5.movingwall.MovingWallService;
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
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dozer.Mapper;
import org.dspace.authorize.AuthorizeException;
import org.dspace.content.Collection;
import org.dspace.content.Item;
import org.dspace.content.ItemIterator;
import org.dspace.content.Metadatum;
import org.dspace.handle.HandleManager;
import org.dspace.services.ConfigurationService;
import org.joda.time.DateTime;
import org.joda.time.Months;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Component
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
    private ContextWrapper contextWrapper;
    @Autowired
    private DSpaceTools dSpaceTools;
    @Autowired
    private ImportDataMap importDataMap;
    @Autowired
    private MWLockerProvider mWLockerProvider;

    private Issue issue;
    private Monography monography;
    private ObjectWrapper currentWrapper;
    private String[] collectionFileNames;

    @PostConstruct
    private void init()
    {
        this.collectionFileNames = configurationService.getProperty("dspace.collection.files").split(",");
        logger.info("Allowed names for Collection files to be imported set to " + Arrays.toString(collectionFileNames));
    }

    @Override
    public void setup(ObjectWrapper objectWrapper) throws IllegalStateException, IllegalArgumentException
    {
        if (this.currentWrapper != null || (this.issue != null ^ this.monography != null))
        {
            throw new IllegalStateException("It seems that setup was already called before, or clear was not called properly.");
        }

        this.currentWrapper = objectWrapper;
        if (objectWrapper.getPath().toString().contains("serial"))
        {
            try
            {
                this.issue = objectMapper.convertPathToObject(objectWrapper.getPath(), "detail.xml");
            }
            catch (FileNotFoundException ex)
            {
                logger.error(ex, ex.getCause());
                this.currentWrapper = null;
            }
        }
        else if (objectWrapper.getPath().toString().contains("monograph"))
        {
            try
            {
                this.monography = objectMapper.convertPathToObject(objectWrapper.getPath(), "detail.xml");
            }
            catch (FileNotFoundException nfe)
            {
                logger.error(nfe, nfe.getCause());
                this.currentWrapper = null;
            }
        }

        if (!(this.issue == null ^ this.monography == null))
        {
            throw new IllegalArgumentException("It was not possible to create proper representation of passed objectWrapper");
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
        else if (this.monography != null)
        {
            mapper.map(monography, metadataWrapper);
        }
        else
        {
            throw new IllegalStateException();
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
            setupIssue(collection, parents);
        }
        else if (this.monography != null)
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
        this.monography = null;
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

    private void setupIssue(Collection collection, List<ObjectWrapper> parents)
    {
        if (issue != null)
        {
            DateTime publDate = getPublDate(Issue.class);
            DateTime endDate = getEndDate(Issue.class, publDate);
            importDataMap.put(MovingWallService.PUBLICATION_DATE, publDate);
            importDataMap.put(MovingWallService.END_DATE, endDate);

            try
            {
                resolveVirtual(issue, collection);
            }
            catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex)
            {
                logger.error(ex);
            }
        }
    }

    private void setupMonography(Collection collection, List<ObjectWrapper> parents)
    {
        try
        {
            resolveVirtual(monography, collection);
        }
        catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex)
        {
            logger.error(ex);
        }

        for (String file : collectionFileNames)
        {
            Path wholeBook = currentWrapper.getPath().resolve(file);
            if (Files.exists(wholeBook))
            {
                Path extraStorage = dSpaceTools.getExtraStoragePath(currentWrapper.getPath());

                try
                {
                    Files.createDirectories(extraStorage);
                    Files.copy(wholeBook, extraStorage.resolve(file), StandardCopyOption.REPLACE_EXISTING);
                }
                catch (IOException ex)
                {
                    logger.error(ex, ex.getCause());
                }
            }
        }
        
        DateTime publDate = getPublDate(Monography.class);
        DateTime endDate = getEndDate(Monography.class, publDate);
        importDataMap.put(MovingWallService.PUBLICATION_DATE, publDate);
        importDataMap.put(MovingWallService.END_DATE, endDate);

        try
        {
            mWLockerProvider.getLocker(Collection.class).lockObject(collection);
        }
        catch (IllegalArgumentException | MovingWallException ex)
        {
            logger.fatal(ex, ex.getCause());
        }
    }

    private void resolveVirtual(Object object, Collection collection) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {

        String linkToReal = StringUtils.trim((String) PropertyUtils.getProperty(object, "linkToReal"));// trim needed #see SpisyFF/209-1977-1
        if (!StringUtils.isEmpty(linkToReal))
        {
            String realHandle = handleService.getHandleForPath(Paths
                    .get(configurationService.getProperty("meditor.rootbase"))
                    .resolve(linkToReal), true);

            logger.debug("Attempting to resolve virtual links of " + collection.getHandle() + " linked to " + linkToReal + " with handle " + realHandle + " .");

            Collection realCollection = null;
            try
            {
                realCollection = (Collection) HandleManager.resolveToObject(contextWrapper.getContext(), realHandle);
            }
            catch (SQLException | ClassCastException ex)
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

    // @TODO implement by interface
    private DateTime getPublDate(Class clasz)
    {
        if (clasz.equals(Monography.class))
        {
            return null;
        }
        else if (clasz.equals(Issue.class))
        {
            if (issue.getPublicationDate() != null && !issue.getPublicationDate().isEmpty())
            {
                return dSpaceTools.parseDate(issue.getPublicationDate());
                //importDataMap.put(MovingWallService.PUBLICATION_DATE, publDate);
            }
            else
            {
                // if year is set @getPublYear then it is autoset to YEAR-12-31
                // or 1900-12-31 if no date is set
                return dSpaceTools.parseDate(issue.getPublYear());
                //importDataMap.put(MovingWallService.PUBLICATION_DATE, publDate);
            }
        }
        else
        {
            throw new IllegalArgumentException("Unsupported class.");
        }
    }
    
    private DateTime getEndDate(Class clasz, DateTime publDate)
    {
        if(clasz.equals(Monography.class))
        {
            return null;
        }
        else if(clasz.equals(Issue.class))
        {
            if (issue.getEmbargoEndDate() != null && !issue.getEmbargoEndDate().isEmpty())
            {
                return dSpaceTools.parseDate(issue.getEmbargoEndDate());
            
                //importDataMap.put(MovingWallService.END_DATE, dSpaceTools.parseDate(issue.getEmbargoEndDate()));
            }
            else
            {
                if(importDataMap.containsKey(MovingWallService.MOVING_WALL))
                {
                    return publDate.plus(Months.months(Integer.parseInt(importDataMap.getTypedValue(MovingWallService.MOVING_WALL, String.class))));
                }
                else
                {                    
                    return null;
                }
            }
            
//                if (importDataMap.containsKey(MovingWallService.MOVING_WALL))
//                {
//                    DateTime endDate = publDate.plus(Months.months(Integer.parseInt(importDataMap.getTypedValue(MovingWallService.MOVING_WALL, String.class))));
//                    importDataMap.put(MovingWallService.END_DATE, endDate);
//                }
//                else
//                {
//                    
//                }
//            }
        }
        else
        {
            throw new IllegalArgumentException("Unsupported class.");
        }
    }
}
