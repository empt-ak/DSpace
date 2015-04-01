/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.digilib.services.impl;

import cz.muni.ics.dspace5.core.DSpaceGroupService;
import cz.muni.ics.dspace5.exceptions.GroupAlreadyExistException;
import cz.muni.ics.dspace5.impl.ContextWrapper;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import org.dspace.authorize.AuthorizeException;
import org.dspace.eperson.Group;
import org.dspace.services.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Component(value = "groupService")
public class DSpaceGroupServiceImpl implements DSpaceGroupService
{
    private static final Logger logger = Logger.getLogger(DSpaceGroupServiceImpl.class);    
    private static final String[] builtInGroupNames = new String[]{"Anonymous","Administrator"};
    
    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private ContextWrapper contextWrapper;
    
    @Override
    public void createAll()
    {
        String[] groupNamesFromConfig = configurationService.getProperty("dspace.muni.groups").split(",");
        for(String groupName : groupNamesFromConfig)
        {            
            try
            {
                createGroup(groupName);
            }
            catch(GroupAlreadyExistException gae)
            {
                logger.debug(gae,gae.getCause());
            }
        }     
    }

    @Override
    public void check()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Group createGroup(String groupName) throws IllegalArgumentException, GroupAlreadyExistException
    {
        Group group = getGroupByName(groupName);
        
        if(group != null)
        {
            throw new GroupAlreadyExistException("Given group ["+groupName+"] already exist.");
        }
        else
        {
            try
            {
                group = Group.create(contextWrapper.getContext());
                group.setName(groupName);
                group.update();
                logger.info("Group ["+groupName+" was created.");
                contextWrapper.getContext().commit();
            }
            catch(SQLException | AuthorizeException ex)
            {
                logger.error(ex,ex.getCause());
            }  
            
            return group;
        }
    }

    @Override
    public Group getGroupByName(String groupName) throws IllegalArgumentException
    {
        if(groupName == null || groupName.isEmpty())
        {
            throw new IllegalArgumentException("Given group name is empty.");
        }
        
        Group group = null;
        
        try
        {
            group = Group.findByName(contextWrapper.getContext(), groupName);
        }
        catch(SQLException ex)
        {
            logger.error(ex,ex.getCause());
        }
        
        return group;
    }    

    @Override
    public void remove(String groupName) throws IllegalArgumentException
    {
        Group group = getGroupByName(groupName);
        
        if(group != null)
        {
            try
            {
                group.delete();
                contextWrapper.getContext().commit();
            }
            catch(SQLException ex)
            {
                logger.error(ex,ex.getCause());
            }            
        }
    }
}
