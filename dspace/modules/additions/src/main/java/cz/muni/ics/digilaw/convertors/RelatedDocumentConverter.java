/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.digilaw.convertors;

import cz.muni.ics.dspace5.api.DSpaceDozerConvertor;
import cz.muni.ics.dspace5.api.HandleService;
import cz.muni.ics.dspace5.impl.DSpaceTools;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.log4j.Logger;
import org.dspace.content.DSpaceObject;
import org.dspace.content.Metadatum;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class RelatedDocumentConverter extends DSpaceDozerConvertor
{
    private static final Logger logger = Logger.getLogger(RelatedDocumentConverter.class);
    
    @Autowired
    private HandleService handleService;
    @Autowired
    private DSpaceTools dSpaceTools;
    /**
     * calling subpath method on path removes the root '/'
     */
    private static final Path FAKE_ROOT = Paths.get("/"); 
    
    
    
    @Override
    public Object convert(Object destination, Object source, Class<?> destinationClass, Class<?> sourceClass)
    {
        List<Metadatum> resultList = new ArrayList<>();
        if(destination != null)
        {
            List<Metadatum> temp2 = (List<Metadatum>) destination;
            resultList.addAll(temp2);
        }
        
        if(source instanceof Collection)
        {
            List<String> temp = (List<String>) source;
            
            for(String link : temp)
            {
                Path relatedPath = dSpaceTools.getMeditorRootPath().resolve(link);
                
                // links to the article are stored withtout # prefix
                // so we have to add it when necessary
                if(dSpaceTools.getPathLevel(relatedPath) == 2)
                {
                    if(!relatedPath.getFileName().toString().startsWith("#"))
                    {
                        // subpath removes the root '/'
                        relatedPath = FAKE_ROOT.resolve(relatedPath.subpath(0, relatedPath.getNameCount()-1)
                                .resolve("#"+relatedPath.getFileName()));
                    }
                }
                
                String handle = handleService.getHandleForPath(relatedPath, false);
                
                if(handle != null)
                {
                    DSpaceObject targetObject = handleService.getGenericObjectByHandle(handle);
                    
                    if(targetObject != null)
                    {
                        resultList.add(metadatumFactory.createMetadatum(schema, element, qualifier, null, targetObject.getHandle()));
                    }
                    else
                    {
                        logger.warn("Cannot resolve related document. Path ["+relatedPath+"] has handle. But handle ["+handle+"] is not imported yet.");
                    }                    
                }
                else
                {
                    logger.warn("Cannot resolve related document. Path ["+relatedPath+"] is not imported yet.");
                }                
            }
        }
        
        return resultList;
    }
    
}
