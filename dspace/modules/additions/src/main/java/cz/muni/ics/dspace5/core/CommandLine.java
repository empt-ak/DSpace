/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.core;

import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface CommandLine
{
    Options getOptions();
    void process(String[] args) throws IllegalArgumentException, ParseException;
}
