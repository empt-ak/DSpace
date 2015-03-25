/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.impl.io;

import cz.muni.ics.dspace5.core.HandleService;
import cz.muni.ics.dspace5.impl.ContextWrapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.apache.log4j.Logger;
import org.dspace.core.Context;
import org.dspace.services.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Component
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

    private static final Logger logger = Logger.getLogger(HandleServiceImpl.class);

    @Override
    public String getHandleForPath(Path path, boolean createIfMissing) throws IllegalArgumentException
    {        
        init();

        Path dspaceFile = path.resolve(handleFile);
        logger.debug("Attempting to read dspaceID from "+dspaceFile);

        String suffix = null;

        if (Files.exists(dspaceFile))
        {
            try
            {
                suffix = new String(Files.readAllBytes(dspaceFile));
                logger.debug("File found. Suffix is set as: "+suffix);
            }
            catch (IOException ex)
            {
                logger.error(ex);
            }
        }
        else if(createIfMissing)
        {
            logger.debug("File was not found. Creating new suffix.");
            String newSuffix = getNewHandle(contextWrapper.getContext().getDBConnection()).toString();

            if (newSuffix == null)
            {
                throw new RuntimeException("FATAL ERROR CREATING HANDLE.");
            }
            else
            {
                try
                {
                    Files.write(dspaceFile, newSuffix.getBytes(), StandardOpenOption.CREATE_NEW);
                    logger.debug("File with suffix ["+newSuffix+" created.");
                }
                catch (IOException ex)
                {
                    logger.error(ex);
                    throw new RuntimeException("FATAL ERROR CREATING HANDLE FILE.");
                }

                suffix = newSuffix;
            }
        }
        
        if(suffix == null)
        {
            return null;
        }
        else
        {
            return handlePrefix + suffix;
        }
    }

    @Override
    public String getVolumeHandle(Path path) throws IllegalArgumentException
    {        
        init();
        
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
            else
            {
                if (volumeSuffix == null)
                {
                    try
                    {
                        volumeSuffix = new String(Files.readAllBytes(dspaceVolumeID));
                    }
                    catch (IOException ex)
                    {
                        logger.error(ex, ex.getCause());
                        throw new RuntimeException("ERROR WHILE READING FROM dspace_volume_id.txt at path [" + dspaceVolumeID + "]");
                    }
                }
            }
        }

        if (volumeSuffix == null)
        {
            volumeSuffix = getNewHandle(contextWrapper.getContext().getDBConnection()).toString();
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
                        logger.fatal(ex, ex.getCause());
                        throw new RuntimeException("ERROR WHILE WRITING TO dspace_volume_id.txt at path [" + dspaceVolumeID + "]");
                    }
                }
            }
        }

        return handlePrefix + volumeSuffix;
    }

    /**
     * Method obtains new handle ID from database by running following
     * <b>PostgreSQL</b> Query:
     * <pre>
     *  select max(substring(handle from ?)::bigint)+1 as new_handle from handle
     * </pre>.
     *
     * This is created as Prepared statement with argument of
     * <i>handlePrefix.length()</i> then cast to long and selected maximum value
     * incremented by one. This method is forbidden to close obtained as method
     * argument.
     *
     * @param connection obtained from {@link Context#getDBConnection() }
     *
     * @return new handle suffix id, null if error occurred during execution
     *
     * @throws IllegalArgumentException if connection is null
     * @throws IllegalStateException    if connection is closed
     */
    private synchronized Long getNewHandle(Connection connection) throws IllegalArgumentException, IllegalStateException
    {
        if (connection == null)
        {
            throw new IllegalArgumentException("Connection is null.");
        }

        try
        {
            if (connection.isClosed())
            {
                throw new IllegalStateException("Connection is closed.");
            }
        }
        catch (SQLException ex)
        {
            throw new IllegalStateException("Connection is closed.", ex.getCause());
        }        

        Long resultID = null;

        try (PreparedStatement ps = connection.prepareStatement("select max(substring(handle from ?)::int)+1 as new_handle from handle"))
        {
            ps.setInt(1, handlePrefix.length()+1);

            try (ResultSet rs = ps.executeQuery())
            {
                rs.next();

                resultID = rs.getLong(1);
                logger.info("New handle suffix will be: "+resultID);
            }
        }
        catch (SQLException ex)
        {
            logger.error(ex);
        }

        return resultID;
    }

    /**
     * Method initializes field variables of this class.
     */
    private void init()
    {
        if (globalHandleFile == null)
        {
            logger.trace("Setting global handle file.");
            globalHandleFile = configurationService.getProperty("meditor.handle.file.global");
        }

        if (handleFile == null)
        {
            logger.trace("Setting handle file.");
            handleFile = configurationService.getProperty("meditor.handle.file");
        }

        if (handlePrefix == null)
        {
            logger.trace("Setting handle prefix.");
            handlePrefix = configurationService.getProperty("handle.prefix") + "/";
        }
    }
}
