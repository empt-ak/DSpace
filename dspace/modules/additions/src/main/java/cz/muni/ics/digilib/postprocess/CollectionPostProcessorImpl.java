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
import cz.muni.ics.dspace5.api.post.CollectionPostProcessor;
import cz.muni.ics.dspace5.impl.ContextWrapper;
import cz.muni.ics.dspace5.impl.DSpaceTools;
import cz.muni.ics.dspace5.impl.MetadataWrapper;
import cz.muni.ics.dspace5.movingwall.MovingWallService;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
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
public class CollectionPostProcessorImpl implements CollectionPostProcessor
{

    private static final Logger logger = Logger.getLogger(CollectionPostProcessorImpl.class);
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

    private Issue issue;
    private Monography monography;
    private ObjectWrapper currentWrapper;

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
    public List<Metadatum> processMetadata(List<ObjectWrapper> parents, Map<String, Object> dataMap) throws IllegalArgumentException, IllegalStateException
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
    public void processCollection(Collection collection, List<ObjectWrapper> parents, Map<String, Object> dataMap) throws IllegalStateException, IllegalArgumentException
    {
        setCover(this.currentWrapper.getPath().resolve(COVER_FILENAME), collection);

        if (this.issue != null)
        {
            setupIssue(collection, parents, dataMap);
        }
        else if (this.monography != null)
        {
            setupMonography(collection, parents, dataMap);
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

    private void setupIssue(Collection collection, List<ObjectWrapper> parents, Map<String, Object> dataMap)
    {
        if (issue != null)
        {
            DateTime publDate;
            if (issue.getPublicationDate() != null && !issue.getPublicationDate().isEmpty())
            {
                publDate = dSpaceTools.parseDate(issue.getPublicationDate());
                dSpaceTools.createDataMap(MovingWallService.PUBLICATION_DATE, publDate, dataMap, false);
            }
            else
            {
                // if year is set @getPublYear then it is autoset to YEAR-12-31
                // or 1900-12-31 if no date is set
                publDate = dSpaceTools.parseDate(issue.getPublYear());
                dSpaceTools.createDataMap(MovingWallService.PUBLICATION_DATE, publDate, dataMap, false);
            }

            if (issue.getEmbargoEndDate() != null && !issue.getEmbargoEndDate().isEmpty())
            {
                dSpaceTools.createDataMap(MovingWallService.END_DATE, dSpaceTools.parseDate(issue.getEmbargoEndDate()), dataMap, false);
            }
            else
            {
                if (dataMap.containsKey(MovingWallService.MOVING_WALL))
                {
                    DateTime endDate = publDate.plus(Months.months(Integer.parseInt((String) dataMap.get(MovingWallService.MOVING_WALL))));
                    dSpaceTools.createDataMap(MovingWallService.END_DATE, endDate, dataMap, false);
                }
                else
                {
                    logger.debug("No moving wall is stored in dataMap. Assuming there is no movingWall for this branch.");
                }
            }

            if (!StringUtils.isEmpty(issue.getLinkToReal()))
            {
                String realHandle = handleService.getHandleForPath(Paths
                        .get(configurationService.getProperty("meditor.rootbase"))
                        .resolve(issue.getLinkToReal()), true);

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
    }

    private void setupMonography(Collection collection, List<ObjectWrapper> parents, Map<String, Object> dataMap)
    {
        // TODO
    }
}
