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
public interface DSpaceObjectService
{
    /**
     * Running this method, executes one of possible DSpace I/O services. Before method is executed, caller must ensure that using {@code CommandLineService} required arguments are stored inside {@code InputDataMap}. Following table  
     */
    void execute();
}
