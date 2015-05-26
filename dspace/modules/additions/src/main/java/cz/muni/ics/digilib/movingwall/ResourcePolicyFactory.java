/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.digilib.movingwall;

import cz.muni.ics.dspace5.impl.ContextWrapper;
import cz.muni.ics.dspace5.movingwall.MovingWall;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import org.dspace.authorize.AuthorizeException;
import org.dspace.authorize.AuthorizeManager;
import org.dspace.authorize.ResourcePolicy;
import org.dspace.content.DSpaceObject;
import org.dspace.core.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Component
public class ResourcePolicyFactory
{
    private static final Logger logger = Logger.getLogger(ResourcePolicyFactory.class);
    @Autowired
    private ContextWrapper contextWrapper;
    
    
    public ResourcePolicy createResourcePolicy(ResourcePolicy oldPolicy, DSpaceObject object, MovingWall movingWall, String description, String name) throws AuthorizeException, SQLException
    {
        ResourcePolicy rp = AuthorizeManager.createOrModifyPolicy(
                oldPolicy,
                contextWrapper.getContext(), 
                movingWall.getRightsAccess() != null ? movingWall.getRightsAccess() : "embargo",
                oldPolicy.getGroupID(), 
                contextWrapper.getContext().getCurrentUser(), 
                movingWall.getEndDate().toDate(), 
                Constants.READ,
                description,
                object);
        
        rp.update();
        
        if(movingWall.getRightsAccess() != null)
        {
            switch(movingWall.getRightsAccess())
            {
                case "closedaccess":
                {
                    logger.fatal("Closed access - setting time");
                    rp.setStartDate(movingWall.getPublDate().toDate());
                    rp.setEndDate(movingWall.getEndDate().toDate());
                }
                break;
            }
        }
        
        return rp;
    }
}
