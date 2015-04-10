/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.core;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface CommandLineService
{
    CommandLine getCommandLine(String type) throws IllegalArgumentException, UnsupportedOperationException;
//
//    enum Mode
//    {
//
//        IMPORT,
//        DELETE
//    }
//
//    /**
//     * Method takes array of arguments passed from method execution in command
//     * line and creates a commandLine parser out of it.
//     *
//     * @param args array of arguments passed to parser
//     * @param mode parse mode, whether we are doing import, or delete
//     *
//     * @throws ParseException if any error occurs
//     */
//    void parseInput(String[] args, Mode mode) throws ParseException, IllegalArgumentException;
}
