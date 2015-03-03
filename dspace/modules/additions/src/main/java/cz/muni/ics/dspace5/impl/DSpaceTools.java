/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.impl;

import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.dspace.services.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Component
public class DSpaceTools
{

    @Autowired
    private ConfigurationService configurationService;

    /**
     * Method returns current level in ME path.
     *
     * @param p path to be checked
     *
     * @return current level of path
     */
    public int getPathLevel(Path p)
    {
        // lets have paths:
        // A=(/x/y/z/a/b)
        // B=(/x/y/z)
        // then B.relativize(A) would return a/b 
        // at this point getNameCount returns 2, but
        // a/b is e.g. serial/1_NiceMag so its root and should be resolved
        // as 0 thus -2        
        return Paths.get(configurationService.getProperty("meditor.rootbase"))
                .relativize(p).getNameCount() - 2;
    }

    /**
     * Method converts given <code>long</code> value into byte array.
     *
     * @param value to be converted
     *
     * @return byte array converted from input long value
     */
    public byte[] longToByte(Long value)
    {
        return ByteBuffer.allocate(Long.SIZE).putLong(value).array();
    }

    //@meditor: not needed
    /**
     * Method selects volume number out of given path. Example: if path to
     * collection is <b>1-1921-4</b> then volume number is <b>1</b>
     *
     * @param p path of issue
     *
     * @return volume number for given issue
     */
    public String getVolumeNumber(Path p)
    {
        return p.getFileName().toString().split("-")[0];
    }

    //@meditor
    /**
     * Method selects issue number out of given path.
     *
     * @param p of collection (issue)
     *
     * @return issue number.
     */
    public String getIssueNumber(Path p)
    {
        return p.getFileName().toString().split("-")[2];
    }

    /**
     * For given path for example an issue path, or an article path method
     * returns root directory of magazine.
     *
     * @param p path to be resolved
     *
     * @return root of given magazine
     *
     * @throws IllegalArgumentException if Path is somewhere lower than actual
     *                                  top directory of magazine.
     */
    public Path getRoot(Path p) throws IllegalArgumentException
    {
        return buildPath(p, 2);
    }

    /**
     * For given path for example an issue path itself, or article returns
     * issue.
     *
     * @param p
     *
     * @return
     *
     * @throws IllegalArgumentException
     */
    public Path getIssue(Path p) throws IllegalArgumentException
    {
        return buildPath(p, 3);
    }

    private Path buildPath(Path p, int level)
    {
        Path meditorPath = Paths.get(configurationService.getProperty("meditor.rootbase"));
        Path contextPath = meditorPath.relativize(p);

        Path result = Paths.get("");

        for (int i = 0; i < level; i++)
        {
            result = result.resolve(contextPath.getName(i));
        }

        return meditorPath.resolve(result);
    }
}
