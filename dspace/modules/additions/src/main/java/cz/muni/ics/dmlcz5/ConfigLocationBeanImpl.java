/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dmlcz5;

import cz.muni.ics.dspace5.api.ConfigLocationBean;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.stereotype.Component;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Component
public class ConfigLocationBeanImpl implements ConfigLocationBean
{

    private static final Path configFileLocation = Paths.get("/opt/dmlcz5/config");
    @Override
    public Path getLocation()
    {
        return configFileLocation;
    }
    
}
