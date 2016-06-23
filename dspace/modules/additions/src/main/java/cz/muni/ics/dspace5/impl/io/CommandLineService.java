package cz.muni.ics.dspace5.impl.io;

import cz.muni.ics.dspace5.exceptions.ParseException;
import org.springframework.beans.factory.InitializingBean;

import java.io.IOException;

/**
 * @author Dominik Szalai - emptulik at gmail.com on 6/23/16.
 */
public interface CommandLineService extends InitializingBean
{
    CLIDataProcessor parse(String processorName, String... data) throws ParseException;

    void printHelp(String processorName) throws IOException;
}
