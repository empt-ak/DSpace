/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dmlcz5.aspects;

import cz.muni.ics.dmlcz5.aspects.comparators.CollectionSerialComparator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.dspace.content.Collection;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class AspectUtils
{
    public static List<Collection> getSortedIssuesForVolume(Collection[] unsorted)
    {
        if(unsorted != null && unsorted.length > 0)
        {
            List<Collection> result = new ArrayList<>(unsorted.length);
            result.addAll(Arrays.asList(unsorted));
            Collections.sort(result, new CollectionSerialComparator());
            
            return result;
        }
        else
        {
            return Collections.emptyList();
        }        
    }
}
