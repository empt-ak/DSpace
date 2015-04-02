/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.movingwall;

import cz.muni.ics.dspace5.exceptions.MovingWallException;
import java.util.Map;
import org.dspace.content.DSpaceObject;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface MWLocker
{
    /**
     * Method locks given {@code DSpaceObject} inside DSpace system. If object is after movingwall then it's unlocked so there is no need to explicitly call {@link #unlockObject(org.dspace.content.DSpaceObject, java.util.Map) }
     * @param dSpaceObject
     * @param dataMap
     * @throws IllegalArgumentException
     * @throws MovingWallException 
     */
    void lockObject(DSpaceObject dSpaceObject, Map<String,Object> dataMap) throws IllegalArgumentException, MovingWallException;
    void unlockObject(DSpaceObject dSpaceObject, Map<String,Object> dataMap) throws IllegalArgumentException, MovingWallException;
}
