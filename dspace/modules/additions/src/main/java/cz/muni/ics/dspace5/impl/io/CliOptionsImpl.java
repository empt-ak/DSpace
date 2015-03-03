/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.impl.io;

import cz.muni.ics.dspace5.core.CliOptions;
import cz.muni.ics.dspace5.core.CommandLineService;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Component
public class CliOptionsImpl implements CliOptions
{
    private static final long serialVersionUID = 321670609088875116L;
    private static final Logger logger = Logger.getLogger(CliOptionsImpl.class);        

    @Override
    public Options getOptions(CommandLineService.Mode mode)
    {
        Options options = new Options();
        
        if(mode.equals(CommandLineService.Mode.IMPORT))
        {
            options.addOption(
                Option.builder("p")
                        .longOpt("path")
                        .argName("path")
                        .hasArg(true)
                        .type(String.class)                        
                        .required(true)
                        .desc("Path to be imported.")
                        .build()
            );

            logger.debug(options.getOption("p"));

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
            
            logger.debug(options.getOption("foe"));
        }
        else
        {
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
        }
        
        return options;
    }
}
