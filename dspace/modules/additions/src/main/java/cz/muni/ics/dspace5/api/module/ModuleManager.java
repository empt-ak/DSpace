/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.api.module;

import cz.muni.ics.dspace5.api.module.ModuleService;
import java.nio.file.Path;

/**
 * Core interface responsible for the most of decisions inside system.
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface ModuleManager
{

    /**
     * Method obtains specific module from pool of modules.
     *
     * @param name of the module to be obtained
     *
     * @return module with given name
     *
     * @throws IllegalArgumentException      if name is an empty string
     * @throws UnsupportedOperationException if there is no such module with
     *                                       {@code name} as argument
     */
    ModuleService getModule(String name) throws IllegalArgumentException, UnsupportedOperationException;

    /**
     * Wrapper for method for {@link #getModule(java.lang.String) } which
     * obtains module name out of path.
     *
     * @param path determining name of module
     *
     * @return module assigned to given path
     *
     * @throws IllegalArgumentException      if path is null
     * @throws UnsupportedOperationException if there is no such module with
     *                                       {@code name} as argument
     * @see #getModule(java.lang.String)
     */
    ModuleService getModule(Path path) throws IllegalArgumentException, UnsupportedOperationException;

    /**
     * Wrapper method for {@link #getModule(java.nio.file.Path) }. From
     * objectWrapper is extracted path which is passed to the mentioned method.
     *
     * @param objectWrapper containing path used for descision making which
     *                      module is returned
     *
     * @return module according to objectWrapper
     *
     * @throws IllegalArgumentException      if objectWrapper is null, or does
     *                                       not have path
     * @throws UnsupportedOperationException if there is no such module with
     *                                       {@code name} as argument
     * @see #getModule(java.nio.file.Path)
     */
    ModuleService getModule(ObjectWrapper objectWrapper) throws IllegalArgumentException, UnsupportedOperationException;

    /**
     * Method used for deciding whats the name of module for given path.
     *
     * @param path to be converted into module name
     *
     * @return name of the module
     *
     * @throws IllegalArgumentException if path is null
     * @see ModuleNameResolver#getModuleName(java.nio.file.Path)
     */
    String getModuleName(Path path) throws IllegalArgumentException;
    
    
    /**
     * Method checks whether given module does exist (is supported).
     * @param moduleName name of module to be checked
     * @return true if module is implemented, false otherwise
     */
    boolean hasModule(String moduleName);
}
