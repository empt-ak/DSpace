/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.digilib.services.impl.movingwall;

import cz.muni.ics.dspace5.core.DSpaceGroupService;
import cz.muni.ics.dspace5.exceptions.MovingWallException;
import cz.muni.ics.dspace5.impl.DSpaceTools;
import cz.muni.ics.dspace5.movingwall.MWLocker;
import java.util.Map;
import org.apache.log4j.Logger;
import org.dspace.content.DSpaceObject;
import org.dspace.core.Constants;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class ItemMWLocker implements MWLocker
{
    private static final Logger logger = Logger.getLogger(ItemMWLocker.class);
    private static final String ORIGINAL = "ORIGINAL";// TODO configurable
    @Autowired
    private DSpaceGroupService dSpaceGroupService;
    @Autowired
    private DSpaceTools dSpaceTools;

    @Override
    public void lockObject(DSpaceObject dSpaceObject, Map<String, Object> dataMap) throws IllegalArgumentException, MovingWallException
    {
        if(dSpaceObject.getType() != Constants.ITEM)
        {
            throw new IllegalArgumentException("Given object is not an item.");
        }
        
        if(dataMap != null)
        {
            if(dataMap.containsKey("embragoEndDate"))
            {
                logger.info(dSpaceObject.getHandle() +" may be under embargo with date "+ dSpaceTools.parseDate((String) dataMap.get("embargoEndDate")));
            }
        }     
    }

    @Override
    public void unlockObject(DSpaceObject dSpaceObject, Map<String, Object> dataMap) throws IllegalArgumentException, MovingWallException
    {
        throw new UnsupportedOperationException("Not supported yet."); //TODO
    }
}
