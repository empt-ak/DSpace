/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.api;

import java.nio.file.Path;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface ObjectWrapperResolverFactory
{
    /**
     * Method returns specific resolver strategy used for object tree resolving. Based on given input proper strategy is returned.
     * @param type of strategy to be returned
     * @return strategy based on {@code type} value
     * @throws IllegalArgumentException if type is not supported.
     */
    ObjectWrapperResolver provideObjectWrapperResolver(String type) throws IllegalArgumentException;
    
    /**
     * Method returns specific resolver strategy used for object tree resolving. The result is based on given input {@code path}.
     * @param path to be imported
     * @return strategy resolver based on {@code path} value
     * @throws IllegalArgumentException if path is not valid
     */
    ObjectWrapperResolver provideObjectWrapperResolver(Path path) throws IllegalArgumentException;
}
