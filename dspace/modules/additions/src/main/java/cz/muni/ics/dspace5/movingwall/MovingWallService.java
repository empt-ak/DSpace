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
public interface MovingWallService
{

    /**
     * Key value for datamap specifying under which key is the value of
     * publication date.
     */
    public static final String PUBLICATION_DATE = "embargoPublicationDate";

    /**
     * Key value for datamap specifying under which key is the value of end
     * date.
     */
    public static final String END_DATE = "embargoEndDate";

    /**
     * Key value for datamap specifying under which key is the value of moving
     * wall.
     */
    public static final String MOVING_WALL = "embargoMovingWall";

    /**
     * Method used for direct execution of moving wall on given path or handle.
     * Path is extracted from command line arguments by any way user is familiar
     * with. Method must recreate (or pass required values) for given
     * path/handle. So if path/handle for item is found then we have to read
     * values from Community and Collection in order to set object under
     * embargo.
     *
     * @param args values passed to service in order to execute moving wall
     *             process.
     */
    void execute(String[] args);

    /**
     * Method locks given {@code DSpaceObject} according to its implementation
     * based on {@link MWLockerProvider}.
     *
     * @param dSpaceObject to be locked
     * @param dataMap      values required for setting given object under
     *                     embargo.
     *
     * @throws IllegalArgumentException if given dSpaceObject is not supported.
     * @throws MovingWallException      if any error occurs during setting
     *                                  restricted access to dspace object
     * @see MWLocker#lockObject(org.dspace.content.DSpaceObject, java.util.Map)
     * @see MWLockerProvider#getLocker(java.lang.Class)
     */
    void lock(DSpaceObject dSpaceObject, Map<String, Object> dataMap) throws IllegalArgumentException, MovingWallException;

    /**
     * Method unlocks given {@code DSpaceObject} according to its implementation
     * based on {@link MWLockerProvider}.
     *
     * @param dSpaceObject to be unlocked
     * @param dataMap      values required for unsetting given object from
     *                     embargo.
     *
     * @throws IllegalArgumentException if given dSpaceObject is not supported.
     * @throws MovingWallException      if any error occurs during setting
     *                                  restricted access to dspace object
     * @see MWLocker#lockObject(org.dspace.content.DSpaceObject, java.util.Map)
     * @see MWLockerProvider#getLocker(java.lang.Class)
     */
    void unlock(DSpaceObject dSpaceObject, Map<String, Object> dataMap) throws IllegalArgumentException, MovingWallException;
}
