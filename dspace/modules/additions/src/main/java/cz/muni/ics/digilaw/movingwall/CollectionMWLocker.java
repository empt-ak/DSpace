/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.digilaw.movingwall;

import cz.muni.ics.dspace5.exceptions.MovingWallException;
import cz.muni.ics.dspace5.movingwall.MWLocker;
import cz.muni.ics.dspace5.movingwall.MovingWall;
import java.io.IOException;
import java.nio.file.Files;
import org.apache.log4j.Logger;
import org.dspace.content.DSpaceObject;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class CollectionMWLocker implements MWLocker
{

    private static final Logger logger = Logger.getLogger(CollectionMWLocker.class);
    private static final String RESTRICTION_FILE = "restricted";

    @Override
    public void lockObject(DSpaceObject dSpaceObject, MovingWall movingWall) throws IllegalArgumentException, MovingWallException
    {
        checkInput(dSpaceObject, movingWall);
        logger.info("Following MW obtained: "+movingWall);
        if(movingWall.extraStorage() != null)
        {
            if(!movingWall.ignore() && (movingWall.getEndDate().isAfterNow() && !movingWall.isOpenAccess()) ||
                    (movingWall.getRightsAccess() != null && !"openaccess".equals(movingWall.getRightsAccess()))
                )
            {
                try
                {
                    if(Files.exists(movingWall.extraStorage()))
                    {
                        Files.createFile(movingWall.extraStorage().resolve(RESTRICTION_FILE));
                        logger.info("Created restriction for Collection @path "+movingWall.extraStorage());
                    }
                    else
                    {
                        logger.warn("Extra storage path is set to "+movingWall.extraStorage()+" but directory does not exist.");
                    }
                }
                catch(IOException ex)
                {
                    throw new MovingWallException("Moving wall restriction file for collection could not be created.",ex.getCause());
                }
            }
            else
            {
                unlockObject(dSpaceObject, movingWall);
            }
        }       
    }

    @Override
    public void unlockObject(DSpaceObject dSpaceObject, MovingWall movingWall) throws IllegalArgumentException, MovingWallException
    {
        checkInput(dSpaceObject, movingWall);
        
        if(movingWall.getEndDate().isBeforeNow())
        {
            try
            {
                if(Files.deleteIfExists(movingWall.extraStorage().resolve(RESTRICTION_FILE)))
                {
                    logger.info("Moving wall restriction removed for "+movingWall.extraStorage());
                }
                else
                {
                    logger.warn("MW restriction could not be removed for "+movingWall.extraStorage());
                }
            }
            catch(IOException ex)
            {
                logger.error(ex,ex.getCause());
            }
        }
    }    
    
    private void checkInput(DSpaceObject object, MovingWall movingWall) throws IllegalArgumentException
    {
        if(object == null)
        {
            throw new IllegalArgumentException("Given DSpace object is null.");
        }
        
        if(movingWall == null)
        {
            throw new IllegalArgumentException("GIven MovingWall object is null.");
        }
    }
}
