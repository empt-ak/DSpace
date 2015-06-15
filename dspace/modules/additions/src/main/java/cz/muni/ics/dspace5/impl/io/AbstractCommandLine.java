/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.impl.io;

import cz.muni.ics.dspace5.api.CommandLine;
import cz.muni.ics.dspace5.impl.ImportDataMap;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.Logger;
import org.dspace.services.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public abstract class AbstractCommandLine implements CommandLine
{

    private static final Logger logger = Logger.getLogger(AbstractCommandLine.class);
    @Autowired
    protected CommandLineParser commandLineParser;
    @Autowired
    protected HelpFormatter helpFormatter;
    @Autowired
    protected ImportDataMap importDataMap;
    @Autowired
    protected ConfigurationService configurationService;

    protected org.apache.commons.cli.CommandLine getParsedCommandLine(String[] args, Options options) throws ParseException
    {
        org.apache.commons.cli.CommandLine cmd = null;

        try
        {
            cmd = commandLineParser.parse(options, args);
        }
        catch (ParseException ex)
        {
            logger.error(ex, ex.getCause());
            helpFormatter.printHelp("posix", options);
            throw ex;
        }

        return cmd;
    }

    /**
     * Adds following options into command line options object:
     *
     * <table>
     * <tr>
     * <td>short option</td>
     * <td>long option</td>
     * <td>description</td>
     * </tr>
     * <tr>
     * <td>p</td>
     * <td>path</td>
     * <td>path to be imported</td>
     * </tr>
     * <tr>
     * <td>u</td>
     * <td>user</td>
     * <td>email of user, executing import</td>
     * </tr>
     * </table>
     *
     * @return {@code Option} object with options specified above.
     */
    protected Options getBasicOptions()
    {
        Options options = new Options();

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

        options.addOption(Option.builder("u")
                .longOpt("user")
                .argName("u")
                .hasArg(true)
                .type(String.class)
                .required(false)
                .desc("Flag specifying user executing import. The value is email set when creating the user.")
                .build());

        return options;
    }
}
