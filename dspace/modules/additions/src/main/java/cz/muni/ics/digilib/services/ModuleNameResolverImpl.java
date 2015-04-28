/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.digilib.services;

import cz.muni.ics.dspace5.api.ModuleNameResolver;
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
        if(path.toString().contains("serial"))
        {
            return "serial";
        }
        else if(path.toString().contains("monograph"))
        {
            return "monograph";
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }
    
}
