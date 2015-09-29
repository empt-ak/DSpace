/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.comparators;

import java.nio.file.Path;
import java.util.Comparator;
import org.apache.commons.cli.Option;

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
    public abstract Comparator<Path> provideIssuePathComparator();
    public abstract Comparator<Path> provideArticlePathComparator();
    public abstract Comparator<Option> provideOptionComparator(); 
}
