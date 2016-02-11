/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.digilaw.aspects.comparators;

import java.util.Comparator;
import org.apache.log4j.Logger;
import org.dspace.content.Collection;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class CollectionProceedingComparator implements Comparator<Collection>
{
    private static final Logger logger = Logger.getLogger(CollectionProceedingComparator.class);
    
    @Override
    public int compare(Collection o1, Collection o2)
    {   
        return o1
                .getMetadata("dc", 
                        "title", 
                        "acronym", null)[0].value
                .compareTo(o2.getMetadata("dc", 
                        "title", 
                        "acronym", null)[0].value);
    }
    
}
