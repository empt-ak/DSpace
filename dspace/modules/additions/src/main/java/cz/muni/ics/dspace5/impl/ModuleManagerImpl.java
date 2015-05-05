/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.impl;

import cz.muni.ics.dspace5.api.ModuleManager;
import cz.muni.ics.dspace5.api.ModuleNameResolver;
import cz.muni.ics.dspace5.api.ObjectWrapper;
import cz.muni.ics.dspace5.api.module.ModuleService;
import java.nio.file.Path;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class ModuleManagerImpl implements ModuleManager
{
    private static final Logger logger = Logger.getLogger(ModuleManagerImpl.class);
    private Map<String,ModuleService> availableModules;
    @Autowired
    private ModuleNameResolver moduleNameResolver;

    public void setAvailableModules(Map<String, ModuleService> availableModules)
    {
        this.availableModules = availableModules;
        
        logger.info("Following modules are available: "+this.availableModules.keySet());
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

    @Override
    public boolean hasModule(String moduleName)
    {
        return availableModules.containsKey(moduleName);
    }
}
