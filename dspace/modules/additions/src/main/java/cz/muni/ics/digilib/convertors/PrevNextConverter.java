/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.digilib.convertors;

import cz.muni.ics.dspace5.api.DSpaceDozerConvertor;
import cz.muni.ics.dspace5.api.HandleService;
import cz.muni.ics.dspace5.impl.DSpaceTools;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.dspace.content.Community;
import org.dspace.content.Metadatum;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author emptak
 */
public class PrevNextConverter extends DSpaceDozerConvertor
{
    @Autowired
    private HandleService handleService;
    @Autowired
    private DSpaceTools dSpaceTools;
    
    private static final Logger logger = Logger.getLogger(PrevNextConverter.class);

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
            List<String> seriesList = (List<String>) source;
            
            for(String s : seriesList)
            {
                Path relatedPath = dSpaceTools.getMeditorRootPath().resolve(s);
                
                String handle = handleService.getHandleForPath(relatedPath, false);
                
                if(handle != null)
                {
                    Community community = handleService.getObjectByHandle(handle);
                    
                    if(community != null)
                    {
                        resultList.add(metadatumFactory.createMetadatum(schema, element, qualifier, null, community.getHandle()));
                    }
                    else
                    {
                        logger.warn("Cannot resolve Previous/next document. Path ["+relatedPath+"] has handle. But handle ["+handle+"] is not imported yet.");
                    }
                }
            }            
        }
        
        return resultList;
    }    
}
