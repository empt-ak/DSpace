/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.api;

import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface CommandLine
{

    /**
     * Method returns options for given command line type.
     *
     * @return options for command line
     */
    Options getOptions();

    /**
     * Method processes given arguments from command line.
     *
     * @param args arguments passed from main method
     *
     * @throws IllegalArgumentException if arguments are empty, or null
     * @throws ParseException           if input does not meet required values
     */
    void process(String[] args) throws IllegalArgumentException, ParseException;
}
