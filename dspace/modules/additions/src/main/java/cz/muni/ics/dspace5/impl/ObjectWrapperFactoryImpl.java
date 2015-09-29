/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.impl;

import cz.muni.ics.dspace5.api.HandleService;
import cz.muni.ics.dspace5.api.ObjectWrapperFactory;
import cz.muni.ics.dspace5.api.module.ObjectWrapper;
import java.nio.file.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Component
public abstract class ObjectWrapperFactoryImpl implements ObjectWrapperFactory
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

    @Override
    public ObjectWrapper createRootObjectWrapper(Path path)
    {
        ObjectWrapper ow = createObjectWrapper();
        ow.setPath(path);

        return ow;
    }
    
    @Override
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
    @Override
    public ObjectWrapper createObjectWrapper(Path path, boolean isVolume, boolean attachHandle, boolean createHandleIfMissing)
    {
        if (attachHandle)
        {
            if (isVolume)
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
