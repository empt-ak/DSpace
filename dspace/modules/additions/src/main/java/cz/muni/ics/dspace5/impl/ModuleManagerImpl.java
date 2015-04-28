/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.impl;

import cz.muni.ics.dspace5.api.ModuleManager;
import cz.muni.ics.dspace5.api.ModuleNameResolver;
import cz.muni.ics.dspace5.api.ModuleService;
import cz.muni.ics.dspace5.api.ObjectWrapper;
import java.nio.file.Path;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class ModuleManagerImpl implements ModuleManager
{
    private Map<String,ModuleService> availableModules;
    @Autowired
    private ModuleNameResolver moduleNameResolver;

    public void setAvailableModules(Map<String, ModuleService> availableModules)
    {
        this.availableModules = availableModules;
    }
    
    @Override
    public ModuleService getModule(String name) throws IllegalArgumentException, UnsupportedOperationException
    {
        if(StringUtils.isEmpty(name))
        {
            throw new IllegalArgumentException("Given name is a empty string.");
        }
        
        if(!availableModules.containsKey(name))
        {
            throw new UnsupportedOperationException("Given module is not supported.");
        }
        
        return availableModules.get(name);
    }    

    @Override
    public ModuleService getModule(Path path) throws IllegalArgumentException, UnsupportedOperationException
    {
        return getModule(getModuleName(path));
    }

    @Override
    public String getModuleName(Path path) throws IllegalArgumentException
    {
        return moduleNameResolver.getModuleName(path);
    }

    @Override
    public ModuleService getModule(ObjectWrapper objectWrapper) throws IllegalArgumentException, UnsupportedOperationException
    {
        return getModule(objectWrapper.getPath());
    }
}
