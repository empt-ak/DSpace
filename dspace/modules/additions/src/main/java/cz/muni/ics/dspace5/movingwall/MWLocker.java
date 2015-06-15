/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.movingwall;

import cz.muni.ics.dspace5.exceptions.MovingWallException;
import org.dspace.content.DSpaceObject;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface MWLocker
{

    /**
     * Method is used for setting restrictions for given {@code DSpaceObject}.
     * Values are read from movingWall object.
     *
     * @param dSpaceObject to be locked
     * @param movingWall   container holding required values for locking
     *
     * @throws IllegalArgumentException if any of arguments is null
     * @throws MovingWallException      if any error occurs during method
     *                                  execution
     */
    void lockObject(DSpaceObject dSpaceObject, MovingWall movingWall) throws IllegalArgumentException, MovingWallException;

    /**
     * Method used for unsettling restriction for given {@code DSpaceObject}.
     * Values are stored in movingWall object, are used for this task.
     *
     * @param dSpaceObject to be unlocked
     * @param movingWall   container holding required values for unlocking
     *
     * @throws IllegalArgumentException if any of arguments is null
     * @throws MovingWallException      if any error occurs during method
     *                                  execution
     */
    void unlockObject(DSpaceObject dSpaceObject, MovingWall movingWall) throws IllegalArgumentException, MovingWallException;
}
