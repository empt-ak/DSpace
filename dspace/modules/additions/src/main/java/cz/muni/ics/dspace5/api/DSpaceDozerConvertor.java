/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.api;

import cz.muni.ics.dspace5.metadata.MetadatumFactory;
import org.apache.log4j.Logger;
import org.dozer.ConfigurableCustomConverter;
import org.dspace.content.Metadatum;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This class serves as special parent for any other converter used by system.
 * It forces subclass to implement {@link ConfigurableCustomConverter#convert(java.lang.Object, java.lang.Object, java.lang.Class, java.lang.Class)
 * } method which does the conversion. Parent class is only responsible for
 * setting and reseting parameter value. It also contains access to
 * {@link MetadatumFactory} which allows creation of {@link Metadatum} objects.
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public abstract class DSpaceDozerConvertor implements ConfigurableCustomConverter
{

    private static final Logger logger = Logger.getLogger(DSpaceDozerConvertor.class);
    protected String schema;
    protected String element;
    protected String qualifier;
    @Autowired
    protected MetadatumFactory metadatumFactory;
    
    @Override
    public void setParameter(String parameter)
    {
        cleanup();
        logger.debug("setParameter() called with following argument: " + parameter);
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
            logger.debug("Values set to schema@" + this.schema + " element@" + this.element + " qualifier@" + this.qualifier);
        }
    }

    /**
     * Method sets metadatumFactory required for creating metadatum objects
     * which are result of conversion.
     *
     * @param metadatumFactory
     */
    public void setMetadatumFactory(MetadatumFactory metadatumFactory)
    {
        this.metadatumFactory = metadatumFactory;
    }

    /**
     * Method removes previous values, as converter is singleton and may be
     * shared among more instances than one.
     */
    protected void cleanup()
    {
        this.element = null;
        this.qualifier = null;
        this.schema = null;
    }

    @Override
    public abstract Object convert(Object destination, Object source, Class<?> destinationClass, Class<?> sourceClass);   
}
