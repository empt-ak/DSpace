/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.digilib.movingwall;

import cz.muni.ics.dspace5.exceptions.MovingWallException;
import cz.muni.ics.dspace5.movingwall.MovingWallService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.apache.log4j.Logger;
import org.dspace.content.DSpaceObject;
import org.joda.time.DateTime;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class CollectionMWLocker extends AbstractLocker
{

    private static final Logger logger = Logger.getLogger(CollectionMWLocker.class);
    private static final String RESTRICTION_FILE = "restricted";

    @Override
    public void lockObject(DSpaceObject dSpaceObject) throws IllegalArgumentException, MovingWallException
    {
        if (importDataMap.containsKey(MovingWallService.MW_COLLECTION_PATH))
        {
            Path extraStorage = importDataMap.getTypedValue(MovingWallService.MW_COLLECTION_PATH, Path.class);
            DateTime embargoEnd = extractEndDate();
            DateTime embargoStart = extractStartDate();
            
            if (embargoEnd != null && DateTime.now().isBefore(embargoEnd))
            {
                try
                {
                    if(!Files.exists(extraStorage))
                    {
                        Files.createDirectories(extraStorage);
                    }
                    Files.createFile(extraStorage.resolve(RESTRICTION_FILE));
                }
                catch(IOException ex)
                {
                    throw new MovingWallException("Moving wall restriction file for collection could not be created.",ex.getCause());
                }                
            }
            else
            {
                try
                {
                    if(Files.deleteIfExists(extraStorage.resolve(RESTRICTION_FILE)))
                    {
                        logger.info("Moving wall restriction removed for "+extraStorage);
                    }
                }
                catch(IOException ex)
                {
                    logger.error(ex,ex.getCause());
                }                
            }
        }        
    }
}
