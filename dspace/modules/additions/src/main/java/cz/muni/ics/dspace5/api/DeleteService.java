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
public interface DeleteService
{

    /**
     * Calling method executes all steps which will lead into deletion of given
     * object specified by input arguments.
     *
     * @param args values required for deletion passed from command line.
     */
    void execute(String[] args);
}
