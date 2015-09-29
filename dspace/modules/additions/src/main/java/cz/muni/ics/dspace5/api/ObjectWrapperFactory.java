/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.api;

import cz.muni.ics.dspace5.api.module.ObjectWrapper;
import java.nio.file.Path;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface ObjectWrapperFactory
{

    /**
     * Method creates ObjectWrapper out of given path. Only path and level is
     * set, according to given {@code isVolume} value.
     *
     * @param path     target object in folder hierarchy
     * @param isVolume flag specifying whether issue path should be resolved
     *                 into volume
     * @param handle   handle to be set
     *
     * @return ObjectWrapper pointing to given path
     */
    ObjectWrapper createObjectWrapper(Path path, boolean isVolume, String handle);

    /**
     * Calling method creates {@code ObjectWrapper} specified by input
     * parameters.
     *
     * @param path                  path of object
     * @param isVolume              flag specifying whether on path is volume or
     *                              not
     * @param attachHandle          if method should set {@code handle} for
     *                              result
     * @param createHandleIfMissing if handle should be created when missing
     *
     * @return ObjectWrapper created according given parameters
     *
     * @see #createObjectWrapper(java.nio.file.Path, boolean, java.lang.String)
     * @see #createObjectWrapper()
     */
    ObjectWrapper createObjectWrapper(Path path, boolean isVolume, boolean attachHandle, boolean createHandleIfMissing);

    ObjectWrapper createRootObjectWrapper(Path path);
    
}
