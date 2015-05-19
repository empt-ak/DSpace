/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.metadata;

import cz.muni.ics.dspace5.impl.ContextWrapper;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.apache.log4j.Logger;
import org.dspace.administer.MetadataImporter;
import org.dspace.administer.RegistryImportException;
import org.dspace.authorize.AuthorizeException;
import org.dspace.content.NonUniqueMetadataException;
import org.dspace.services.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Component
public class RegistryServiceImpl implements RegistryService
{
    private static final Logger logger = Logger.getLogger(RegistryServiceImpl.class);
    @Autowired
    private ContextWrapper contextWrapper;
    @Autowired
    private ConfigurationService configurationService;

    @Override
    public void updateRegistrySchema(String schemaName)
    {
        try
        {
            Path registryFile = Paths.get(configurationService.getProperty("dspace.dir")).resolve("config").resolve("registries").resolve(schemaName);
            
            MetadataImporter.loadRegistry(registryFile.toString(), true);
        }
        catch (SQLException | IOException | TransformerException | ParserConfigurationException | AuthorizeException | SAXException | NonUniqueMetadataException | RegistryImportException ex)
        {
            logger.error(ex,ex.getCause());
        }
    }    
}
