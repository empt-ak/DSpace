/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.citation;

import org.dspace.content.Metadatum;
import org.springframework.beans.factory.InitializingBean;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface CitationProcessor extends InitializingBean
{
    Metadatum generateCitation(CitationDTO citation);
}
