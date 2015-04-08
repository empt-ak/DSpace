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
     * Calling method locks given {@code DSpaceObject} according to values
     * stored in {@code dataMap}. dataMap <b>has to</b> contain following keys:
     * <ul>
     * <li> {@link MovingWallService#END_DATE}</li>
     * <li> {@link MovingWallService#PUBLICATION_DATE}</li>
     * </ul>
     * in order to lock the object. If value of {@code END_DATE} has already passed, then {@link #unlockObject(org.dspace.content.DSpaceObject, java.util.Map) } is called.
     *
     * @param dSpaceObject on which embargo should be set
     * @param dataMap      important values required for setting the embargo
     *
     * @throws IllegalArgumentException if dSpaceObject is not as one as
     *                                  expected according to {@link DSpaceObject#getType() } return value, or if {@link MovingWallService#END_DATE} is missing, or null.
     * @throws MovingWallException      if any error while setting restriction
     *                                  to given item occurs.
     */
    void lockObject(DSpaceObject dSpaceObject, Map<String, Object> dataMap) throws IllegalArgumentException, MovingWallException;

    /**
     * Calling this method unlocks given {@code DSpaceObject}. If
     * {@code dataMap} is provided then it is checked whether value of
     * {@link MovingWallService#END_DATE} has already passed or not. If not then
     * unlocking is not done, otherwise object is unlocked. If no dataMap is
     * provided then object is unlocked right away.
     *
     * @param dSpaceObject to be unlocked
     * @param dataMap      containing key values for unlocking dspaceobject
     *
     * @throws IllegalArgumentException if dSpaceObject is not as one as
     *                                  expected according to {@link DSpaceObject#getType()
     *                                  } return value, or if {@link MovingWallService#END_DATE} is missing, or
     *                                  null.
     * @throws MovingWallException      if any error occurs during changing
     *                                  restriction on given object
     */
    void unlockObject(DSpaceObject dSpaceObject, Map<String, Object> dataMap) throws IllegalArgumentException, MovingWallException;
}
