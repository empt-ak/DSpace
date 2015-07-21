/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dmlcz5.services.io;

import cz.muni.ics.dspace5.impl.io.AbstractCommandLine;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.Logger;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class ImportCommandLine extends AbstractCommandLine
{
    private static final Logger logger = Logger.getLogger(ImportCommandLine.class);
    
    private Set<String> movingWallOptions;

    public void setMovingWallOptions(Set<String> movingWallOptions)
    {
        this.movingWallOptions = movingWallOptions;
    }

    @Override
    public void setOptions(List<Option> options)
    {
        for(Option o : options)
        {
            logger.debug("Adding option: "+o);
            this.options.addOption(o);
        }
    }
    
    @Override
    public void process(String[] args) throws IllegalArgumentException, ParseException
    {
        org.apache.commons.cli.CommandLine cmd = getParsedCommandLine(args, options);
        
        String method = cmd.getOptionValue("method", "update");
        
        switch(method)
        {
            case "update": break;
            case "single": break;
            default:
                throw new IllegalArgumentException("Unexpected --method argument. Should be [update/single] but was ["+method+"]");
        } 
        
        inputDataMap.put("method", method);
        
        String value = cmd.getOptionValue("value");
        
        if(value != null)
        {
            inputDataMap.put("value", Paths.get(configurationService.getProperty("meditor.rootbase"),value));
        }
        else
        {
            throw new IllegalArgumentException("Value is not set should be any path in ME structure.");
        }
        
        super.parseUser(cmd);
        
        
        if(cmd.hasOption("check"))
        {
            inputDataMap.put("check", true);
        }
        
        String movingWall = cmd.getOptionValue("movingwall", "off");
        
        if(!movingWallOptions.contains(movingWall))
        {
            throw new IllegalArgumentException("--movingwall has invalid value. Should be lock,unlock,auto or off but was ["+movingWall+"].");
        }
        else
        {
            inputDataMap.put("mwmethod", movingWall);
        }
    }
}
