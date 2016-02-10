/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dmlcz5.services.io.references;

import cz.muni.ics.dspace5.api.ObjectMapper;
import cz.muni.ics.dspace5.api.module.ObjectWrapper;
import cz.muni.ics.dspace5.metadata.MetadatumFactory;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import org.apache.log4j.Logger;
import org.dspace.content.Metadatum;
import org.dspace.services.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Component
public class ReferenceServiceImpl implements ReferenceService
{
    private static final Logger logger = Logger.getLogger(ReferenceServiceImpl.class);
    private String referenceFileName;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private MetadatumFactory metadatumFactory;

    @PostConstruct
    private void init()
    {
        referenceFileName = configurationService.getProperty("meditor.reference.file");
        logger.debug("Reference file set to: " + referenceFileName);
    }

    @Override
    public List<Metadatum> processReferences(ObjectWrapper objectWrapper) throws IllegalArgumentException
    {
        if(objectWrapper == null)
        {
            throw new IllegalArgumentException("Given objectWrapper is null");
        }
        if(objectWrapper.getPath() == null)
        {
            throw new IllegalArgumentException("Given objectWrapper does not hold path.");
        }
        
        // init empty list if nothing is goin to be processed
        List<Metadatum> result = Collections.emptyList();
        
        // check if file exists so filenotfound is not thrown
        if (Files.exists(objectWrapper.getPath().resolve(referenceFileName)))
        {
            // prepare container for xml references
            List<Reference> references = null;

            try
            {
                references = ((References) objectMapper.convertPathToObject(objectWrapper.getPath(), referenceFileName)).getReference();
            }
            catch (IllegalArgumentException | FileNotFoundException ex)
            {
                logger.error(ex);
            }
            
            if(references != null && !references.isEmpty())
            {   
                // reinit restul list
                result = new ArrayList<>(references.size());
                
                for(Reference r : references)
                {
                    result.add(metadatumFactory.createMetadatum("dc", "relation", "isbasedon",null, convert(r)));
                }                            
            }
        }
        else
        {
            logger.debug("Reference file not found for " + objectWrapper.getPath());
        }
        
        return result;
    }
    
    /**
     * Method converts given reference into its String representation. String representation has following form:
     * <p>
     * Author1, Author2: &lt;b&gt;Title&lt/b&gt;. Suffix Link1 | Link2 ...
     * </p>
     * @param reference to be converted
     * @return String representation of reference
     */
    private String convert(Reference reference)
    {
        StringBuilder sb = new StringBuilder(reference.getPrefix());
        sb.append(" ");
        
        for(Iterator<String> iter = reference.getAuthors().getAuthor().iterator(); iter.hasNext();)
        {
            sb.append(iter.next());
            if(iter.hasNext())
            {
                sb.append(", ");
            }
        }
        
        sb.append(": ");
        sb.append("<b>");
        sb.append(reference.getTitle());
        sb.append("</b>.");
        sb.append(reference.getSuffix());
        sb.append(" ");
        
        for(Iterator<Link> iter = reference.getLinks().getLink().iterator(); iter.hasNext();)
        {
            sb.append(convertLink(iter.next()));
            if(iter.hasNext())
            {
                sb.append(" | ");
            }
        }
        
        return sb.toString();        
    }
    
    /**
     * Method converts link into a href link.
     * @param link to be converted
     * @return string representation of input link
     */
    private String convertLink(Link link)
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append("<a href=\"");
        sb.append(link.getValue());
        sb.append("\">");
        
        switch(link.getSource())
        {
            case "zbl":
                sb.append("Zbl ");
                break;
            case "mref":
                sb.append("MR ");
                break;
            case "doi":
                sb.append("DOI ");
                break;                
        }
        
        sb.append(link.getId());
        sb.append("</a>");
        
        return sb.toString();        
    }

}
