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
public class SetupCommandLine extends AbstractCommandLine
{
    private static final Logger logger = Logger.getLogger(SetupCommandLine.class);
    @Override
    public Options getOptions()
    {
        Options options = new Options();
        options.addOption(Option.builder("r")
                .longOpt("registry")
                .argName("registry")
                .hasArg(true)
                .type(String.class)
                .desc("Registry name to be updated.")
                .build()
        );
        
        return options;
    }

    @Override
    public void process(String[] args) throws IllegalArgumentException, ParseException
    {
        org.apache.commons.cli.CommandLine cmd = getParsedCommandLine(args, getOptions());
        
        if(cmd.hasOption("r"))
        {
            importDataMap.put("registry", cmd.getOptionValue("r"));
        }        
    }
    
}
