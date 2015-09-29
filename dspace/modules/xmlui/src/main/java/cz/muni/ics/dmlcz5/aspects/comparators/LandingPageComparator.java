/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dmlcz5.aspects.comparators;

import java.util.Comparator;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class LandingPageComparator implements Comparator<String>
{
    private final String order ="smpc";

    @Override
    public int compare(String o1, String o2)
    {
        return order.indexOf(o1.charAt(0)) - order.indexOf(o2.charAt(0));
    }    
}
