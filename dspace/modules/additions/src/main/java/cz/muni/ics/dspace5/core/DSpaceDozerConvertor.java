/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.core;

import org.apache.log4j.Logger;
import org.dozer.ConfigurableCustomConverter;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public abstract class DSpaceDozerConvertor implements ConfigurableCustomConverter
{
    private static final Logger logger = Logger.getLogger(DSpaceDozerConvertor.class);
    protected String schema;
    protected String element;
    protected String qualifier;
    protected MetadatumFactory metadatumFactory;
    
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

    public void setMetadatumFactory(MetadatumFactory metadatumFactory)
    {
        this.metadatumFactory = metadatumFactory;
    }
    
    protected void cleanup()
    {
        this.element = null;
        this.qualifier = null;
        this.schema = null;
    }
}
