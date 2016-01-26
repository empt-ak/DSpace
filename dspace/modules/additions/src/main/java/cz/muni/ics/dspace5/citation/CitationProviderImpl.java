/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.citation;

import de.undercouch.citeproc.csl.CSLItemData;
import de.undercouch.citeproc.csl.CSLItemDataBuilder;
import de.undercouch.citeproc.csl.CSLType;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class CitationProviderImpl implements CitationProvider
{
    private CitationDTO citationDTO;
    
    @Override
    public void setCitationDTO(CitationDTO citationDTO)
    {
        this.citationDTO = citationDTO;
    }

    @Override
    public CSLItemData retrieveItem(String id)
    {
        return new CSLItemDataBuilder()
                .id(citationDTO.getHandle())
                .type(CSLType.ARTICLE_JOURNAL)
                .title("A dummy journal article")
                .author("John", "Smith")
                .issued(2013, 9, 6)
                .containerTitle("Dummy journal")
                .build();
    }

    @Override
    public String[] getIds()
    {
        return new String[]{citationDTO.getHandle()};
    }
    
}
