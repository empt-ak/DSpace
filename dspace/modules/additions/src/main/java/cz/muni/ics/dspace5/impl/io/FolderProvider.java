/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.impl.io;

import cz.muni.ics.dspace5.impl.DSpaceTools;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Component
public class FolderProvider
{

    private static final Logger logger = Logger.getLogger(FolderProvider.class);
    @Autowired
    private DSpaceTools dSpaceTools;

    /**
     * Method returns {@code List} of subfolders from given {@code path}. The
     * depth is set to 1 so only direct ancestors of {@code path} are returned.
     * Output is not ordered by default.
     *
     * @param path input path out of which subfolders are to be found
     *
     * @return unordered list of paths representing subfolders.
     */
    public List<Path> getFoldersFromPath(Path path)
    {
        List<Path> resultList = new ArrayList<>();

        try (DirectoryStream<Path> ds = Files.newDirectoryStream(path, new DirectoryStream.Filter<Path>()
        {
            @Override
            public boolean accept(Path entry) throws IOException
            {
                return Files.isDirectory(entry);
            }
        }))
        {
            for (Path p : ds)
            {
                resultList.add(p);
            }
        }
        catch (IOException ex)
        {
            logger.error(ex, ex.getCause());
        }

        return resultList;
    }

    /**
     * Method returns list of issues that belong to given volume. The input
     * <b>has to be</b> any of the issue of volume, or volume itself
     * (represented by path with <i>.xml</i> file) we would like to retrieve.
     *
     * @param targetPath any issue of desired volume
     *
     * @return unordered list of issues that belong to volume
     */
    public List<Path> getIssuesFromPath(Path targetPath)
    {
        String volumeNumber = null;

        if (targetPath.getFileName().toString().endsWith(".xml"))
        {
            volumeNumber = StringUtils.substringBefore(targetPath.getFileName().toString(), ".xml");
        }
        else
        {
            volumeNumber = dSpaceTools.getVolumeNumber(targetPath);
        }
        return getIssues(targetPath.getParent(), volumeNumber);
    }

    /**
     * Method returns list of issues for given {@code rootPath} and specific
     * {@code volumeNumber}.
     *
     * @param rootPath     rootFolder for magazine/book
     * @param volumeNumber specific volume number
     *
     * @return unordered list of issues for explicitly given input parameters
     *
     * @throws IllegalArgumentException if volumeNumber is empty, path does not
     *                                  exist or is not a root path.
     */
    public List<Path> getIssues(Path rootPath, final String volumeNumber) throws IllegalArgumentException
    {
        if (StringUtils.isEmpty(StringUtils.trimToEmpty(volumeNumber)))
        {
            throw new IllegalArgumentException("VolumeNumber is empty.");
        }

        List<Path> resultList = new ArrayList<>();

        try (DirectoryStream<Path> ds = Files.newDirectoryStream(rootPath, new DirectoryStream.Filter<Path>()
        {
            @Override
            public boolean accept(Path entry) throws IOException
            {
                if (Files.isDirectory(entry))
                {
                    return dSpaceTools.getVolumeNumber(entry).equals(volumeNumber);
                }
                else
                {
                    return false;
                }
            }
        }))
        {
            for (Path p : ds)
            {
                resultList.add(p);
            }
        }
        catch (IOException ex)
        {
            logger.info(ex);
        }

        return resultList;
    }
}
