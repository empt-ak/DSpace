/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.comparators;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public abstract class ComparatorFactory
{
    /**
     * Method provides singleton instance of {@code IssuePathComparator}
     * @return comparator used for comparing (sorting) issues.
     */
    public abstract IssuePathComparator provideIssuePathComparator();
}
