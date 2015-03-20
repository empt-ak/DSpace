/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.digilib.services.impl;

import cz.muni.ics.dspace5.core.ObjectWrapperResolver;
import cz.muni.ics.dspace5.core.ObjectWrapperResolverFactory;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public abstract class ObjectWrapperResolverFactoryImpl implements ObjectWrapperResolverFactory
{
    abstract ObjectWrapperResolver provideMonographyResolver();
    abstract ObjectWrapperResolver provideSerialResolver();
    
    @Override
    public ObjectWrapperResolver provideObjectWrapperResolver(String type) throws IllegalArgumentException
    {        
        switch(type)
        {
            case "serial": return provideSerialResolver();
            case "monograph" : return provideMonographyResolver();
            default: throw new IllegalArgumentException("Invalid type. Expected [serial/monograph] but was ["+type+"]");
        }
    }
    
}
