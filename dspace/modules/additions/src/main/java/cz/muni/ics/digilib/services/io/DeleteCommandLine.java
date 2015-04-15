/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.digilib.services.io;

import cz.muni.ics.dspace5.impl.io.AbstractCommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.Logger;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class DeleteCommandLine extends AbstractCommandLine
{
    private static final Logger logger = Logger.getLogger(DeleteCommandLine.class);
    
    @Override
    public Options getOptions()
    {
        Options options = new Options();
        options.addOption(
                Option.builder("m")
                        .longOpt("mode")
                        .argName("mode")
                        .hasArg(true)
                        .type(String.class)                        
                        .required(true)
                        .desc("Delete mode can has value either 'handle' or 'path'."
                                + " If set to handle then -v parameter is expected "
                                + "to be handle value, otherwise path is expected.")
                        .build()
            );
            
            logger.debug(options.getOption("m"));
            
            options.addOption(
                Option.builder("v")
                        .longOpt("value")
                        .argName("value")
                        .hasArg(true)
                        .type(String.class)                        
                        .required(true)
                        .desc("Delete mode can has value either 'handle' or 'path'."
                                + " If set to handle then -v parameter is expected "
                                + "to be handle value, otherwise path is expected.")
                        .build()
            );
            
            logger.debug(options.getOption("v"));
            
            return options;
    }

    @Override
    public void process(String[] args) throws IllegalArgumentException, ParseException
    {
        org.apache.commons.cli.CommandLine cmd = getParsedCommandLine(args, getOptions());
        
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

        importDataMap.put("mode", importMode);
        importDataMap.put("value", cmd.getOptionValue("value"));

        importDataMap.dump();
    }    
}
