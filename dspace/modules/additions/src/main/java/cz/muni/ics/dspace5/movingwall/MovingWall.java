/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.movingwall;

import org.joda.time.DateTime;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface MovingWall
{
    DateTime getEndDate();

    DateTime getPublDate();
    
    String getRightsAccess();
    
    boolean isOpenAccess();    
}
