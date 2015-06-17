/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.api;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface CommandLineService
{
    /**
     * Method returns proper commandline for given input type.
     * @param type of command line to be obtained
     * @return command line according to {@code type}
     * @throws IllegalArgumentException if type is null, or empty string
     * @throws UnsupportedOperationException if given type is not supported
     */
    CommandLine getCommandLine(String type) throws IllegalArgumentException, UnsupportedOperationException;
}
