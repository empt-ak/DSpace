/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dmlcz5.aspects.comparators;

import java.util.Comparator;
import org.dspace.content.Item;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class ItemComparator implements Comparator<Item>
{

    @Override
    public int compare(Item o1, Item o2)
    {
        int position1 = Integer.valueOf(o1.getMetadata("dc.identifier.position"));
        int position2 = Integer.valueOf(o2.getMetadata("dc.identifier.position"));
        
        return position1 - position2;
    }
    
}
