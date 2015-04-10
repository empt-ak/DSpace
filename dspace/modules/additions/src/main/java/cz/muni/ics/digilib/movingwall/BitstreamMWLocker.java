/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.digilib.movingwall;

import cz.muni.ics.dspace5.exceptions.MovingWallException;
import cz.muni.ics.dspace5.movingwall.MovingWallService;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.dspace.authorize.AuthorizeException;
import org.dspace.authorize.AuthorizeManager;
import org.dspace.authorize.ResourcePolicy;
import org.dspace.content.DSpaceObject;
import org.dspace.core.Constants;
import org.dspace.eperson.Group;
import org.joda.time.DateTime;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class BitstreamMWLocker extends AbstractLocker
{
    private static final Logger logger = Logger.getLogger(BitstreamMWLocker.class);

    @Override
    public void lockObject(DSpaceObject dSpaceObject, Map<String, Object> dataMap) throws IllegalArgumentException, MovingWallException
    {
        DateTime embargoEnd = (DateTime) dataMap.get(MovingWallService.END_DATE);
        DateTime embargoStart = (DateTime) dataMap.get((MovingWallService.PUBLICATION_DATE));
        
        if (DateTime.now().isBefore(embargoEnd))
        {
            try
            {
                Group anonGroup = dSpaceGroupService.getAnonymousGroup();
                
                List<ResourcePolicy> currentPolicies = getPolicies(dSpaceObject);
                
                if(currentPolicies.isEmpty())
                {
                    logger.info("There are no policies. Creating new");
                    ResourcePolicy rp = AuthorizeManager.createOrModifyPolicy(null, 
                            contextWrapper.getContext(), 
                            "embargoname", 
                            anonGroup.getID(), 
                            null, 
                            embargoEnd.toDate(), 
                            Constants.READ, 
                            "embargoryson", 
                            dSpaceObject
                    );
                
                    rp.update(); 
                }
                else
                {
                    for(ResourcePolicy rp : currentPolicies)
                    {
                        logger.debug("Resource policy ID:- "+rp.getID()+" for group ID:- "+rp.getGroupID());
                        if(rp.getGroupID() == anonGroup.getID())
                        {
                            // we set restriction only to anonymous user, other users
                            // cant log in anyway.
                            
                            logger.info("Setting restriction access until "+embargoEnd.toString());
                            if(dataMap.containsKey("itemHandle"))
                            {
                                rp.setRpDescription("Bitstream embargo for item with handle @"+dataMap.get("itemHandle")+" from @"+embargoStart+" to @"+embargoEnd);
                            }
                            else
                            {
                                rp.setRpDescription("Bitstream embargo from @"+embargoStart+" to @"+embargoEnd);
                            }
                            
                            // just update values
                            rp.setStartDate(embargoEnd.toDate());
                            // we don't set the end date because if set,
                            // then it means during which period is bitstream
                            // available for 
                            
                            rp.update();
                        }
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
            unlockObject(dSpaceObject, dataMap);
        }
    }
}
