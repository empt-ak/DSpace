/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.impl;

import cz.muni.ics.dspace5.core.ObjectWrapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.dspace.services.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public abstract class AbstractTools
{
    @Autowired
    protected ConfigurationService configurationService;
    @Autowired
    protected InputArguments inputArguments;
    @Autowired
    protected ContextWrapper contextWrapper;
    
    /**
     * Method creates new List containing {@code ObjectWrapper}s which represent current processed node in object tree.
     * @param newEntry to be added
     * @param currentBranch so far processed branch
     * @return List containing current branch
     * @throws IllegalArgumentException if {@code newEntry} is null
     */
    public List<ObjectWrapper> createParentBranch(ObjectWrapper newEntry, List<ObjectWrapper> currentBranch) throws IllegalArgumentException
    {
        if(newEntry == null)
        {
            throw new IllegalArgumentException("New entry to list cannot by null.");
        }
        
        List<ObjectWrapper> resultList = new ArrayList<>();
        
        if(currentBranch != null && !currentBranch.isEmpty())
        {
            resultList.addAll(currentBranch);
        }
        
        resultList.add(newEntry);
        
        return resultList;
    }
    
    /**
     * Method creates new data map
     * @param key of object to be stored
     * @param object to be stored
     * @param current actual (if existing) datamap
     * @param createNew flag specifying whether &lt;key,object&gt; should be put in new map, or current one
     * @return data map holding new &lt;key,object&gt; value
     * @throws IllegalArgumentException if key is null or empty string
     */
    public Map<String,Object> createDataMap(String key, Object object, Map<String,Object> current, boolean createNew) throws IllegalArgumentException
    {
        if(key == null || key.isEmpty())
        {
            throw new IllegalArgumentException("Key to map has to be non empty String.");
        }
        
        if(!createNew)
        {
            if(current == null)
            {
                Map<String,Object> newResult = new HashMap<>();
                newResult.put(key, object);
                
                return newResult;
            }
            else
            {
                current.put(key, object);
                
                return current;
            }
        }
        else
        {
            Map<String,Object> newResult = new HashMap<>();
            if(current != null)
            {
                newResult.putAll(current);
            }
            
            newResult.put(key, object);

            return newResult;
        }
    }
}
