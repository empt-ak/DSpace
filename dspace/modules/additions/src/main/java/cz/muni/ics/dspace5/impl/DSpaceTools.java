/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.impl;

import cz.muni.ics.dspace5.movingwall.MovingWallService;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.apache.log4j.Logger;
import org.dspace.authorize.AuthorizeException;
import org.dspace.eperson.EPerson;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Component
public class DSpaceTools extends AbstractTools
{

    private static final Logger logger = Logger.getLogger(DSpaceTools.class);
    private final List<DateTimeFormatter> knownDateFormats = new ArrayList<>();

    @PostConstruct
    private void init()
    {
        knownDateFormats.add(DateTimeFormat.forPattern("yyyy-MM-dd"));
        knownDateFormats.add(DateTimeFormat.forPattern("yyyy-MM"));
        knownDateFormats.add(DateTimeFormat.forPattern("yyyy"));
        logger.info("@PostConstruct:- knownDateFormats initialized.");
    }

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
     * @param p path to article
     *
     * @return path to issue if input was an article
     *
     * @throws IllegalArgumentException if path is not a article
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
     * @return hash of file for given path with algorithm name provided as
     *         method arguments
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
            for (int i = 0; i < bytes.length; i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            result = sb.toString();
        }

        return result;
    }

    /**
     * Method parses given input using given pattern.
     *
     * @param input   to be parsed
     * @param pattern used for parsing
     *
     * @return DateTime represented by given input and pattern
     *
     * @throws ParseException if any error occurs
     */
    public DateTime parseDate(String input, String pattern) throws ParseException
    {
        return DateTimeFormat.forPattern(pattern).parseDateTime(input.trim());
    }

    /**
     * Method parses given input based on known patterns. Following pattern are
     * recognized:
     * <ul>
     * <li>yyyy-MM-dd</li>
     * <li>yyyy-MM</li>
     * <li>yyyy</li>
     * </ul>. If any of values is missing (month, day) then maximum possible
     * value is added. So if day is missing then 29/30/31 as day is added. If
     * month is missing aswell then 12th month is added. The main use this
     * method is in {@link MovingWallService} where dates are parsed. If none of
     * patterns is matched towards given input then <b>1900-12-31</b> is
     * returned.
     *
     * @param input to be parsed
     *
     * @return DateTime parsed according to known patterns
     */
    public DateTime parseDate(String input)
    {
        for (DateTimeFormatter dtf : knownDateFormats)
        {
            try
            {
                DateTime dt = dtf.parseDateTime(input);
                if (input.length() == 10)
                {
                    return dt;
                }
                else if (input.length() == 7)
                {
                    return dt.dayOfMonth().withMaximumValue();
                }
                else if (input.length() == 4)
                {
                    return dt.monthOfYear().withMaximumValue().dayOfMonth().withMaximumValue();
                }
            }
            catch (IllegalArgumentException pe)
            {
                logger.trace(pe);
            }
        }

        return knownDateFormats.get(0).parseDateTime("1900-12-31");
    }
    
    /**
     * Method finds {@code EPerson} inside system based on given input value. If null, or empty string is passed then first user found is set as current operating DSpace user. Otherwise EPerson (if found) is returned by given input.
     * @param email of person to be found
     * @return EPerson with given email, or first one ever created if no input is specified
     * @throws IllegalStateException if {@code Context} has not been initialized yet
     * @throws IllegalArgumentException if EPerson with given email does not exist.
     */
    public EPerson findEPerson(String email) throws IllegalStateException, IllegalArgumentException
    {
        if(contextWrapper.getContext() == null)
        {
            throw new IllegalStateException("Context not yet initialized.");
        }
        
        EPerson[] persons = new EPerson[0];
        
        if(email == null || email.isEmpty())
        {
            persons = new EPerson[0];
            try
            {
                persons = EPerson.findAll(contextWrapper.getContext(), EPerson.ID);
            }
            catch(SQLException ex)
            {
                logger.error(ex,ex.getCause());
            }
            
            if(persons == null || persons.length == 0)
            {
                throw new IllegalStateException("There are no users created yet. Run 'dspace create-administrator' from /bin folder first.");
            }
            else
            {
                return persons[0];
            }
        }
        else
        {
            EPerson person = null;
            try
            {
                person = EPerson.findByEmail(contextWrapper.getContext(), email);
            }
            catch(SQLException | AuthorizeException ex)
            {
                logger.error(ex,ex.getCause());
            }
            
            if(person == null)
            {
                throw new IllegalArgumentException("There is no such user with given email. ["+email+"]");
            }
            else
            {
                return person;
            }
        }
    }
}
