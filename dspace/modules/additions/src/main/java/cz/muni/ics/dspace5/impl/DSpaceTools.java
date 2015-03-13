/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.log4j.Logger;
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

    private static final Logger logger = Logger.getLogger(DSpaceTools.class);
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

    /**
     * Out of given path (somewhere in ME) method recreates name for its PDF
     * file. For example <b>serial/1_SuperMag/14-1765-2/#3</b> the filename will
     * be <b>1_SuperMag_14-1765-2_3</b>
     *
     * @param path to be converted into PDF file name
     *
     * @return name for PDF file belonging to article
     */
    public String getNameForPDF(Path path)
    {
        StringBuilder sb = new StringBuilder();

        Path n = Paths.get(configurationService.getProperty("meditor.rootbase")).relativize(path);

        for (int i = 1; i < n.getNameCount() - 1; i++)
        {
            sb.append(n.getName(i)).append("_");
        }

        sb.append(n.getName(n.getNameCount() - 1).toString().replace("#", ""));

        return sb.toString();
    }

    /**
     * Method calculates hash of given file passed as {@code path} with given
     * algorithm.
     *
     * @param path      of file to be calculated
     * @param algorithm used for calculation
     *
     * @return
     *
     * @throws IllegalArgumentException
     */
    public String calculateHash(Path path, String algorithm) throws IllegalArgumentException
    {
        MessageDigest md = null;

        String result = null;

        try
        {
            md = MessageDigest.getInstance(algorithm);
        }
        catch (NoSuchAlgorithmException ex)
        {
            throw new IllegalArgumentException(ex.getMessage());
        }

        if (md != null)
        {
            try (InputStream is = Files.newInputStream(path, StandardOpenOption.READ))
            {
                byte[] buffer = new byte[1024];
                int num;

                while ((num = is.read(buffer)) != -1)
                {
                    md.update(buffer, 0, num);
                }
            }
            catch (IOException ex)
            {
                logger.error(ex, ex.getCause());
            }
            
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for(int i =0 ; i < bytes.length; i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff)+0x100,16).substring(1));
            }
            
            result = sb.toString();
        }

        return result;
    }
}
