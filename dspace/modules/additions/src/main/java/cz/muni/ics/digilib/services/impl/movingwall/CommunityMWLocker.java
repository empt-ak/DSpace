/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.digilib.services.impl.movingwall;

import cz.muni.ics.dspace5.exceptions.MovingWallException;
import cz.muni.ics.dspace5.movingwall.MWLocker;
import java.util.Map;
import org.dspace.content.DSpaceObject;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class CommunityMWLocker implements MWLocker
{

    @Override
    public void lockObject(DSpaceObject dSpaceObject, Map<String, Object> dataMap) throws IllegalArgumentException, MovingWallException
    {
        throw new UnsupportedOperationException("Not supported yet."); //TODO
    }

    @Override
    public void unlockObject(DSpaceObject dSpaceObject, Map<String, Object> dataMap) throws IllegalArgumentException, MovingWallException
    {
        throw new UnsupportedOperationException("Not supported yet."); //TODO
    }
    
}
