/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.digilib.service.io.references;

import cz.muni.ics.dspace5.api.ObjectMapper;
import cz.muni.ics.dspace5.api.module.ObjectWrapper;
import cz.muni.ics.dspace5.metadata.MetadatumFactory;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
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
    
    private final Pattern p;
    
    
    public ReferenceServiceImpl()
    {
        // moved to constructor so it does not appear in class diagram
        p = Pattern.compile("(?i)<?\\b((?:(https?|ftp):(?:/{1,3}|[a-z0-9%])|[a-z0-9.\\-]+[.](?:com|net|org|edu|gov|mil|aero|asia|biz|cat|coop|info|int|jobs|mobi|museum|name|post|pro|tel|travel|xxx|ac|ad|ae|af|ag|ai|al|am|an|ao|aq|ar|as|at|au|aw|ax|az|ba|bb|bd|be|bf|bg|bh|bi|bj|bm|bn|bo|br|bs|bt|bv|bw|by|bz|ca|cc|cd|cf|cg|ch|ci|ck|cl|cm|cn|co|cr|cs|cu|cv|cx|cy|cz|de|dj|dk|dm|do|dz|ec|ee|eg|eh|er|es|et|eu|fi|fj|fk|fm|fo|fr|ga|gb|gd|ge|gf|gg|gh|gi|gl|gm|gn|gp|gq|gr|gs|gt|gu|gw|gy|hk|hm|hn|hr|ht|hu|id|ie|il|im|in|io|iq|ir|is|it|je|jm|jo|jp|ke|kg|kh|ki|km|kn|kp|kr|kw|ky|kz|la|lb|lc|li|lk|lr|ls|lt|lu|lv|ly|ma|mc|md|me|mg|mh|mk|ml|mm|mn|mo|mp|mq|mr|ms|mt|mu|mv|mw|mx|my|mz|na|nc|ne|nf|ng|ni|nl|no|np|nr|nu|nz|om|pa|pe|pf|pg|ph|pk|pl|pm|pn|pr|ps|pt|pw|py|qa|re|ro|rs|ru|rw|sa|sb|sc|sd|se|sg|sh|si|sj|Ja|sk|sl|sm|sn|so|sr|ss|st|su|sv|sx|sy|sz|tc|td|tf|tg|th|tj|tk|tl|tm|tn|to|tp|tr|tt|tv|tw|tz|ua|ug|uk|us|uy|uz|va|vc|ve|vg|vi|vn|vu|wf|ws|ye|yt|yu|za|zm|zw)/)(?:[^\\s()<>{}\\[\\]]+|\\([^\\s()]*?\\([^\\s()]+\\)[^\\s()]*?\\)|\\([^\\s]+?\\))+(?:\\([^\\s()]*?\\([^\\s()]+\\)[^\\s()]*?\\)|\\([^\\s]+?\\)|[^\\s`!()\\[\\]{};:'\".,<>?«»“”‘’])|(?:(?<!@)[a-z0-9]+(?:[.\\-][a-z0-9]+)*[.](?:com|net|org|edu|gov|mil|aero|asia|biz|cat|coop|info|int|jobs|mobi|museum|name|post|pro|tel|travel|xxx|ac|ad|ae|af|ag|ai|al|am|an|ao|aq|ar|as|at|au|aw|ax|az|ba|bb|bd|be|bf|bg|bh|bi|bj|bm|bn|bo|br|bs|bt|bv|bw|by|bz|ca|cc|cd|cf|cg|ch|ci|ck|cl|cm|cn|co|cr|cs|cu|cv|cx|cy|cz|de|dj|dk|dm|do|dz|ec|ee|eg|eh|er|es|et|eu|fi|fj|fk|fm|fo|fr|ga|gb|gd|ge|gf|gg|gh|gi|gl|gm|gn|gp|gq|gr|gs|gt|gu|gw|gy|hk|hm|hn|hr|ht|hu|id|ie|il|im|in|io|iq|ir|is|it|je|jm|jo|jp|ke|kg|kh|ki|km|kn|kp|kr|kw|ky|kz|la|lb|lc|li|lk|lr|ls|lt|lu|lv|ly|ma|mc|md|me|mg|mh|mk|ml|mm|mn|mo|mp|mq|mr|ms|mt|mu|mv|mw|mx|my|mz|na|nc|ne|nf|ng|ni|nl|no|np|nr|nu|nz|om|pa|pe|pf|pg|ph|pk|pl|pm|pn|pr|ps|pt|pw|py|qa|re|ro|rs|ru|rw|sa|sb|sc|sd|se|sg|sh|si|sj|Ja|sk|sl|sm|sn|so|sr|ss|st|su|sv|sx|sy|sz|tc|td|tf|tg|th|tj|tk|tl|tm|tn|to|tp|tr|tt|tv|tw|tz|ua|ug|uk|us|uy|uz|va|vc|ve|vg|vi|vn|vu|wf|ws|ye|yt|yu|za|zm|zw)\\b/?(?!@)))>?");
    }
    
    @PostConstruct
    private void init()
    {
        this.referenceFileName = configurationService.getProperty("meditor.reference.file");
        logger.info("Reference file set to: "+referenceFileName);
    }
    
    @Override
    public List<Reference> loadReferences(ObjectWrapper objectWrapper)
    {
        References r = null;
        
        try
        {
            r = objectMapper.convertPathToObject(objectWrapper.getPath(), referenceFileName);
        }
        catch(FileNotFoundException nfe)
        {
            logger.debug(nfe,nfe.getCause());
        }
        
        if(r != null)
        {
            return r.getReference();
        }
        else
        {
            return Collections.emptyList();
        }
    }

    @Override
    public List<String> getParsedReferences(ObjectWrapper objectWrapper)
    {
        List<Reference> references = loadReferences(objectWrapper);
        StringBuilder builder = new StringBuilder(100);
        List<String> result = new ArrayList<>(references.size());
        
        for(Reference r : references)
        {
            if(!StringUtils.isEmpty(r.getPrefix()))
            {
                builder.append(r.getPrefix()).append(" ");
            }
            if(!StringUtils.isEmpty(r.getAuthors()))
            {
                builder.append(r.getAuthors()).append(" ");
            }
            if(!StringUtils.isEmpty(r.getTitle()))
            {
                builder.append("<b>").append(r.getTitle()).append("</b>");
            }
            
            builder.append(p.matcher(r.getSuffix()).replaceAll(" <a href=\"$1\">$1</a>"));           
            
            if(r.getLinks() != null && !r.getLinks().getLink().isEmpty())
            {
                builder.append(" | ");
                for(int i = 0; i < r.getLinks().getLink().size(); i++)
                {
                    Link l = r.getLinks().getLink().get(i);
                    
                    appendLink(l, builder);
                    
                    if(i < r.getLinks().getLink().size() -1)
                    {
                        builder.append(" | ");
                    }
                }
            }
            
            result.add(builder.toString());
            builder.delete(0, builder.length());
        }        
        
        return result;
    }    

    @Override
    public List<Metadatum> getReferencesAsMetadata(ObjectWrapper objectWrapper)
    {
        List<String> metadata = getParsedReferences(objectWrapper);
        List<Metadatum> result = new ArrayList<>(metadata.size());
        
        for(String s : metadata)
        {
            result.add(metadatumFactory.createMetadatum("muni", "reference", null, null, s));
        }
        
        return result;
    }
    
    private void appendLink(Link link,StringBuilder builder)
    {
        builder.append("<a href=\"");
        builder.append(link.getValue());
        builder.append("\">");
        builder.append(link.getSource().toUpperCase());
        builder.append(" ");
        builder.append(link.getId());
        builder.append("</a>");
    }    
}
