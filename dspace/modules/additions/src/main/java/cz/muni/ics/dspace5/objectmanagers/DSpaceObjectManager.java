/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.objectmanagers;

import cz.muni.ics.dspace5.api.module.ObjectWrapper;
import java.util.List;
import org.dspace.content.DSpaceObject;

/**
 * The one and only interface to all DSpaceObject managers. Its aim is to
 * provide common access point to DSpace objects. Method has to create object if
 * missing, update it if existing, and handle its restriction access using
 * moving wall technique.
 *
 * @author Dominik Szalai - emptulik at gmail.com
 * @param <T> generic type bind to manager
 */
public interface DSpaceObjectManager<T extends DSpaceObject>
{

    /**
     * Method handles creation, updating restriction setting of given
     * {@code objectWrapper} object inside DSpace system. Once this level is
     * done, current {@code objectWrapper} is added into parents branch and
     * called on child iteration.
     *
     * @param objectWrapper to be resolved in DSpace
     * @param parents current executed branch 
     *
     * @return created or updated object
     */
    T resolveObjectInDSpace(ObjectWrapper objectWrapper, List<ObjectWrapper> parents);
}
