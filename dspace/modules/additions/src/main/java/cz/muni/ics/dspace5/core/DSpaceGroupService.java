/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.core;

import cz.muni.ics.dspace5.exceptions.GroupAlreadyExistException;
import org.dspace.core.Context;
import org.dspace.eperson.Group;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface DSpaceGroupService
{

    /**
     * Method creates all groups set under {@code dspace.muni.groups} property
     * inside configuration file.
     *
     * @throws NullPointerException if there are no groups set inside
     *                              configuration file.
     */
    void createAll() throws NullPointerException;

    /**
     * Method creates group with given {@code groupName}. Method is forced to
     * commit context after group has been created.
     *
     * @param groupName name of group to be created
     *
     * @return created group
     *
     * @throws IllegalArgumentException   if groupName is null, or empty string.
     * @throws GroupAlreadyExistException if such group already exists
     * @see Context#commit()
     */
    Group createGroup(String groupName) throws IllegalArgumentException, GroupAlreadyExistException;

    /**
     * Method creates (if missing) all groups specified by
     * {@code dspace.muni.groups} property set inside configuration file.
     */
    void check();

    /**
     * Method removes group with given name from database.
     *
     * @param groupName to be removed
     *
     * @throws IllegalArgumentException if groupName is null, or has empty
     *                                  length.
     */
    void remove(String groupName) throws IllegalArgumentException;

    /**
     * Method obtains Group based on its name passed as method argument.
     *
     * @param groupName name of group to be obtained
     *
     * @return Group with given name, null if there is no such group
     *
     * @throws IllegalArgumentException if {@code groupName} is null or empty
     *                                  String.
     */
    Group getGroupByName(String groupName) throws IllegalArgumentException;
    
    /**
     * Method returns anonymous group created by default DSpace setup process.
     * @return anonymous group.
     */
    Group getAnonymousGroup();
}
