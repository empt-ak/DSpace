/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dmlcz5.convertors;

import cz.muni.ics.dmlcz5.domain.Titlecomp;
import cz.muni.ics.dspace5.api.DSpaceDozerConvertor;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dspace.content.Metadatum;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class TitlecompConverter extends DSpaceDozerConvertor
{

    private static final Logger logger = Logger.getLogger(TitlecompConverter.class);
    private String subtitleSchema;
    private String subtitleElement;
    private String subtitleQualifier;
    
    private String volumeSchema;
    private String volumeElement;
    private String volumeQualifier;

    public void setSubtitleSchema(String subtitleSchema)
    {
        this.subtitleSchema = subtitleSchema;
    }

    public void setSubtitleElement(String subtitleElement)
    {
        this.subtitleElement = subtitleElement;
    }

    public void setSubtitleQualifier(String subtitleQualifier)
    {
        this.subtitleQualifier = subtitleQualifier;
    }

    public void setVolumeSchema(String volumeSchema)
    {
        this.volumeSchema = volumeSchema;
    }

    public void setVolumeElement(String volumeElement)
    {
        this.volumeElement = volumeElement;
    }

    public void setVolumeQualifier(String volumeQualifier)
    {
        this.volumeQualifier = volumeQualifier;
    }  

    @Override
    public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass, Class<?> sourceClass)
    {
        List<Metadatum> resultList = new ArrayList<>();

        if (existingDestinationFieldValue != null)
        {
            resultList.addAll((List<Metadatum>) existingDestinationFieldValue);
        }
        if (sourceFieldValue != null)
        {

            List<Titlecomp> sourceList = (List<Titlecomp>) sourceFieldValue;

            for (Titlecomp tc : sourceList)
            {
                resultList.add(metadatumFactory.createMetadatum(schema, element, qualifier, tc.getTitle().getLang(), tc.getTitle().getValue()));
                if(tc.getSubTitle() != null && !StringUtils.isEmpty(tc.getSubTitle().getValue()))
                {
                    resultList.add(metadatumFactory.createMetadatum(subtitleSchema, 
                            subtitleElement, 
                            subtitleQualifier, 
                            tc.getSubTitle().getLang(), tc.getSubTitle().getValue()));
                }
                
                if(!StringUtils.isEmpty(tc.getVolume()))
                {
                    resultList.add(metadatumFactory.createMetadatum(volumeSchema, volumeElement, volumeQualifier, null, tc.getVolume()));
                }                
            }
        }
        
        return resultList;
    }

}
