/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dmlcz5.convertors;

import cz.muni.ics.dmlcz5.domain.DmlczPerson;
import cz.muni.ics.dspace5.api.DSpaceDozerConvertor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dspace.content.Metadatum;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class DmlPersonConvertor extends DSpaceDozerConvertor
{

    private static final Logger logger = Logger.getLogger(DmlPersonConvertor.class);

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
            if (sourceFieldValue instanceof Collection)
            {
                List<DmlczPerson> personList = (List<DmlczPerson>) sourceFieldValue;

                for (DmlczPerson p : personList)
                {
                    if (StringUtils.isEmpty(qualifier))
                    {
                        if (StringUtils.isEmpty(p.getRole()))
                        {
                            logger.error("##" + p.getRole() + "##" + p.getValue());
                            //throw new IllegalArgumentException("Role is empty, when it not should be.");
                        }
                        else
                        {
                            resultList.add(metadatumFactory.createMetadatum(schema, element, p.getRole().toLowerCase(), null, p.getValue()));
                        }
                    }
                    else
                    {
                        resultList.add(metadatumFactory.createMetadatum(schema, element, qualifier, null, p.getValue()));
                    }

                }
            }
            else
            {
                DmlczPerson p = (DmlczPerson) sourceFieldValue;
                //only issuer is empty
                resultList.add(metadatumFactory.createMetadatum(schema, element, qualifier, null, p.getValue()));
            }

        }

        return resultList;
    }
}
