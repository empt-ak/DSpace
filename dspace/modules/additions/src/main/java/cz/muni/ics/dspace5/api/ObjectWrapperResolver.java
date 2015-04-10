/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.api;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface ObjectWrapperResolver
{

    /**
     * Method resolves given {@code objectWrapper} into full (or partial) tree
     * representing given object structure on filesystem.
     *
     * @param objectWrapper target point in ME structure
     * @param mainCall      whether we directly called this method, or not.
     *
     * @return resolved objectWrapper for given objectwrapper
     */
    ObjectWrapper resolveObjectWrapper(ObjectWrapper objectWrapper, boolean mainCall);
}
