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
public class CollectionCelebrityComparator implements Comparator<Collection>
{

    @Override
    public int compare(Collection o1, Collection o2)
    {
        return Integer.valueOf(
                o1.getMetadataByMetadataString("digilib.position.issue")[0].value
        ).compareTo(
                Integer.valueOf(
                        o2.getMetadataByMetadataString("digilib.position.issue")[0].value
                )
        );
    }
    
}
