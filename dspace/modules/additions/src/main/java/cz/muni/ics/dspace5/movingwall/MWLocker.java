/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.movingwall;

import cz.muni.ics.dspace5.exceptions.MovingWallException;
import cz.muni.ics.dspace5.impl.ImportDataMap;
import org.dspace.content.DSpaceObject;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface MWLocker
{

    /**
     * Calling method locks given {@code DSpaceObject} according to values
     * stored in {@code dataMap}. {@link ImportDataMap} <b>has to</b> contain following keys:
     * <ul>
     * <li> {@link MovingWallService#END_DATE}</li>
     * <li> {@link MovingWallService#PUBLICATION_DATE}</li>
     * </ul>
     * in order to lock the object. If value of {@code END_DATE} has already passed, then {@link #unlockObject(org.dspace.content.DSpaceObject) } is called.
     *
     * @param dSpaceObject on which embargo should be set
     *
     * @throws IllegalArgumentException if dSpaceObject is not as one as
     *                                  expected according to {@link DSpaceObject#getType() } return value, or if {@link MovingWallService#END_DATE} is missing, or null.
     * @throws MovingWallException      if any error while setting restriction
     *                                  to given item occurs.
     */
    void lockObject(DSpaceObject dSpaceObject) throws IllegalArgumentException, MovingWallException;

    /**
     * Calling this method unlocks given {@code DSpaceObject}. If
     * {@link ImportDataMap} is provided then it is checked whether value of
     * {@link MovingWallService#END_DATE} has already passed or not. If not then
     * unlocking is not done, otherwise object is unlocked. 
     *
     * @param dSpaceObject to be unlocked
     *
     * @throws IllegalArgumentException if dSpaceObject is not as one as
     *                                  expected according to {@link DSpaceObject#getType()
     *                                  } return value, or if {@link MovingWallService#END_DATE} is missing, or
     *                                  null.
     * @throws MovingWallException      if any error occurs during changing
     *                                  restriction on given object
     */
    void unlockObject(DSpaceObject dSpaceObject) throws IllegalArgumentException, MovingWallException;
}
