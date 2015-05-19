/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.impl;

import cz.muni.ics.dspace5.api.CommandLineService;
import cz.muni.ics.dspace5.api.SetupService;
import cz.muni.ics.dspace5.metadata.RegistryService;
import java.sql.SQLException;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.Logger;
import org.dspace.core.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Component(value = "setupService")
public class SetupServiceImpl implements SetupService
{
    private static final Logger logger = Logger.getLogger(SetupServiceImpl.class);
    
    @Autowired
    private CommandLineService commandLineService;
    @Autowired
    private ContextWrapper contextWrapper;
    @Autowired
    private DSpaceTools dSpaceTools;
    @Autowired
    private ImportDataMap importDataMap;
    @Autowired
    private RegistryService registryService;
    
    @Override
    public void execute(String[] args)
    {
        boolean error = false;
        
        try
        {
            commandLineService.getCommandLine("setup").process(args);
        }
        catch(ParseException pe)
        {
            error = true;
        }
        
        if(importDataMap.containsKey("registry"))
        {
            registryService.updateRegistrySchema(importDataMap.getValue("registry"));
        }
        
        
        if(!error)
        {
            try
            {
                contextWrapper.setContext(new Context());
                contextWrapper.getContext().setCurrentUser(dSpaceTools.findEPerson(importDataMap.getValue("user")));
            }
            catch(SQLException ex)
            {
                logger.info(ex,ex.getCause());
            }
        }
    }
    
}
