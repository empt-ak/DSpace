/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.api.module;

import java.io.FileNotFoundException;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface ModuleService
{

    /**
     * Method returns specific resolver strategy used for object tree resolving.
     *
     * @return strategy based on module
     *
     * @throws FileNotFoundException if detail.xml is missing
     */
    ObjectWrapperResolver getObjectWrapperResolver() throws FileNotFoundException;

    /**
     * Method returns community processor based on module strategy.
     *
     * @return community processor
     */
    CommunityProcessor getCommunityProcessor();

    /**
     * Method returns collection processor based on module strategy
     *
     * @return collection processor
     */
    CollectionProcessor getCollectionProcessor();

    /**
     * Method returns item processor based on module strategy
     *
     * @return item processor
     */
    ItemProcessor getItemProcessor();
}
