/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.impl.io;

import cz.muni.ics.dspace5.api.DSpaceExecutor;
import cz.muni.ics.dspace5.api.CommandLineService;
import cz.muni.ics.dspace5.api.DSpaceObjectService;
import cz.muni.ics.dspace5.impl.ContextWrapper;
import cz.muni.ics.dspace5.impl.InputDataMap;
import java.util.Map;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class DSpaceExecutorImpl implements DSpaceExecutor
{
    private static final Logger logger = Logger.getLogger(DSpaceExecutorImpl.class);
    
    @Autowired
    private CommandLineService commandLineService; 
    @Autowired
    private InputDataMap inputDataMap;
    @Autowired
    private ContextWrapper contextWrapper;
    
    private Map<String,DSpaceObjectService> dspaceServices;

    public void setDspaceServices(Map<String, DSpaceObjectService> dspaceServices)
    {
        this.dspaceServices = dspaceServices;
    }

    @Override
    public void execute(String mode, String[] args)
    {
        boolean error = false;

        try
        {
            commandLineService.getCommandLine(mode).process(args);
        }
        catch (ParseException pe)
        {
            error = true;
        }

        if (!error)
        {
            contextWrapper.setEperson(inputDataMap.getValue("user"));
            
            inputDataMap.dump();
            
            dspaceServices.get(mode).execute();
        }
    }
    
}
