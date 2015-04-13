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
 * Class which calls implementation of this interface is strongly dependant on
 * its execution.
 * <ul>
 * <ol>First we call method {@link #setup(cz.muni.ics.dspace5.api.ObjectWrapper)
 * }. Calling method should recreate given object from {@code objectWrapper} For
 * example if objectwrapper is isssue then it is converted into issue. If we are
 * not working with domain model we can convert it into {@link Document} or any
 * other representation we want. </ol>
 * <ol>Second step is to retrieve metadata out of already prepared source
 * object. This is done by calling {@link #processMetadata(cz.muni.ics.dspace5.api.ObjectWrapper, java.util.List, java.util.Map)
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
public interface ItemPostProcessor
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
     * required for any reason previous parents and universal dataMap may be
     * provided.
     *
     * @param parents previous processed branch for current object
     * @param dataMap extra storage for additional processing
     *
     * @return list of metada object, empty if there are none
     *
     * @throws IllegalArgumentException TODO?
     * @throws IllegalStateException    if {@link #setup(cz.muni.ics.dspace5.api.ObjectWrapper)
     *                                  } was not called before.
     */
    List<Metadatum> processMetadata(List<ObjectWrapper> parents, Map<String, Object> dataMap) throws IllegalArgumentException, IllegalStateException;

    /**
     * Calling this method makes additional changes to item in post processing
     * as metadata were already extracted. This includes activities such as
     * setting cover picture, or setting extra restriction to object.
     *
     * @param item    to be modified in post process
     * @param parents previous processed branch for current object
     * @param dataMap extra storage for additional processing
     *
     * @throws IllegalArgumentException if item is null
     * @throws IllegalStateException    if {@link #setup(cz.muni.ics.dspace5.api.ObjectWrapper)
     *                                  } was not called before
     */
    void processItem(Item item, List<ObjectWrapper> parents, Map<String, Object> dataMap) throws IllegalStateException, IllegalArgumentException;

    /**
     * Calling this method clears object(s) stored, or recreated by {@link #setup(cz.muni.ics.dspace5.api.ObjectWrapper)
     * } method. Calling this method multiple times has no effect.
     */
    void clear();
}
