/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dmlcz5.services.io;

import cz.muni.ics.dspace5.impl.io.AbstractCommandLine;
import java.util.List;
import org.apache.commons.cli.Option;
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
    public void setOptions(List<Option> options)
    {
        for(Option o : options)
        {
            logger.debug("Adding option: "+o);
            this.options.addOption(o);
        }
    }
    
    @Override
    public void process(String[] args) throws IllegalArgumentException, ParseException
    {
        org.apache.commons.cli.CommandLine cmd = getParsedCommandLine(args, options);
        
        String method = cmd.getOptionValue("method", "handle");        
        
        switch(method)
        {
            case "handle":
                break;
            case "path":
                break;
            default: throw  new IllegalArgumentException();
        }
        
        inputDataMap.put("method", method);        
        
        String value = cmd.getOptionValue("value");
        
        if(value == null)
        {
            throw new IllegalArgumentException();
        }
        else
        {
            inputDataMap.put("value", value);
        }
    }    
}
