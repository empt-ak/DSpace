/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.digilaw.movingwall;

import cz.muni.ics.dspace5.impl.ContextWrapper;
import cz.muni.ics.dspace5.movingwall.MovingWall;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import org.apache.log4j.Logger;
import org.dspace.authorize.AuthorizeException;
import org.dspace.authorize.AuthorizeManager;
import org.dspace.authorize.ResourcePolicy;
import org.dspace.content.DSpaceObject;
import org.dspace.eperson.Group;
import org.dspace.core.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 * @author Vlastimil Krejcir - krejcir at ics.muni.cz
 * 
 */
@Component
public class ResourcePolicyService
{
    private static final Logger logger = Logger.getLogger(ResourcePolicyService.class);
    @Autowired
    private ContextWrapper contextWrapper;
    
    /**
     * Method recreates new policy out of old one, with new values as are given in method call parameters.
     * @param oldPolicy old policy from which values are inherited
     * @param object dspace object whose resource policy is created
     * @param movingWall moving wall object holding required values
     * @param description description of resource policy
     * @param name name of the resource policy
     * @return new resource policy with values given as method parameters
     * @throws AuthorizeException if {@link AuthorizeManager#createOrModifyPolicy(org.dspace.authorize.ResourcePolicy, org.dspace.core.Context, java.lang.String, int, org.dspace.eperson.EPerson, java.util.Date, int, java.lang.String, org.dspace.content.DSpaceObject) } throws one
     * @throws SQLException same as AuthorizeException
     */
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
                    rp.setStartDate(movingWall.getPublDate().toDate());
                    rp.setEndDate(movingWall.getEndDate().toDate());
                }
                break;
            }
        }
        
        return rp;
    }
    
    
    /**
     * Method creates READ policy for the group and object.
     * 
     * @param object DSpace object for with the policy is created
     * @param g Group for policy
     * @throws AuthorizeException if {@link AuthorizeManager#createOrModifyPolicy(org.dspace.authorize.ResourcePolicy, org.dspace.core.Context, java.lang.String, int, org.dspace.eperson.EPerson, java.util.Date, int, java.lang.String, org.dspace.content.DSpaceObject) } throws one
     * @throws SQLException same as AuthorizeException
     */
    public void createReadGroupPolicy(DSpaceObject object, Group group) throws AuthorizeException, SQLException {
        
        AuthorizeManager.addPolicy(
                contextWrapper.getContext(), 
                object,
                Constants.READ,
                group,
                ResourcePolicy.TYPE_CUSTOM);
    } 
    
    public void removeReadGroupPolicy(DSpaceObject object, Group group) throws SQLException {
        
        AuthorizeManager.removeGroupPolicies(contextWrapper.getContext(),
                object,
                group);
    }
    
    /**
     * Method returns policies for given {@code DSpaceObject} in form of list. If there are none, or exception occurs then empty list is returned.
     * @param dSpaceObject of which policies should be obtained
     * @return list of policies, or empty list if there are none.
     */
    public List<ResourcePolicy> getPolicies(DSpaceObject dSpaceObject)
    {
        try
        {
            return AuthorizeManager.getPolicies(contextWrapper.getContext(), dSpaceObject);
        }
        catch(SQLException ex)
        {
            logger.error(ex,ex.getCause());
        }
        
        return Collections.emptyList();
    }
}
