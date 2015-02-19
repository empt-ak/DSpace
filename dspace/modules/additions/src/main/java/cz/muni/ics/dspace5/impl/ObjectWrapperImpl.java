/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.impl;

import cz.muni.ics.dspace5.core.ObjectWrapper;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class ObjectWrapperImpl implements ObjectWrapper
{

    private final Path path;
    private final String handle;
    private final Object object;
    private List<ObjectWrapper> children;

    /**
     * Default and the only constructor to this class.
     *
     * @param path      target path, location where is this object stored
     * @param handle    handle of this object
     * @param object    already converted object from given path. if we are
     *                  using domain import (path is converted into object via
     *                  XML mapping) then we can store object in this parameter.
     *                  This parameter may be null.
     * @param children children of this object. If
     *                  {@link ImportConfig#importMode} is set to <b>single</b>
     *                  then children may be null. Otherwise list of children is
     *                  required, if they exists.
     */
    public ObjectWrapperImpl(Path path, String handle, Object object, List<ObjectWrapper> children)
    {
        this.path = path;
        this.handle = handle;
        this.object = object;
        this.children = children;
    }

    @Override
    public Path getPath()
    {
        return path;
    }

    @Override
    public String getHandle()
    {
        return handle;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getObject()
    {
        return (T) object;
    }

    @Override
    public List<ObjectWrapper> getChildren()
    {
        return children;
    }

    @Override
    public void setChildren(List<ObjectWrapper> children)
    {
        this.children = children;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.handle);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final ObjectWrapperImpl other = (ObjectWrapperImpl) obj;
        return Objects.equals(this.handle, other.handle);
    }
}
