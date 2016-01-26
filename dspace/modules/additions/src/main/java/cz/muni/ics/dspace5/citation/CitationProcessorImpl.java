/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.citation;

import cz.muni.ics.dspace5.metadata.MetadatumFactory;
import de.undercouch.citeproc.CSL;
import org.apache.log4j.Logger;
import org.dspace.content.Metadatum;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class CitationProcessorImpl implements CitationProcessor
{
    private static final Logger LOG = Logger.getLogger(CitationProcessorImpl.class);
    private CSL cslProcessor;
    private String dcField;
    private String schema;
    private String element;
    private String qualifier;
    private CitationProvider citationProvider;
    
    @Autowired
    private MetadatumFactory metadatumFactory;
    
    public void setCslProcessor(CSL cslProcessor)
    {
        this.cslProcessor = cslProcessor;
    }

    public void setDcField(String dcField)
    {
        this.dcField = dcField;
    }

    public void setCitationProvider(CitationProvider citationProvider)
    {
        this.citationProvider = citationProvider;
    }
    
    @Override
    public Metadatum generateCitation(CitationDTO citation)
    {
        citationProvider.setCitationDTO(citation);
        cslProcessor.registerCitationItems(citation.getHandle());
        
        return metadatumFactory
                .createMetadatum(schema, element, qualifier, null, cslProcessor
                        .makeBibliography().getEntries()[0]);
    }

    @Override
    public void afterPropertiesSet() throws Exception
    {
        String[] splitValue = dcField.split("\\.");
        schema = splitValue[0];
        element = splitValue[1];
        if(splitValue.length> 2)
        {
            qualifier = splitValue[2];
        }
        
        LOG.info("Values set as following :");
    }
    
}
