/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dmlcz5.services.io;

import cz.muni.ics.dspace5.api.HandleService;
import cz.muni.ics.dspace5.impl.ContextWrapper;
import cz.muni.ics.dspace5.impl.DSpaceTools;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.lang3.StringUtils;
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
    private boolean mapChanged = false;
    
    private Map<Path,Long> handleMap;

    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private ContextWrapper contextWrapper;
    @Autowired
    private DSpaceTools dSpaceTools;

    private static final Logger logger = Logger.getLogger(HandleServiceImpl.class);

    @Override
    public String getHandleForPath(Path path, boolean createIfMissing) throws IllegalArgumentException
    {
        logger.fatal("looking handle for path"+path);
        String result = null;
        
        Path lookupPath = null;
        
        if(isRetro(path))
        {
            logger.info("Path "+path+" is retro. Path needs to be modified.");
            lookupPath = fixRetroPath(path);
            logger.info("Retro path has been fixed into: "+lookupPath);
        }
        else
        {
            lookupPath = path;
        }
        
        logger.info("hledame id pro: " + lookupPath.toString());
        
        if(handleMap.containsKey(lookupPath))
        {
            result = handlePrefix+handleMap.get(lookupPath);
        }
        else if(createIfMissing)
        {
            throw new RuntimeException("k$"+lookupPath);
        }
        else
        {
            throw new RuntimeException("meh");
        }
        
        return result;
    }

    @Override
    public String getVolumeHandle(Path path) throws IllegalArgumentException
    {        
        //volume is passed as first issue
        //but volume as path is assigned after handle is assigned
        //therefore we need to get volume number first, then let
        //the objectwrapper factory assign path.
        return getHandleForPath(path.getParent().resolve(dSpaceTools.getVolumeNumber(path)), true);
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
        mapChanged = true;
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
        
        //meditor.rootbase=/media/emptak/bb31336e-5bd9-4985-b588-20819fdcbf44/dmlcz-data/
        //meditor.handle.file.global=${meditor.rootbase}/dspace_id_last.txt
        if (globalHandleFile == null)
        {
//            logger.trace("Setting global handle file.");
//            
//            for(Object o : System.getProperties().keySet())
//            {
//                logger.fatal(o+System.getProperty((String) o));
//            }
            
            globalHandleFile = configurationService.getProperty("meditor.handle.file.global");
        }

//        if (handleFile == null)
//        {
//            logger.trace("Setting handle file.");
//            handleFile = configurationService.getProperty("meditor.handle.file");
//        }

        if (handlePrefix == null)
        {
            logger.trace("Setting handle prefix.");
            handlePrefix = configurationService.getProperty("handle.prefix") + "/";
        }
//        logger.fatal("$"+Files.exists(Paths.get(globalHandleFile)));
//        try
//        {
//            logger.fatal("$"+Files.getOwner(Paths.get(globalHandleFile)));
//        }
//        catch(IOException ex)
//        {
//            logger.fatal("e$"+ex);
//        }
//        try
//        {
//            logger.fatal("$"+Files.getPosixFilePermissions(Paths.get(globalHandleFile)));
//        }
//        catch(IOException ex)
//        {
//            logger.fatal("e#"+ex);
//        }
        
        
        handleMap = new TreeMap<>();
        
        try(BufferedReader br = Files.newBufferedReader(Paths.get(globalHandleFile), Charset.forName("UTF-8")))
        {
            for(String line; (line = br.readLine()) != null; )
            {
                String[] data = line.split("\\t",2);
                handleMap.put(Paths.get(configurationService.getProperty("meditor.rootbase"),data[1]), Long.valueOf(data[0]));
            }
        }
        catch(AccessDeniedException ade)
        {
            logger.info(ade);
        }
        catch(IOException ex)
        {
            logger.fatal(ex);
            throw new RuntimeException(ex);
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
            logger.error(ex, ex.getCause());
        }

        return null;
    }
    
    @PreDestroy
    private void destroy()
    {
        if(mapChanged)
        {
            try(Writer writer = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(Paths.get(globalHandleFile).toString(),false),
                            Charset.forName("UTF-8"))
                    )
            )
            {
                for(Path p : handleMap.keySet())
                {
                    writer.write(handleMap.get(p)+"\t"+dSpaceTools.getOnlyMEPath(p)+"\n");
                }
            }
            catch(IOException ex)
            {
                logger.fatal(ex);
            }
        }
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    private boolean isRetro(Path p)
    {
        return dSpaceTools.getOnlyMEPath(p).getName(1).toString().endsWith("Retro");
    }
    
    private Path fixRetroPath(Path p)
    {
        Path result = dSpaceTools.getMeditorRootPath();
        Path mePath = dSpaceTools.getOnlyMEPath(p);
        result = result.resolve(mePath.getName(0));
        result = result.resolve(StringUtils.substringBefore(mePath.getName(1).toString(),"Retro"));
        if(mePath.getNameCount()>2){
            result = result.resolve(mePath.subpath(2,mePath.getNameCount()));
        }

        return result;
    }
}
