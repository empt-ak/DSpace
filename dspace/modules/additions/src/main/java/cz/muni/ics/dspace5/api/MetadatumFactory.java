/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.api;

import org.dspace.content.Metadatum;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface MetadatumFactory
{

    /**
     * Method creates {@link Metadatum} object out of given parameters. Need for
     * this factory is quite simple. {@code Metadatum} class itself does not
     * have neither constructor nor setters method, just public field members.
     *
     * @param schema    schema for metadata entry
     * @param element   element for metadata entry
     * @param qualifier qualifier for metadata entry
     * @param language  language of metadata entry
     * @param value     value of metadata entry
     *
     * @return Metadatum class created out of parameters passed to this method
     *
     * @throws IllegalArgumentException if schema or element or value are empty
     *                                  (null or zero length strings)
     */
    Metadatum createMetadatum(String schema, String element, String qualifier, String language, String value) throws IllegalArgumentException;
}
