/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.impl;

import java.util.Collections;
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
    
    /**
     * Gets value from input arguments based on passed key. Call this method only when you are sure that the value is {@code String}
     * @param key of which value is to be obtained
     * @return String representation of value for given key.
     */
    public String getValue(String key)
    {
        return (String) argumentMap.get(key);
    }
    
    /**
     * Gets value from input arguments based on passed key. Value is automatically cast to given {@code T} type.
     * @param <T> return type
     * @param key of value to be obtained
     * @return value cast to {@code T} type
     */
    public <T> T getTypedValue(String key)
    {
        return (T) argumentMap.get(key);
    }
    
    /**
     * Gets value from input arguments based on passed key. Value is automatically cast to given type of {@code clazz} argument.
     * @param <T> return type
     * @param key key of value to be obtained
     * @param clazz type of return type
     * @return return value based on {@code key} and {@code clazz}
     */
    public <T> T getTypedValue(String key, Class<T> clazz)
    {
        return (T) argumentMap.get(key);
    }
    
    /**
     * Method returns whole argument map.
     * @return argument map.
     */
    public Map<String,Object> getArgumentMap()
    {
        return Collections.unmodifiableMap(argumentMap); //just in case
    }
    
    /**
     * Method dumps all values passed from command line in following form:
     * <pre>
     * key;className;value
     * </pre>
     * All values are put into {@code Logger} at info level.
     */
    public void dump()
    {
        for(String s : argumentMap.keySet())
        {
            Object o = argumentMap.get(s);
            logger.info(s+";"+o.getClass()+";"+o);
        }
    }
    
    /**
     * Method used for fast check whether input map contains given key
     * @param key to be checked
     * @return true if key is present, false otherwise
     */
    public boolean containsKey(String key)
    {
        return argumentMap.containsKey(key);
    }
}
