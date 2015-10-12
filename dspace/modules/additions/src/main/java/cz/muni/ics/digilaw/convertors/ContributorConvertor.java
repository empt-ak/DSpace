/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.digilaw.convertors;

import cz.muni.ics.digilaw.domain.Contributor;
import cz.muni.ics.dspace5.api.DSpaceDozerConvertor;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.dspace.content.Metadatum;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class ContributorConvertor extends DSpaceDozerConvertor
{
    private static final Logger logger = Logger.getLogger(ContributorConvertor.class);
    
    @Override
    public Object convert(Object destination, Object source, Class<?> destinationClass, Class<?> sourceClass)
    {
        List<Metadatum> resultList = new ArrayList<>();
        if (destination != null)
        {
            List<Metadatum> temp2 = (List<Metadatum>) destination;
            resultList.addAll(temp2);
        }
        
        List<Contributor> contributors = (List<Contributor>) source;
        
        for(Contributor c : contributors)
        {
            resultList.add(metadatumFactory.createMetadatum(schema, element, c.getRole().value(), null, c.getValue()));                       
        }
        
        return resultList;
    }
}
