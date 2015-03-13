/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.impl.convertors;

import cz.muni.ics.dspace5.core.MetadatumFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.log4j.Logger;
import org.dozer.ConfigurableCustomConverter;
import org.dspace.content.Metadatum;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class SimpleElementConverter implements ConfigurableCustomConverter
{
    private static final Logger logger = Logger.getLogger(SimpleElementConverter.class);

    private String schema;
    private String element;
    private String qualifier;

    private MetadatumFactory metadatumFactory;

    public void setMetadatumFactory(MetadatumFactory metadatumFactory)
    {
        this.metadatumFactory = metadatumFactory;
    }   

    @Override
    public void setParameter(String parameter)
    {
        cleanup();
        logger.debug("setParameter() called with following argument: "+parameter);
        String[] splitValue = parameter.split("\\.");

        if (splitValue.length < 2 || splitValue.length > 3)
        {
            throw new IllegalArgumentException("Given parameter has invalid value.");
        }
        else
        {
            schema = splitValue[0];
            element = splitValue[1];
            if (splitValue.length == 3)
            {
                qualifier = splitValue[2];
            }
            logger.debug("Values set to schema@"+this.schema+" element@"+this.element+" qualifier@"+this.qualifier);
        }
    }

    @Override
    public Object convert(Object destination, Object source, Class<?> destinationClass, Class<?> sourceClass)
    {
        List<Metadatum> resultList = new ArrayList<>();
        if (destination != null)
        {
            List<Metadatum> temp2 = (List<Metadatum>) destination;
            resultList.addAll(temp2);
        }
        
        if (source instanceof Collection)
        {          
            List temp = (List) source;                 

            for (Object o : temp)
            {
                resultList.add(metadatumFactory.createMetadatum(schema, element, qualifier, null, o.toString()));
            }
        }
        else
        {
            resultList.add(metadatumFactory.createMetadatum(schema, element, qualifier, null, source.toString()));
        }
        
        return resultList;
    }
    
    private void cleanup()
    {
        this.element = null;
        this.qualifier = null;
        this.schema = null;
    }
}
