/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.digilib.services;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface DSpaceExecutor
{
    void execute(String commandLine, String[] args);
}
