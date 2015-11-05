/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.digilib.convertors;

import cz.muni.ics.digilib.domain.Series;
import cz.muni.ics.dspace5.api.DSpaceDozerConvertor;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.dspace.content.Metadatum;

/**
 *
 * @author emptak
 */
public class SeriesConverter extends DSpaceDozerConvertor
{

    @Override
    public Object convert(Object destination, Object source, Class<?> destinationClass, Class<?> sourceClass)
    {
        List<Metadatum> resultList = new ArrayList<>();
        
        if(destination != null)
        {
            List<Metadatum> temp2 = (List<Metadatum>) destination;
            resultList.addAll(temp2);
        }
        
        if(source != null)
        {
            List<Series> temp = (List<Series>) source;
            
            for(Series s : temp)
            {   //skip empty values
                if(!StringUtils.isEmpty(s.getValue()))
                {
                    resultList.add(metadatumFactory.createMetadatum(schema, element, qualifier, null, s.getValue()));
                }                
            }
        }
        
        return resultList;
    }    
}
