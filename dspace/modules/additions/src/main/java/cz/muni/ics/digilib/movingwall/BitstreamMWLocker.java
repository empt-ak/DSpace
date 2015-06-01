/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.digilib.movingwall;

import cz.muni.ics.dspace5.exceptions.MovingWallException;
import cz.muni.ics.dspace5.movingwall.MovingWall;
import java.sql.SQLException;
import java.util.List;
import org.apache.log4j.Logger;
import org.dspace.authorize.AuthorizeException;
import org.dspace.authorize.ResourcePolicy;
import org.dspace.content.DSpaceObject;
import org.dspace.eperson.Group;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class BitstreamMWLocker extends AbstractLocker
{

    private static final Logger logger = Logger.getLogger(BitstreamMWLocker.class);
    
    @Autowired
    private ResourcePolicyFactory resourcePolicyFactory;

    @Override
    public void lockObject(DSpaceObject dSpaceObject, MovingWall movingWall) throws IllegalArgumentException, MovingWallException
    {
        logger.info("Following MW obtained: "+movingWall);
        DateTime embargoEnd = movingWall.getEndDate();
        DateTime embargoStart = movingWall.getPublDate();

        if (!movingWall.ignore() && (DateTime.now().isBefore(embargoEnd) && !movingWall.isOpenAccess()) || 
                (movingWall.getRightsAccess() != null && !"openaccess".equals(movingWall.getRightsAccess()))
            )
        {
            try
            {
                Group anonGroup = dSpaceGroupService.getAnonymousGroup();

                List<ResourcePolicy> currentPolicies = getPolicies(dSpaceObject);

                for (ResourcePolicy rp : currentPolicies)
                {
                    logger.debug("Resource policy ID:- " + rp.getID() + " for group ID:- " + rp.getGroupID());
                    if (rp.getGroupID() == anonGroup.getID())
                    {
                        // we set restriction only to anonymous user, other users
                        // cant log in anyway.
                        resourcePolicyFactory.createResourcePolicy(rp, dSpaceObject, movingWall, "Bitstream embargo from @" + dSpaceTools.simpleFormatTime(embargoStart) + " to @" + dSpaceTools.simpleFormatTime(embargoEnd), "embargo")
                                .update();

                        rp.update();
                    }
                }
            }
            catch (SQLException | AuthorizeException ex)
            {
                logger.error(ex);
            }
        }
        else
        {
            super.unlockObject(dSpaceObject, movingWall);
        }
    }
}
