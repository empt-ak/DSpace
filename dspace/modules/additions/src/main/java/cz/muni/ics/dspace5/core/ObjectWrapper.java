/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.core;

import java.nio.file.Path;
import java.util.List;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface ObjectWrapper
{

    /**
     * Method sets path for this object wrapper
     *
     * @param path target path, location where is this object stored
     */
    void setPath(Path path);

    /**
     * Method sets handle for this object wrapper
     *
     * @param handle handle of this object
     */
    void setHandle(String handle);

    /**
     * Method sets object for this object wrapper
     *
     * @param object already converted object from given path. if we are using
     *               domain import (path is converted into object via XML
     *               mapping) then we can store object in this parameter. This
     *               parameter may be null.
     */
    void setObject(Object object);

    /**
     * Method sets flag whether this objectwrapper is volume or not
     *
     * @param isVolume
     */
    void setVolume(boolean isVolume);

    /**
     * Method adds children to this wrapper.
     *
     * @param children children of this object. If
     *                 {@link ImportConfig#importMode} is set to <b>single</b>
     * then children may be null. Otherwise list of children is required, if
     * they exists.
     */
    void setChildren(List<ObjectWrapper> children);

    Path getPath();

    String getHandle();

    boolean isVolume();

    List<ObjectWrapper> getChildren();

    <T> T getObject();
}
