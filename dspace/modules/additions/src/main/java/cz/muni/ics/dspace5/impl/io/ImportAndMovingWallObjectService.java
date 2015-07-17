/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.impl.io;

import cz.muni.ics.dspace5.api.DSpaceObjectService;
import cz.muni.ics.dspace5.api.ObjectWrapperFactory;
import cz.muni.ics.dspace5.api.module.ModuleManager;
import cz.muni.ics.dspace5.api.module.ObjectWrapper;
import cz.muni.ics.dspace5.impl.ContextWrapper;
import cz.muni.ics.dspace5.impl.InputDataMap;
import cz.muni.ics.dspace5.objectmanagers.DSpaceObjectManager;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import org.dspace.content.Community;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class ImportAndMovingWallObjectService implements DSpaceObjectService
{

    private static final Logger logger = Logger.getLogger(ImportAndMovingWallObjectService.class);

    @Autowired
    private ObjectWrapperFactory objectWrapperFactory;
    @Autowired
    @Qualifier(value = "communityManager")
    private DSpaceObjectManager<Community> communityManager;
    @Autowired
    private InputDataMap inputDataMap;
    @Autowired
    private ContextWrapper contextWrapper;
    @Autowired
    private ModuleManager moduleManager;

    @Override
    public void execute()
    {

        Path pathFromCMD = inputDataMap.getTypedValue("value", Path.class);

        if (!Files.exists(pathFromCMD))
        {
            logger.error("Given path does not exists in metadata editor directory. [" + pathFromCMD + "]");
        }
        else
        {
            ObjectWrapper importTarget = objectWrapperFactory
                    .createRootObjectWrapper(pathFromCMD);

            ObjectWrapper realImport = null;

            try
            {
                realImport = moduleManager.getModule(importTarget.getPath())
                        .getObjectWrapperResolver().resolveObjectWrapper(importTarget, true);
            }
            catch (FileNotFoundException nfe)
            {
                logger.error(nfe);
            }

            if (realImport != null)
            {
                communityManager.resolveObjectInDSpace(realImport, new ArrayList<ObjectWrapper>());
            }
    //            try
            //            {

    //            }
            //            catch(Exception e)
            //            {
            //                logger.fatal(e,e.getCause());
            //  contextWrapper.getContext().abort();
            //                
            //            }
            //            finally
            //            {
            try
            {
                contextWrapper.getContext().complete();
            }
            catch (SQLException ex)
            {
                logger.error(ex, ex.getCause());
            }
            //            }
        }

    }

}
