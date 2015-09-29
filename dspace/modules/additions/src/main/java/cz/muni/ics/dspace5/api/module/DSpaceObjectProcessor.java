/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.api.module;

import java.util.List;
import org.dspace.content.Metadatum;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface DSpaceObjectProcessor
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
     * Calling this method clears object(s) stored, or recreated by {@link #setup(cz.muni.ics.dspace5.api.ObjectWrapper)
     * } method. Calling this method multiple times has no effect.
     */
    void clear();
}
