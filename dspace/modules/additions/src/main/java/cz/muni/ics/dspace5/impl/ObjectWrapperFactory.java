/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.impl;

import cz.muni.ics.dspace5.core.HandleService;
import cz.muni.ics.dspace5.core.ObjectWrapper;
import java.nio.file.Path;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author Dominik Szalai - emptulik at gmail.com
 */
public abstract class ObjectWrapperFactory
{

    @Autowired
    private DSpaceTools dSpaceTools;
    @Autowired
    private HandleService handleService;

    /**
     * Method used by spring to create {@code prototype} bean. Calling this
     * method creates plain object without any values set.
     *
     * @return ObjectWrapper with set dependencies.
     */
    public abstract ObjectWrapper createObjectWrapper();

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
    public ObjectWrapper createObjectWrapper(Path path, boolean isVolume, String handle)
    {
        ObjectWrapper ow = createObjectWrapper();
        if (isVolume)
        {
            ow.setPath(dSpaceTools.getRoot(path).resolve(dSpaceTools.getVolumeNumber(path) + ".xml"));
            ow.setLevel(ObjectWrapper.LEVEL.SUBCOM);

        }
        else
        {
            int level = dSpaceTools.getPathLevel(path);

            switch (level)
            {
                case 0:
                    ow.setLevel(ObjectWrapper.LEVEL.COM);
                    break;
                case 1:
                    ow.setLevel(ObjectWrapper.LEVEL.COL);
                    break;
                case 2:
                    ow.setLevel(ObjectWrapper.LEVEL.ITEM);
                    break;
                default:
                    throw new IllegalArgumentException("Given level is out of range. @path [" + path + "] @level " + level);
            }

            ow.setPath(path);
        }
        
        ow.setHandle(handle);

        return ow;
    }
    
    //todo in future maybe refactor
    /**
     * Calling method creates {@code ObjectWrapper} specified by input parameters.
     * @param path path of object
     * @param isVolume flag specifying whether on path is volume or not
     * @param attachHandle if method should set {@code handle} for result
     * @param createHandleIfMissing if handle should be created when missing
     * @return ObjectWrapper created according given parameters
     * @see #createObjectWrapper(java.nio.file.Path, boolean, java.lang.String) 
     * @see #createObjectWrapper() 
     */
    public ObjectWrapper createObjectWrapper(Path path, boolean isVolume, boolean attachHandle, boolean createHandleIfMissing)
    {
        if(attachHandle)
        {
            if(isVolume)
            {
                return createObjectWrapper(path, isVolume, handleService.getVolumeHandle(path));
            }
            else
            {
                return createObjectWrapper(path, isVolume, handleService.getHandleForPath(path, createHandleIfMissing));
            }
        }
        else
        {
            return createObjectWrapper(path, isVolume, null);
        }        
    }
}
