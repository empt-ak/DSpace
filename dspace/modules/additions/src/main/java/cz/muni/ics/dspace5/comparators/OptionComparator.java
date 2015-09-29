/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.comparators;

import java.util.Comparator;
import org.apache.commons.cli.Option;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class OptionComparator implements Comparator<Option>
{

    @Override
    public int compare(Option o1, Option o2)
    {
        if(o1.isRequired() && o2.isRequired())
        {
            return o1.getLongOpt().compareTo(o2.getLongOpt());
        }
        if(o1.isRequired())
        {
            return 1;
        }
        else
        {
            return -1;
        }
    }
    
}
