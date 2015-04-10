/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.api.post;

import cz.muni.ics.dspace5.api.ObjectWrapper;
import java.util.List;
import java.util.Map;
import org.dspace.content.Item;
import org.dspace.content.Metadatum;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface ItemPostProcessor
{

    /**
     * Method takes given objectWrapper and processes it into {@code List} of
     * metadata. The way metadata are processed is matter of implementation.
     * {@code ObjectWrapper} contains all necessary information such as path,
     * handle and {@code Meta} object. From path we can recreate given
     * <b>.xml</b> file.
     *
     * @param objectWrapper from which we extract metadata
     * @param parents
     * @param dataMap
     *
     * @return List of metadata from given objectwrapper.
     *
     * @throws IllegalArgumentException if objectwrapper is null, or does not
     *                                  have a path
     */
    List<Metadatum> processMetadata(ObjectWrapper objectWrapper, List<ObjectWrapper> parents, Map<String,Object> dataMap) throws IllegalArgumentException;

    /**
     * If anything else need to be done to {@code Item} object, then call this
     * method. Core functionality for item would be storing Bundles, and handling moving wall.
     *
     * @param objectWrapper holder for current object to be processed
     * @param item          to be modified
     * @param parents
     * @param dataMap
     *
     * @throws IllegalArgumentException      if objectWrapper is null or does
     *                                       not have set path. Also thrown when
     *                                       community is null.
     * @throws UnsupportedOperationException if method is not implemented.
     */
    void processItem(ObjectWrapper objectWrapper, Item item, List<ObjectWrapper> parents, Map<String,Object> dataMap) throws IllegalArgumentException;
}
