/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.digilaw.services;

import cz.muni.ics.dspace5.api.module.ModuleNameResolver;
import java.nio.file.Path;
import org.springframework.stereotype.Component;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Component
public class ModuleNameResolverImpl implements ModuleNameResolver
{

    @Override
    public String getModuleName(Path path) throws IllegalArgumentException
    {
        if(path == null)
        {
            throw new IllegalArgumentException("Path is null.");
        }
        
        if(path.toString().contains("celebrity"))
        {
            return "celebrity";
        }
        else if(path.toString().contains("monograph"))
        {
            return "monograph";
        }
        else
        {
            throw new IllegalArgumentException("Module for path "+path+" is not supported.");
        }
    }    
}
