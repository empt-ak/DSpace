/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.movingwall;

import org.dspace.content.Collection;
import org.dspace.content.Community;
import org.dspace.content.Item;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface MWLockerProvider
{
    /**
     * Method provides specific locker for given class. Following classes are allowed:
     * <ul>
     * <li>{@link Item}</li>
     * <li>{@link Collection}</li>
     * <li>{@link Community}</li>
     * </ul>
     * otherwise exception is thrown.
     * @param clasz name of class which decides which locker is returned
     * @return locker for given class
     * @throws IllegalArgumentException if class is not supported. 
     */
    MWLocker getLocker(Class clasz) throws IllegalArgumentException;
}
