/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.api.module;

import cz.muni.ics.dspace5.api.ObjectWrapper;
import java.io.FileNotFoundException;

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
     * @throws java.io.FileNotFoundException when file, or folder is missing
     */
    ObjectWrapper resolveObjectWrapper(ObjectWrapper objectWrapper, boolean mainCall) throws FileNotFoundException;
}
