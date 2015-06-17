/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.api.module;

import java.nio.file.Path;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface ModuleNameResolver
{

    /**
     * Method returns name of module for given path.
     *
     * @param path to be resolved
     *
     * @return name of the module
     *
     * @throws IllegalArgumentException if path is null, or there is no module
     *                                  for this path.
     */
    String getModuleName(Path path) throws IllegalArgumentException;
}
