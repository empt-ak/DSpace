/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.core;

import java.nio.file.Path;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface HandleService
{

    /**
     * Method obtains handle value for given object on given path. If it does
     * not exists, it is created.
     *
     * @param path to object of witch we would like to load handle
     *
     * @return handle for given path in form of <b>prefix/suffix</b>
     */
    String getHandleForPath(Path path);
    
    String getVolumeHandle(Path path);
}
