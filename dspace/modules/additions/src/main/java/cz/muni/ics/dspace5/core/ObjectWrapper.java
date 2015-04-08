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
public interface ObjectWrapper extends Comparable<ObjectWrapper>
{
    public enum LEVEL
    {
        COM(1),
        SUBCOM(2),
        COL(3),
        ITEM(4);
        
        private final int mask;
        
        private LEVEL(int mask)
        {
            this.mask = mask;
        }
        
        public int mask()
        {
            return this.mask;
        }
    }

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
     * Method adds children to this wrapper.
     *
     * @param children children of this object. If importmode is set to <b>single</b>
     * then children may be null. Otherwise list of children is required, if
     * they exists.
     */
    void setChildren(List<ObjectWrapper> children);
    
    void setLevel(LEVEL level);

    Path getPath();

    String getHandle();

    List<ObjectWrapper> getChildren();

    <T> T getObject();
    
    LEVEL getLevel();
    
    void print();
}
