
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.digilaw.services.io;

import cz.muni.ics.dspace5.impl.io.AbstractCommandLine;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.Logger;

/**
 *
 * @author Vlastimil Krejcir - krejcir at ics.muni.cz
 */
public class InitCommandLine extends AbstractCommandLine
{

    private static final Logger logger = Logger.getLogger(InitCommandLine.class);


    @Override
    public void setOptions(List<Option> options)
    {
        for (Option o : options)
        {
            logger.debug("Adding option: " + o);
            this.options.addOption(o);
        }
    }

    @Override
    public void process(String[] args) throws IllegalArgumentException, ParseException
    {
       org.apache.commons.cli.CommandLine cmd = getParsedCommandLine(args, options);
       
       String initparam = cmd.getOptionValue("init");
       
       // FIX THIS! Need to be done.
       inputDataMap.put("init", "init");             
       
    }
}
