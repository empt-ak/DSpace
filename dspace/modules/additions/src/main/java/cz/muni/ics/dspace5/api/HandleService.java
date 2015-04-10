/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.api;

import java.nio.file.Path;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface HandleService
{

    /**
     * Method obtains handle value for given object on given path. If it does
     * not exists, it is created.
     *
     * @param path            to object of witch we would like to load handle
     * @param createIfMissing flag specifying whether missing handle should be
     *                        created or not. This comes handy when we are
     *                        trying to read virtual issue, which wasn't
     *                        imported yet. For such case we don't want to
     *                        create handle (if it does not exist).
     *
     * @return handle for given path in form of <b>prefix/suffix</b>
     *
     * @throws IllegalArgumentException if any of arguments is invalid
     */
    String getHandleForPath(Path path, boolean createIfMissing) throws IllegalArgumentException;

    //@meditor: no longer needed
    /**
     * Method obtains handle for volume. If handle does not exist it is created
     * in each collection.
     *
     * @param path    path of issue from which volume is detected
     *
     * @return handle for given volume in form of <b>prefix/suffix</b>
     *
     * @throws IllegalArgumentException if any of arguments is invalid
     */
    String getVolumeHandle(Path path) throws IllegalArgumentException;
}
