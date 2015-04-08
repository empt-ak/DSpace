/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.digilib.services.impl.movingwall;

import cz.muni.ics.dspace5.core.DSpaceGroupService;
import cz.muni.ics.dspace5.exceptions.MovingWallException;
import cz.muni.ics.dspace5.impl.ContextWrapper;
import cz.muni.ics.dspace5.impl.DSpaceTools;
import cz.muni.ics.dspace5.movingwall.MWLocker;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.dspace.authorize.AuthorizeException;
import org.dspace.authorize.AuthorizeManager;
import org.dspace.authorize.ResourcePolicy;
import org.dspace.content.DSpaceObject;
import org.dspace.eperson.Group;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public abstract class AbstractLocker implements MWLocker
{

    private static final Logger logger = Logger.getLogger(AbstractLocker.class);
    @Autowired
    protected DSpaceGroupService dSpaceGroupService;
    @Autowired
    protected DSpaceTools dSpaceTools;
    @Autowired
    protected ContextWrapper contextWrapper;

    @Override
    public void unlockObject(DSpaceObject dSpaceObject, Map<String, Object> dataMap) throws IllegalArgumentException, MovingWallException
    {
        try
        {
            List<ResourcePolicy> policies = getPolicies(dSpaceObject);
            
            if(policies.isEmpty())
            {
                logger.error("policies should not be empty?");
            }
            else
            {
                Group anon = dSpaceGroupService.getAnonymousGroup();
                
                for(ResourcePolicy rp : policies)
                {
                    if(rp.getGroupID() == anon.getID())
                    {
                        rp.setEndDate(null);
                        rp.setStartDate(null);
                        
                        try
                        {
                            rp.update();
                        }
                        catch(AuthorizeException ex)
                        {
                            logger.error(ex);
                        }                        
                    }
                }
            }
        }
        catch (SQLException ex)
        {
            throw new MovingWallException(ex.getMessage(), ex);
        }
    }

    /**
     * Method returns {@code List} of all policies for given
     * {@code DSpaceObject}. If there are none policies then empty List is
     * returned.
     *
     * @param dSpaceObject object of which policies are to be obtained
     *
     * @return list of policies, or empty list if there are none.
     */
    protected List<ResourcePolicy> getPolicies(DSpaceObject dSpaceObject)
    {
        try
        {
            return AuthorizeManager.getPolicies(contextWrapper.getContext(), dSpaceObject);
        }
        catch (SQLException ex)
        {
            logger.error(ex);
        }

        return Collections.emptyList();
    }
}
