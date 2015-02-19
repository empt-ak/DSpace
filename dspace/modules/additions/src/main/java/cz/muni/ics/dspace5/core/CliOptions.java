/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.core;

import org.apache.commons.cli.Options;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface CliOptions
{
    /**
     * Method returns Apache CLI options for given mode.
     *
     * @param mode which decides about output options
     *
     * @return options based on {@code mode} value
     */
    Options getOptions(CommandLineService.Mode mode);
}
