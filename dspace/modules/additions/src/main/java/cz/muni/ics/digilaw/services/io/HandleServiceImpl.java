/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.digilaw.services.io;

import cz.muni.ics.dspace5.api.HandleService;
import cz.muni.ics.dspace5.impl.ContextWrapper;
import cz.muni.ics.dspace5.impl.io.FolderProvider;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.PostConstruct;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dspace.content.DSpaceObject;
import org.dspace.handle.HandleManager;
import org.dspace.services.ConfigurationService;
import org.dspace.storage.rdbms.DatabaseManager;
import org.dspace.storage.rdbms.TableRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Component(value = "handleService")
public class HandleServiceImpl implements HandleService
{

    private String handleFile = null;
    private String globalHandleFile = null;
    private String handlePrefix = null;

    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private FolderProvider folderProvider;
    @Autowired
    private ContextWrapper contextWrapper;

    private static final Logger LOGGER = Logger.getLogger(HandleServiceImpl.class);

    @Override
    public String getHandleForPath(Path path, boolean createIfMissing) throws IllegalArgumentException
    {
        return getHandleForPath(path, createIfMissing, handleFile);
    }

    @Override
    public String getVolumeHandle(Path path) throws IllegalArgumentException
    {
        List<Path> volume = folderProvider.getIssuesFromPath(path);

        String volumeSuffix = null;
        boolean missing = false;

        for (Path p : volume)
        {
            Path dspaceVolumeID = p.resolve("dspace_volume_id.txt");

            if (!Files.exists(dspaceVolumeID))
            {
                missing = true;
            }
            else if (volumeSuffix == null)
            {
                try
                {
                    volumeSuffix = new String(Files.readAllBytes(dspaceVolumeID));
                }
                catch (IOException ex)
                {
                    LOGGER.error(ex, ex.getCause());
                    throw new RuntimeException("ERROR WHILE READING FROM dspace_volume_id.txt at path [" + dspaceVolumeID + "]");
                }
            }
        }

        if (volumeSuffix == null)
        {
            try
            {
                volumeSuffix = getNewHandle();
            }
            catch (SQLException ex)
            {
                throw new RuntimeException(ex);
            }

        }

        if (missing)
        {
            for (Path p : volume)
            {
                Path dspaceVolumeID = p.resolve("dspace_volume_id.txt");

                if (!Files.exists(dspaceVolumeID))
                {
                    try
                    {
                        Files.write(dspaceVolumeID, volumeSuffix.getBytes(), StandardOpenOption.CREATE_NEW);
                    }
                    catch (IOException ex)
                    {
                        LOGGER.fatal(ex, ex.getCause());
                        throw new RuntimeException("ERROR WHILE WRITING TO dspace_volume_id.txt at path [" + dspaceVolumeID + "]");
                    }
                }
            }
        }

        return handlePrefix + volumeSuffix;
    }

    /**
     * Method obtains new handle suffix from database, by creating new one. The
     * suffix is same as row id if it is not altered by <b>select
     * setval('handle_seq', NEWVALUE);</b> command
     *
     * @return new handle suffix
     *
     * @throws SQLException if any error during creating occurs.
     */
    public synchronized String getNewHandle() throws SQLException
    {
        TableRow handle = DatabaseManager.create(contextWrapper.getContext(), "Handle");
        return Integer.toString(handle.getIntColumn("handle_id"));
    }

    /**
     * Method initializes field variables of this class.
     */
    @PostConstruct
    private void init()
    {
        // select max(substring(handle from ?)::bigint)+1 as new_handle from handle
        // the query above fetches new possible value for sequence
        if (globalHandleFile == null)
        {
            LOGGER.trace("Setting global handle file.");
            globalHandleFile = configurationService.getProperty("meditor.handle.file.global");
        }

        if (handleFile == null)
        {
            LOGGER.trace("Setting handle file.");
            handleFile = configurationService.getProperty("meditor.handle.file");
        }

        if (handlePrefix == null)
        {
            LOGGER.trace("Setting handle prefix.");
            handlePrefix = configurationService.getProperty("handle.prefix") + "/";
        }

        //select setval('handle_seq', 200000);
    }

    @Override
    public DSpaceObject getGenericObjectByHandle(String handle) throws IllegalArgumentException
    {
        if (StringUtils.isEmpty(handle))
        {
            throw new IllegalArgumentException("Given handle is empty.");
        }

        try
        {
            return HandleManager.resolveToObject(contextWrapper.getContext(), handle);
        }
        catch (SQLException ex)
        {
            LOGGER.error(ex, ex.getCause());
        }

        return null;
    }

    @Override
    public <T> T getObjectByHandle(String handle) throws IllegalArgumentException
    {
        if (StringUtils.isEmpty(handle))
        {
            throw new IllegalArgumentException("Given handle is empty.");
        }
        return (T) getGenericObjectByHandle(handle);
    }

    @Override
    public String getHandleForPath(Path path, boolean createIfMissing, String fileName) throws IllegalArgumentException
    {
        Path dspaceFile = path.resolve(fileName);
        LOGGER.debug("Attempting to read dspaceID from " + dspaceFile);

        String suffix = null;

        if (Files.exists(dspaceFile))
        {
            try
            {
                suffix = new String(Files.readAllBytes(dspaceFile));
                LOGGER.debug("File found. Suffix is set as: " + suffix);
            }
            catch (IOException ex)
            {
                LOGGER.error(ex);
            }
        }
        else if (createIfMissing)
        {
            LOGGER.debug("File was not found. Creating new suffix.");

            String newSuffix = null;
            try
            {
                newSuffix = getNewHandle();
            }
            catch (SQLException ex)
            {
                throw new RuntimeException(ex);
            }
            try
            {
                Files.write(dspaceFile, newSuffix.getBytes(), StandardOpenOption.CREATE_NEW);
                LOGGER.debug("File with suffix [" + newSuffix + " created.");
            }
            catch (IOException ex)
            {
                LOGGER.error(ex);
                throw new RuntimeException("FATAL ERROR CREATING HANDLE FILE. Suffix obtained from database @ " + newSuffix + " @path " + dspaceFile + " failed to write.");
            }

            suffix = newSuffix;
        }

        if (suffix == null)
        {
            return null;
        }
        else
        {
            return handlePrefix + suffix;
        }
    }
}
