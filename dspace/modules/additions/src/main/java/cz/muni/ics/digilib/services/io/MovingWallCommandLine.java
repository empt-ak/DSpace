/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.digilib.services.io;

import cz.muni.ics.dspace5.impl.io.AbstractCommandLine;
import java.nio.file.Paths;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.Logger;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class MovingWallCommandLine extends AbstractCommandLine
{
    private static final Logger logger = Logger.getLogger(MovingWallCommandLine.class);
    
    @Override
    public Options getOptions()
    {
        Options options = getBasicOptions();
        
        return options;
    }

    @Override
    public void process(String[] args) throws IllegalArgumentException, ParseException
    {
        org.apache.commons.cli.CommandLine cmd = getParsedCommandLine(args, getOptions());
        
        inputArguments.put("path", Paths.get(configurationService.getProperty("meditor.rootbase"),cmd.getOptionValue("p")));
        
        if(cmd.hasOption("u"))
        {
            inputArguments.put("user", cmd.getOptionValue("u"));
        }
        
        inputArguments.put("movingWallOnly", true);
        
        inputArguments.dump();
    }
    
}
