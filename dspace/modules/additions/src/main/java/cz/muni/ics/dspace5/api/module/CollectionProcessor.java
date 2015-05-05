/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.api.module;

import cz.muni.ics.dspace5.api.ObjectWrapper;
import java.util.List;
import org.dspace.content.Collection;
import org.dspace.content.Metadatum;
import org.w3c.dom.Document;

/**
 * Class which calls implementation of this interface is strongly dependant on
 * its execution.
 * <ul>
 * <ol>First we call method {@link #setup(cz.muni.ics.dspace5.api.ObjectWrapper)
 * }. Calling method should recreate given object from {@code objectWrapper} For
 * example if objectwrapper is isssue then it is converted into issue. If we are
 * not working with domain model we can convert it into {@link Document} or any
 * other representation we want. </ol>
 * <ol>Second step is to retrieve metadata out of already prepared source
 * object. This is done by calling {@link #processMetadata(java.util.List) 
 * }</ol>
 * <ol>In third step we check if anything else has to be done to already created
 * object inside DSpace. An example might be setting restrictions to object, or
 * setting its cover picture.</ol>
 * <ol>In last fourth step we need to clear the source object we created using {@link #setup(cz.muni.ics.dspace5.api.ObjectWrapper)
 * } method by calling {@link #clear() } method. </ol>
 * </ul>
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface CollectionProcessor
{

    /**
     * Method takes given input objectWrapper and should convert it into some
     * kind of representation which will be used in later processing. The
     * <b>only required action</b> is to stored this object for further
     * processing as other method do not require passing this object again.
     *
     * @param objectWrapper to be converted
     *
     * @throws IllegalStateException    if implementation has been already
     *                                  setup, or previous run did not call {@link #clear()
     *                                  }.
     * @throws IllegalArgumentException if objectWrapper is null, does not have
     *                                  path or handle, or does not have valid
     * <b>LEVEL</b>.
     */
    void setup(ObjectWrapper objectWrapper) throws IllegalStateException, IllegalArgumentException;

    /**
     * Method converts already setup object into List of metadata objects. If
     * required for any reason previous parents and are provided.
     *
     * @param parents previous processed branch for current object
     *
     * @return list of metadata object, empty if there are none
     *
     * @throws IllegalArgumentException TODO?
     * @throws IllegalStateException    if {@link #setup(cz.muni.ics.dspace5.api.ObjectWrapper)
     *                                  } was not called before.
     */
    List<Metadatum> processMetadata(List<ObjectWrapper> parents) throws IllegalArgumentException, IllegalStateException;
    
    /**
     * Calling this method makes additional changes to collection in post
     * processing as metadata were already extracted. This includes activities
     * such as setting cover picture, or setting extra restriction to object.
     *
     * @param collection to be modified in post process
     * @param parents    previous processed branch for current object
     *
     * @throws IllegalArgumentException if collection is null
     * @throws IllegalStateException    if {@link #setup(cz.muni.ics.dspace5.api.ObjectWrapper)
     *                                  } was not called before
     */
    void processCollection(Collection collection, List<ObjectWrapper> parents) throws IllegalStateException, IllegalArgumentException;

    /**
     * Calling this method clears object(s) stored, or recreated by {@link #setup(cz.muni.ics.dspace5.api.ObjectWrapper)
     * } method. Calling this method multiple times has no effect.
     */
    void clear();
}
