/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.movingwall;

import org.dspace.content.Bitstream;
import org.dspace.content.Bundle;
import org.dspace.content.Collection;
import org.dspace.content.Community;
import org.dspace.content.DSpaceObject;
import org.dspace.content.Item;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface MWLockerProvider
{

    /**
     * Method provides specific locker for given class. Any of class that has
     * extended {@link DSpaceObject} class may be supported. However it is
     * advised that only following ones are allowed:
     * <ul>
     * <li>{@link Item}</li>
     * <li>{@link Collection}</li>
     * <li>{@link Community}</li>
     * <li>{@link Bundle}</li>
     * <li>{@link Bitstream}</li>
     * </ul>
     * otherwise exception is thrown. Given classes may or may not be
     * implemented.
     *
     * @param clasz name of class which decides which locker is returned
     *
     * @return locker for given class
     *
     * @throws UnsupportedOperationException if given class is not implemented.
     */
    MWLocker getLocker(Class<? extends DSpaceObject> clasz) throws UnsupportedOperationException;

    /**
     * Method used for checking whether given class is implemented or not. It
     * may be used in case of preventing {@link UnsupportedOperationException}
     * when {@link #getLocker(java.lang.Class) } is called.
     *
     * @param clasz to be checked
     *
     * @return true if class is implemented, false otherwise
     */
    boolean isImplemented(Class<? extends DSpaceObject> clasz);
}
