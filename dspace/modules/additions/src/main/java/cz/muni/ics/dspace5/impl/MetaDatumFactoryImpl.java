/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.impl;

import cz.muni.ics.dspace5.core.MetadatumFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dspace.content.Metadatum;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class MetaDatumFactoryImpl implements MetadatumFactory
{
    private static final Logger logger = Logger.getLogger(MetaDatumFactoryImpl.class);

    @Override
    public Metadatum createMetadatum(String schema, String element, String qualifier, String language, String value) throws IllegalArgumentException
    {
        if(StringUtils.isEmpty(schema))
        {
            throw new IllegalArgumentException("Given schema is empty.");
        }
        if(StringUtils.isEmpty(element))
        {
            throw new IllegalArgumentException("Given element is empty.");
        }
        //TODO: remove empty element ? 
//        if(StringUtils.isEmpty(value))
//        {
//            throw new IllegalArgumentException("Given value is empty.");
//        }
        
        Metadatum metadatum = new Metadatum();
        metadatum.schema = schema;
        metadatum.element = element;
        metadatum.qualifier = qualifier;
        metadatum.language = language;
        metadatum.value = value;
        
        logger.debug("+"+metadatum.getField()+":- "+metadatum.value);
        
        return metadatum;
    }
    
}
