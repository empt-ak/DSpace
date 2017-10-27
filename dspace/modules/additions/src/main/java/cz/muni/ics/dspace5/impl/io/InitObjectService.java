/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.impl.io;

import cz.muni.ics.dspace5.api.CommandLineService;
import cz.muni.ics.dspace5.api.DSpaceObjectService;
import cz.muni.ics.dspace5.api.HandleService;
import cz.muni.ics.dspace5.impl.ContextWrapper;
import cz.muni.ics.dspace5.impl.InputDataMap;
import cz.muni.ics.dspace5.api.DSpaceGroupService;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.dspace.authorize.AuthorizeException;
import org.dspace.eperson.Group;
import org.dspace.services.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class InitObjectService implements DSpaceObjectService
{
    private static final Logger logger = Logger.getLogger(DeleteObjectService.class);
    
    @Autowired
    private CommandLineService commandLineService;
    @Autowired
    private HandleService handleService;
    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private ContextWrapper contextWrapper;
    @Autowired
    private DSpaceGroupService dSpaceGroupService;
    
    
    @Override
    public void execute()
    {

        
        
        // create group that will be used to control access for embargoed 
        // bitstreams for authorized users (via Shibboleth)
        dSpaceGroupService.createEmbargoGroup();
        
    }
}