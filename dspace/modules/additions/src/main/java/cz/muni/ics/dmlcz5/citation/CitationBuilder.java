/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dmlcz5.citation;

import cz.muni.ics.dmlcz5.domain.Article;
import cz.muni.ics.dspace5.api.module.ObjectWrapper;
import cz.muni.ics.dspace5.citation.CitationDTO;
import org.springframework.stereotype.Component;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Component
public class CitationBuilder
{
    private String title;
    private String handle;
    
    public void process(Article article, ObjectWrapper objectWrapper)
    {
        this.title = article.getTitle().get(0).getValue();
        this.handle = objectWrapper.getHandle();
    }
    
    public CitationDTO build()
    {
        CitationDTO citation = new CitationDTO();
        citation.setHandle(handle);
        citation.setTitle(title);
        
        return citation;
    }
}
