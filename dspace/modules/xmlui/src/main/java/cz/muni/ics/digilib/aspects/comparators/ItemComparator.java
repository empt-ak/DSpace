/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.digilib.aspects.comparators;

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
        int position1 = Integer.valueOf(o1.getMetadata("digilib.position.article"));
        int position2 = Integer.valueOf(o2.getMetadata("digilib.position.article"));
        
        return position1 - position2;
    }
    
}
