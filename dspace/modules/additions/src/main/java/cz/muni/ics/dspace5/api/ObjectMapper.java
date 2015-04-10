/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.api;

import java.io.FileNotFoundException;
import java.nio.file.Path;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface ObjectMapper
{

    /**
     * Method converts given xml on path for example detail.xml, into
     * appropriate object.
     *
     * @param <T>      result type of object
     * @param p        path where given xml file is
     * @param fileName name of file
     *
     * @return object converted from xml, null if error occurs
     *
     * @throws FileNotFoundException    if directory does not exist, or given
     *                                  file does not.
     * @throws IllegalArgumentException if fileName is empty, does not contain
     *                                  xml extension or is zero length
     *                                  <b>.xml</b> only.
     */
    <T> T convertPathToObject(Path p, String fileName) throws IllegalArgumentException, FileNotFoundException;
}
