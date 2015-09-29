/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.impl.io;

import cz.muni.ics.dspace5.api.CommandLine;
import cz.muni.ics.dspace5.impl.InputDataMap;
import java.util.List;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.MissingArgumentException;
import org.apache.commons.cli.MissingOptionException;
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
    protected Options options = new Options();

    private static final Logger logger = Logger.getLogger(AbstractCommandLine.class);
    private Class subClass;
    @Autowired
    protected CommandLineParser commandLineParser;
    @Autowired
    protected HelpFormatter helpFormatter;
    @Autowired
    protected InputDataMap inputDataMap;
    @Autowired
    protected ConfigurationService configurationService;

    public void setSubClass(Class subClass)
    {
        this.subClass = subClass;
    }
    
    public abstract void setOptions(List<Option> options); // lets force sublcasses implement this
    
    protected org.apache.commons.cli.CommandLine getParsedCommandLine(String[] args, Options options) throws ParseException
    {
        org.apache.commons.cli.CommandLine cmd = null;

        try
        {
            cmd = commandLineParser.parse(options, args);
        }
        catch(MissingArgumentException | MissingOptionException mae)
        {
            helpFormatter.printHelp(buildHelp(), options);
            throw mae;
        }
        catch (ParseException ex)
        {
            logger.error(ex, ex.getCause());
            throw ex;
        }

        return cmd;
    }
    
    protected void parseUser(org.apache.commons.cli.CommandLine cmd)
    {
        String username = cmd.getOptionValue("user");
        
        if(username != null)
        {
            inputDataMap.put("username", username);
        }
    }   
    
    private String buildHelp()
    {
        
        StringBuilder sb = new StringBuilder();
//        switch(subClass.getSimpleName())
//        {
//            case "Import":
//            {
                sb.append("cz.muni.ics.dspace5.");
                sb.append(subClass.getSimpleName());    
                sb.append(" --parameter=value, where parameters may be following:\n\n");
//            }
//            break;
//        }        
        
        return sb.toString();
    }
}
