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
public class MovingWallCommandLine extends AbstractCommandLine
{

    private static final Logger logger = Logger.getLogger(MovingWallCommandLine.class);

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
                .desc("Mode of moving wall. Value can be one of following: 'lock' "
                        + "for locking everything, 'unlock' for unlocking everything or "
                        + "'auto' for locking everything that should be locker, and unlocking "
                        + "(if necessary) locked items. If this parameter is ommited then its set"
                        + " by default that mode will be 'auto'.")
                .build()
        );

        return options;
    }

    @Override
    public void process(String[] args) throws IllegalArgumentException, ParseException
    {
        org.apache.commons.cli.CommandLine cmd = getParsedCommandLine(args, getOptions());

        importDataMap.put("path", Paths.get(configurationService.getProperty("meditor.rootbase"), cmd.getOptionValue("p")));

        if (cmd.hasOption("u"))
        {
            importDataMap.put("user", cmd.getOptionValue("u"));
        }
        
        importDataMap.put("movingWallMode", cmd.getOptionValue("mode", "auto"));

        importDataMap.put("movingWallOnly", true);

        importDataMap.dump();
    }
}
