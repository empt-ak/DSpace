/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.impl.io;

import cz.muni.ics.dspace5.core.CommandLineService;
import cz.muni.ics.dspace5.impl.InputArguments;
import java.nio.file.Paths;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.Logger;
import org.dspace.services.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Component
public class CommandLineServiceImpl implements CommandLineService
{
    private static final Logger logger = Logger.getLogger(CommandLineServiceImpl.class);
    @Autowired private CommandLineParser commandLineParser;
    @Autowired private CliOptionsImpl cliOptions;
    @Autowired private HelpFormatter helpFormatter;
    @Autowired private InputArguments inputArguments;
    @Autowired private ConfigurationService configurationService;
    

    @Override
    public void parseInput(String[] args, Mode mode) throws ParseException
    {
        CommandLine cmd = null;
        
        try
        {
            cmd = commandLineParser.parse(cliOptions.getOptions(mode), args);
        }
        catch(ParseException ex)
        {
            logger.error(ex,ex.getCause());
            helpFormatter.printHelp("posix", cliOptions.getOptions(mode));
            throw ex;
        }
        
        if(mode.equals(Mode.IMPORT))
        {
            inputArguments.put("path", Paths.get(configurationService.getProperty("meditor.rootbase"),
                    cmd.getOptionValue("p"))
            );
            
            String importMode = cmd.getOptionValue("mode", "update");
            switch(importMode)
            {
                case "update":
                    break;
                case "single":
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected -m argument. Should be [update/single] but was ["+importMode+"]");
            }   
            
            inputArguments.put("mode", importMode);
            
            inputArguments.put("failOnError", Boolean.parseBoolean(cmd.getOptionValue("foe", "false")));
            inputArguments.put("user",cmd.getOptionValue("u"));
            
            inputArguments.dump();
        }
        else
        {
            String importMode = cmd.getOptionValue("mode", "handle");
            switch(importMode)
            {
                case "handle":
                    break;
                case "path":
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected -m argument. Should be [update-all/update/single] but was ["+importMode+"]");
            }   
            
            inputArguments.put("mode", importMode);
            inputArguments.put("value", cmd.getOptionValue("value"));
            
            inputArguments.dump();
        }
    }   
}
