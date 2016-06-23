/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.citation;

import de.undercouch.citeproc.ItemDataProvider;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface CitationProvider extends ItemDataProvider
{
    void setCitationDTO(CitationDTO citationDTO);
}
