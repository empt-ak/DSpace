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

    /**
     * Method compares given issues represented by path. Issue is represented by
     * filename such as
     * <b>1-1992-1</b>, <b>1-1992-3</b> and <b>1-1992-2</b>. The number after
     * last dash is issue number. So 3 files represent issues <b>1,3,2</b>, or
     * <b>1,2,3</b> in ordered form.
     *
     * @param o1
     * @param o2
     *
     * @return
     */
    @Override
    public int compare(Path o1, Path o2)
    {
        return dSpaceTools.getIssueNumber(o1).compareTo(dSpaceTools.getIssueNumber(o2));
    }
}
