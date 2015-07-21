/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dmlcz5.services;

import cz.muni.ics.dspace5.api.module.ModuleNameResolver;
import cz.muni.ics.dspace5.impl.DSpaceTools;
import java.nio.file.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Component
public class ModuleNameResolverImpl implements ModuleNameResolver
{
    @Autowired
    private DSpaceTools dSpaceTools;
    
    @Override
    public String getModuleName(Path path) throws IllegalArgumentException
    {
        if(path == null)
        {
            throw new IllegalArgumentException("Path is null.");
        }
        
        return dSpaceTools.getOnlyMEPath(path).getName(0).toString();
    }    
}
