/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.digilib.services.convertors;

import cz.muni.ics.digilib.domain.Author;
import cz.muni.ics.dspace5.core.DSpaceDozerConvertor;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.dspace.content.Metadatum;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class AuthorConverter extends DSpaceDozerConvertor
{
    private static final Logger logger = Logger.getLogger(AuthorConverter.class);
    
    @Override
    public Object convert(Object destination, Object source, Class<?> destinationClass, Class<?> sourceClass)
    {
        logger.trace("dest$"+destination);
        logger.trace("source$"+source);
        List<Metadatum> resultList = new ArrayList<>();
        if (destination != null)
        {
            List<Metadatum> temp2 = (List<Metadatum>) destination;
            resultList.addAll(temp2);
        }
        
        List<Author> authors = (List<Author>) source;
        
        for(Author a : authors)
        {
            logger.trace(a);
            resultList.add(metadatumFactory.createMetadatum(schema, element, qualifier, null, a.getValue()));
        }
        
        return resultList;
    }
    
}
