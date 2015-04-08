/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.digilib.services.impl.movingwall;

import cz.muni.ics.dspace5.exceptions.MovingWallException;
import cz.muni.ics.dspace5.movingwall.MovingWallService;
import java.sql.SQLException;
import java.util.Map;
import org.apache.log4j.Logger;
import org.dspace.authorize.AuthorizeException;
import org.dspace.authorize.AuthorizeManager;
import org.dspace.authorize.ResourcePolicy;
import org.dspace.content.DSpaceObject;
import org.dspace.content.Item;
import org.dspace.core.Constants;
import org.dspace.eperson.Group;
import org.joda.time.DateTime;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class ItemMWLocker extends AbstractLocker
{
    private static final Logger logger = Logger.getLogger(ItemMWLocker.class);

    @Override
    public void lockObject(DSpaceObject dSpaceObject, Map<String, Object> dataMap) throws IllegalArgumentException, MovingWallException
    {
        Item item = (Item) dSpaceObject;

        DateTime embargoEnd = (DateTime) dataMap.get(MovingWallService.END_DATE);
        
        if (DateTime.now().isBefore(embargoEnd))
        {
            try
            {
                Group anonGroup = dSpaceGroupService.getAnonymousGroup();

                ResourcePolicy rp = AuthorizeManager.createOrModifyPolicy(null, contextWrapper.getContext(), "embargoname", anonGroup.getID(), null, embargoEnd.toDate(), Constants.READ, "embargoryson", item);
                
                rp.update();                
                
                logger.fatal(rp.getRpName());
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
