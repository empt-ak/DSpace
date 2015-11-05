/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.digilib.aspects.comparators;

import java.util.Comparator;
import org.dspace.content.Collection;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class CollectionSerialComparator implements Comparator<Collection>
{

    @Override
    public int compare(Collection o1, Collection o2)
    {
        int i1 = -1;
        int i2 = -1;
        
        try
        {
            i1 = Integer.valueOf(o1.getMetadataByMetadataString("dc.identifier.position")[0].value);
        }
        catch(ArrayIndexOutOfBoundsException ex)
        {
            throw new ArrayIndexOutOfBoundsException(o1.getHandle());
        }
        
        try
        {
            i2 = Integer.valueOf(o2.getMetadataByMetadataString("dc.identifier.position")[0].value);
        }
        catch(ArrayIndexOutOfBoundsException ex)
        {
            throw new ArrayIndexOutOfBoundsException(o2.getHandle());
        }
        
        return i1-i2;
    }    
}
