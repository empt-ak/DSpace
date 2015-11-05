/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.digilib.aspects.comparators;

import java.util.Comparator;
import org.apache.log4j.Logger;
import org.dspace.content.Collection;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class CollectionMonographyComparator implements Comparator<Collection>
{
    private static final Logger logger = Logger.getLogger(CollectionMonographyComparator.class);
    
    @Override
    public int compare(Collection o1, Collection o2)
    {       
        int position1 = Integer.valueOf(o1.getMetadata("dc", "identifier", "position", null)[0].value);
        int position2 = Integer.valueOf(o2.getMetadata("dc", "identifier", "position", null)[0].value);
        return position1 - position2;
    }
    
}
