/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.api.module;

import cz.muni.ics.dspace5.exceptions.MovingWallException;
import java.util.List;
import org.dspace.content.Item;
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
public interface ItemProcessor extends DSpaceObjectProcessor
{
    /**
     * Calling this method makes additional changes to item in post processing
     * as metadata were already extracted. This includes activities such as
     * setting cover picture, or setting extra restriction to object.
     *
     * @param item    to be modified in post process
     * @param parents previous processed branch for current object
     *
     * @throws IllegalArgumentException if item is null
     * @throws IllegalStateException    if {@link #setup(cz.muni.ics.dspace5.api.ObjectWrapper)
     *                                  } was not called before
     */
    void processItem(Item item, List<ObjectWrapper> parents) throws IllegalStateException, IllegalArgumentException;
    
    public void movingWall(Item item) throws MovingWallException;
}
