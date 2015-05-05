/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.digilib.services;

import cz.muni.ics.dspace5.api.DSpaceGroupService;
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
    private String[] customMadeGroups;
    
    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private ContextWrapper contextWrapper;    

    @Override
    public Group createGroup(String groupName) throws IllegalArgumentException, GroupAlreadyExistException
    {
        Group group = getGroupByName(groupName); // throws iae
        
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
                logger.info("Group ["+groupName+"] was created.");
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

    @Override
    public Group getAnonymousGroup()
    {
        return getGroupByName(builtInGroupNames[0]);
    }
    
    private int getNumberOfGroups()
    {        
        try
        {
            return Group.findAll(contextWrapper.getContext(), Group.ID).length;
        }
        catch(SQLException ex)
        {
            logger.error(ex,ex.getCause());
        }
        
        return -1;
    }
    
    //throws dspace.cfg not found
    // issue might be thread safety ?
    
//    @PostConstruct
//    private void init() throws SQLException
//    {
//        boolean wasMissing = false;
//        try
//        {
//            contextWrapper.getContext();
//        }
//        catch(IllegalStateException ise)
//        {
//            contextWrapper.setContext(new Context());
//            contextWrapper.getContext().turnOffAuthorisationSystem();
//            wasMissing = true;
//        }
//        
//        // check for first run. when bean is created we check if there are 2 default
//        // groups. if not then do nothing, otherwise create our custom groups
//        if(getNumberOfGroups() > 1)
//        {
//            this.customMadeGroups = configurationService.getProperty("dspace.muni.groups").split(",");
//        
//            for(String group : customMadeGroups)
//            {
//                if(getGroupByName(group) == null)
//                {
//                    try
//                    {
//                        createGroup(group);
//                    }
//                    catch(GroupAlreadyExistException gae)
//                    {
//                        logger.error(gae);
//                    }
//                }
//            }
//        }        
//        
//        if(wasMissing)
//        {
//            contextWrapper.getContext().restoreAuthSystemState();
//            contextWrapper.getContext().complete();
//            contextWrapper.setContext(null);
//        }
//        else
//        {
//            contextWrapper.getContext().commit();
//        }
//    }
}
