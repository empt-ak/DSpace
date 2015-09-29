/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.api;

import cz.muni.ics.dspace5.impl.ContextWrapper;
import java.nio.file.Path;

/**
 * This interface is used to fix {@link ContextWrapper}. This bean depends on
 * loading from properties files, however file is not set until the web
 * application loading is done [or better web servlet context is created] This
 * causes that the default fallback when property is not found or passed, is to
 * load config file related to position of ConfigurationManager class however
 * the config file neither exist in ConfigurationManager class location nor is
 * passed as classpath argument (like when commnad ./dpsace dsrun is executed).
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface ConfigLocationBean
{

    /**
     * Method returns full path to <b>dspace.cfg</b> configuration path. File
     * itself is not part of path
     *
     * @return location of dspace.cfg
     */
    Path getLocation();
}
