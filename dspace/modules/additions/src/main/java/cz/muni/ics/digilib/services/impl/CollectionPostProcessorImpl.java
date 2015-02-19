/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.digilib.services.impl;

import cz.muni.ics.dspace5.core.post.CollectionPostProcessor;
import cz.muni.ics.dspace5.impl.MetadataRow;
import cz.muni.ics.dspace5.core.ObjectWrapper;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.List;
import org.apache.log4j.Logger;
import org.dspace.authorize.AuthorizeException;
import org.dspace.content.Collection;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class CollectionPostProcessorImpl implements CollectionPostProcessor
{
    private static final Logger logger = Logger.getLogger(CollectionPostProcessorImpl.class);

    @Override
    public List<MetadataRow> processMetadata(ObjectWrapper objectWrapper) throws IllegalArgumentException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void processCollection(ObjectWrapper objectWrapper, Collection collection) throws IllegalArgumentException, UnsupportedOperationException
    {
        if(Files.exists(objectWrapper.getPath()))
        {
            try(FileInputStream fis = new FileInputStream(objectWrapper.getPath().toFile()))
            {
                collection.setLogo(fis);
            }
            catch(IOException | AuthorizeException | SQLException ex)
            {
                logger.error(ex, ex.getCause());
            }
        }
        else
        {
            logger.info("There is no file on given path. Bundle will be not set for ["+objectWrapper.getHandle()+"] at path "+objectWrapper.getPath().toString());
        }
    }    
}
