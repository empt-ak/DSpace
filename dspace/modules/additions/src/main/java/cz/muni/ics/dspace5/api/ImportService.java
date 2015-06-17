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
public interface ImportService
{

    /**
     * Executing this method will lead into importing specified object into
     * DSpace repository. Behaviour and target object are stored in {@code args}
     * argument passed from command line.
     *
     * @param args values required for import passed from command line.
     */
    void execute(String[] args);
}
