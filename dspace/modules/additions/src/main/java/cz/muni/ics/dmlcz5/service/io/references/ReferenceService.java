/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dmlcz5.service.io.references;

import cz.muni.ics.dspace5.api.module.ObjectWrapper;
import java.util.List;
import org.dspace.content.Metadatum;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface ReferenceService
{

    /**
     * Method loads given reference file into list of reference entries.
     *
     * @param objectWrapper holding path to references
     *
     * @return list of references from specific file, empty list if there are no
     *         references
     */
    List<Reference> loadReferences(ObjectWrapper objectWrapper);

    /**
     * Method loads references from given object wrapper into list of string
     * representing reference line
     *
     * @param objectWrapper holding path to references
     *
     * @return list of references in form of string, empty list if there are no
     *         references
     */
    List<String> getParsedReferences(ObjectWrapper objectWrapper);

    /**
     * Method loads given object wrapper holding reference to reference file
     * into list of metadatum
     *
     * @param objectWrapper
     *
     * @return
     */
    List<Metadatum> getReferencesAsMetadata(ObjectWrapper objectWrapper);
}
