/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.movingwall;

import cz.muni.ics.dspace5.exceptions.MovingWallException;
import java.util.Map;
import org.dspace.content.DSpaceObject;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface MovingWallService
{
    void execute(String[] args);
    
    void lock(DSpaceObject dSpaceObject, Map<String,Object> dataMap) throws IllegalArgumentException, MovingWallException;
    void unlock(DSpaceObject dSpaceObject, Map<String,Object> dataMap) throws IllegalArgumentException, MovingWallException;
}
