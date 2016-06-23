package cz.muni.ics.dspace5.impl.io;

/**
 * @author Dominik Szalai - emptulik at gmail.com on 6/23/16.
 */
public interface CLIOption
{
    String getOption();
    String getDescription();
    boolean isRequired();
    boolean hasArgs();
}
