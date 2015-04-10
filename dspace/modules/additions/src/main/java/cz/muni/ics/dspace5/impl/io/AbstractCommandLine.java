/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.impl.io;

import cz.muni.ics.dspace5.api.CommandLine;
import cz.muni.ics.dspace5.impl.InputArguments;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
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
    @Autowired protected CommandLineParser commandLineParser;
    @Autowired protected HelpFormatter helpFormatter;
    @Autowired protected InputArguments inputArguments;
    @Autowired protected ConfigurationService configurationService;
    
    protected org.apache.commons.cli.CommandLine getParsedCommandLine(String[] args, Options options) throws ParseException
    {
        org.apache.commons.cli.CommandLine cmd = null;
        
        try
        {
            cmd = commandLineParser.parse(options, args);
        }
        catch(ParseException ex)
        {
            logger.error(ex,ex.getCause());
            helpFormatter.printHelp("posix", options);
            throw ex;
        }
        
        return cmd;
    }
}
