/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.digilib.movingwall;

import cz.muni.ics.dspace5.exceptions.MovingWallException;
import org.apache.log4j.Logger;
import org.dspace.content.DSpaceObject;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class CollectionMWLocker extends AbstractLocker
{
    private static final Logger logger = Logger.getLogger(CollectionMWLocker.class);

    @Override
    public void lockObject(DSpaceObject dSpaceObject) throws IllegalArgumentException, MovingWallException
    {
        // TODO lock whole book
        logger.error("Operation not supported yet.");
    }
}
