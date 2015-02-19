/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.impl;

import cz.muni.ics.dspace5.core.HandleService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
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
    private DSpaceTools dspaceTools;
    @Autowired
    private ConfigurationService configurationService;
    
    
    private static final Logger logger = Logger.getLogger(HandleServiceImpl.class);
    
    @Override
    public synchronized String getHandleForPath(Path path)
    {
        init();
        
        Path handlePath = path.resolve(handleFile);
        
        if(Files.exists(handlePath))
        {
            try
            {
                String suffix = new String(Files.readAllBytes(handlePath));
            
                return handlePrefix+suffix;
            }
            catch(IOException ex)
            {
                logger.error(ex, ex.getCause());
                //TODO: Fail on error via system.exit ??
            }
            
            return StringUtils.EMPTY;            
        }
        else
        {
            return handlePrefix+createAndProvideNewHandle(path);
        }
    }
    
    /**
     * Method ensures that new id is created. Below is workflow used by method:
     * <ol>
     * <li>if file with global id exists then read it</li>
     * <li>otherwise create it and write new id 0</li>
     * <li>increment current id by 1</li>
     * <li>write to global file</li>
     * <li>write to single file for given object</li>
     * <li>return new value</li>
     * </ol>
     * 
     * TODO: volume handling
     * @param path
     * @return 
     */
    private String createAndProvideNewHandle(Path path)
    {
        
        Long lastId = null;
        try
        {
            lastId = Long.valueOf(new String(Files.readAllBytes(Paths.get(globalHandleFile))));
        }
        catch(IOException ex)
        {
            logger.warn("No global handle file found.");
        }
        
        // first time run~>no id yet
        if(lastId == null)
        {
            lastId = Long.valueOf("0");            
            
            try
            {
                Files.write(Paths.get(globalHandleFile), dspaceTools.longToByte(lastId), StandardOpenOption.CREATE_NEW);
            }
            catch(IOException ex)
            {
                logger.fatal(ex);
            }
        }
        
        //increment id and save it to global file first
        Long newId = lastId + 1;
        try
        {
            Files.write(Paths.get(globalHandleFile), dspaceTools.longToByte(newId), StandardOpenOption.CREATE_NEW);
        }
        catch(IOException ex)
        {
            logger.fatal(ex);
        }
        
        // save for given object
        // TODO: handle 
        try
        {
            Files.write(path.resolve(handleFile), dspaceTools.longToByte(newId), StandardOpenOption.CREATE_NEW);
        }
        catch(IOException ex)
        {
            logger.fatal(ex);
        }
        
        return newId.toString();
    }
    
    private void init()
    {
        if(globalHandleFile == null)
        {
            globalHandleFile = configurationService.getProperty("meditor.handle.file.global");
        }
        
        if(handleFile == null)
        {
            handleFile = configurationService.getProperty("meditor.handle.file");
        }
        
        if(handlePrefix == null)
        {
            handlePrefix = configurationService.getProperty("handle.prefix")+"/";
        }
    }

    @Override
    public String getVolumeHandle(Path path)
    {
        return handlePrefix+"12315zz";
    }
}
