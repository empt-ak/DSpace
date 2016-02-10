/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dmlcz5.services.io.references;

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
     * Method converts references for given objectWrapper. File is specified by
     * configuration file under property key {@code meditor.reference.file}. If
     * no file is found then empty list of metadata is returned.
     *
     * @param objectWrapper containing path to references
     * @return list of metadata for given object wrapper
     * @throws IllegalArgumentException if objectWrapper is null, or does not
     * contain path
     */
    List<Metadatum> processReferences(ObjectWrapper objectWrapper) throws IllegalArgumentException;
}
