/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.digilib.services.convertors;

import cz.muni.ics.dspace5.core.DSpaceDozerConvertor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.dspace.content.Metadatum;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class ComposedElementConverter extends DSpaceDozerConvertor
{
    private static final Logger logger = Logger.getLogger(ComposedElementConverter.class);

    @Override
    public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass, Class<?> sourceClass)
    {
        if(sourceFieldValue != null)
        {
            List<Metadatum> resultList = new ArrayList<>();
            
            if(existingDestinationFieldValue != null)
            {
                resultList.addAll((List<Metadatum>) existingDestinationFieldValue);
            }
            
            if(sourceFieldValue instanceof Collection)
            {
                List sourceList = (List) sourceFieldValue;
                
                for(Object o : sourceList)
                {
                    resultList.add(convertObject(o));
                }
            }
            else
            {
                resultList.add(convertObject(sourceFieldValue));
            }
            
            return resultList;
        }
        
        return null;
    }
    
    private Metadatum convertObject(Object inputValue)
    {
        try
        {
            return metadatumFactory.createMetadatum(schema, 
                element, 
                qualifier, 
                (String) PropertyUtils.getProperty(inputValue, "lang"),
                (String) PropertyUtils.getProperty(inputValue, "value")
            );
        }
        catch(IllegalAccessException | InvocationTargetException | NoSuchMethodException ex)
        {
            logger.error(ex,ex.getCause());
        }
        
        return null;
    }
    
}
