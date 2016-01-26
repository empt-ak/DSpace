/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.citation;

import java.util.ArrayList;
import java.util.List;
import org.dspace.content.Metadatum;
import org.springframework.stereotype.Component;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Component
public class CitationProviderFactory
{
    private List<CitationProcessor> processors;

    public void setProcessors(List<CitationProcessor> processors)
    {
        this.processors = processors;
    }
    
    public List<Metadatum> getCitationsAsMetadatum(CitationDTO citationDTO)
    {
        List<Metadatum> resultList = new ArrayList<>(processors.size());
        for(CitationProcessor cp : processors)
        {
            resultList.add(cp.generateCitation(citationDTO));
        }
        
        return resultList;
    }
}
