/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.impl;

import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Component
public class InputArguments
{
    private static final Logger logger = Logger.getLogger(InputArguments.class);
    private final Map<String,Object> argumentMap = new HashMap<>();
    
    public void put(String key, Object value)
    {
        argumentMap.put(key, value);
    }
    
    public String getValue(String key)
    {
        return (String) argumentMap.get(key);
    }
    
    public <T> T getTypedValue(String key)
    {
        return (T) argumentMap.get(key);
    }
    
    public <T> T getTypedValue(String key, Class<T> clazz)
    {
        return (T) argumentMap.get(key);
    }
    
    public Map<String,Object> getArgumentMap()
    {
        return argumentMap;
    }
    
    public void dump()
    {
        for(String s : argumentMap.keySet())
        {
            Object o = argumentMap.get(s);
            logger.info(s+";"+o.getClass()+";"+o);
        }
    }
}
