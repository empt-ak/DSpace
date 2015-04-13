/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.digilib.services.io;

import cz.muni.ics.dspace5.impl.io.AbstractCommandLine;
import java.nio.file.Paths;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.Logger;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class ImportCommandLine extends AbstractCommandLine
{
    private static final Logger logger = Logger.getLogger(ImportCommandLine.class);

    @Override
    public Options getOptions()
    {
        Options options = getBasicOptions();

        options.addOption(
                Option.builder("m")
                        .longOpt("mode")
                        .argName("mode")
                        .hasArg(true)
                        .type(String.class)
                        .required(false)
                        .desc("Mode used on import. Possible values are : 'update' "
                                + "which updates everything on given path and below, "
                                + "'single' which updates only given path. Default "
                                + "value is 'update'")
                        .build()
        );

        logger.debug(options.getOption("m"));

        options.addOption(
                Option.builder("foe")
                        .longOpt("fail-on-error")
                        .argName("foe")
                        .hasArg(true)
                        .type(Boolean.class)
                        .required(false)
                        .desc("Flag specifying whether import should fail, if there is any error, or can continue. Default value is false.")
                        .build()
        );
        
        options.addOption(
                Option.builder("mw")
                    .longOpt("movingwall")
                    .argName("mw")
                    .hasArg(false)
                    .required(false)
                    .desc("Flag specifying whether import should be done alongside with import.")
                    .build()
        );

        logger.debug(options.getOption("foe"));
        
        return options;
    }

    @Override
    public void process(String[] args) throws IllegalArgumentException, ParseException
    {
        org.apache.commons.cli.CommandLine cmd = getParsedCommandLine(args, getOptions());
        
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

        if(cmd.hasOption("u"))
        {
            inputArguments.put("user",cmd.getOptionValue("u"));
        }
        
        if(cmd.hasOption("mw"))
        {
            inputArguments.put("movingwall", true);
        }

        inputArguments.dump();
    }
}
