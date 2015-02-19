/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.impl;

import org.dspace.utils.DSpace;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class DSpaceWrapper
{
    private static DSpaceWrapper instance;
    private static DSpace dspace;   
    
    private DSpaceWrapper(){}
    
    public static DSpaceWrapper getInstance()
    {
        if(instance == null)
        {
            instance = new DSpaceWrapper();
            dspace = new DSpace();
        }
        
        return instance;
    }
    
    public DSpace getDSpace()
    {       
        return dspace;
    }
    
    public <T> T getBean(String name,Class<T> type)
    {
        return dspace.getServiceManager().getServiceByName(name, type);
    }
    
    public String getProperty(String property)
    {
        return getDSpace().getConfigurationService().getProperty(property);
    }
}
