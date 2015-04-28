/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.comparators;

import java.nio.file.Path;
import java.util.Comparator;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class ArticlePathComparator implements Comparator<Path>
{    
    @Override
    public int compare(Path o1, Path o2)
    {
        int i1 = Integer.parseInt(o1.getFileName().toString().substring(1));
        int i2 = Integer.parseInt(o2.getFileName().toString().substring(1));
        
        return i1-i2;
    }    
}
