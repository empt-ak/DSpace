/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.comparators;

import cz.muni.ics.dspace5.impl.DSpaceTools;
import java.nio.file.Path;
import java.util.Comparator;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class IssuePathComparator implements Comparator<Path>
{
    @Autowired
    private DSpaceTools dSpaceTools;
    
    @Override
    public int compare(Path o1, Path o2)
    {
        return dSpaceTools.getIssueNumber(o1).compareTo(dSpaceTools.getIssueNumber(o2));
    }    
}
