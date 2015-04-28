/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.api;

import java.nio.file.Path;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface ModuleManager
{
    ModuleService getModule(String name) throws IllegalArgumentException, UnsupportedOperationException;
    ModuleService getModule(Path path) throws IllegalArgumentException, UnsupportedOperationException;
    ModuleService getModule(ObjectWrapper objectWrapper) throws IllegalArgumentException, UnsupportedOperationException;
    String getModuleName(Path path) throws IllegalArgumentException;
}
