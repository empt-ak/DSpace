package cz.muni.ics.dmlcz5.services.io;

import cz.muni.ics.dspace5.impl.InputDataMap;
import cz.muni.ics.dspace5.impl.io.CLIDataProcessor;
import joptsimple.OptionSet;
import org.apache.log4j.Logger;
import org.dspace.services.ConfigurationService;

import java.nio.file.Paths;

/**
 * @author Dominik Szalai - emptulik at gmail.com on 6/23/16.
 */
public class CLIDataProcessorImport implements CLIDataProcessor
{
    private static final Logger logger = Logger.getLogger(CLIDataProcessorImport.class);
    private OptionSet optionSet;
    private InputDataMap inputDataMap;
    private ConfigurationService configurationService;

    public CLIDataProcessorImport(OptionSet optionSet, InputDataMap inputDataMap, ConfigurationService configurationService)
    {
        this.optionSet = optionSet;
        this.inputDataMap = inputDataMap;
        this.configurationService = configurationService;
        logger.debug("Init");
    }

    @Override
    public void process()
    {
        String user = (String) optionSet.valueOf("username");
        String method = (String) optionSet.valueOf("method");
        String value = (String) optionSet.valueOf("value");

        if (user != null)
        {
            inputDataMap.put("username", user);
        }

        if (method == null && !method.equals("update") && !method.equals("single"))
        {
            logger.error(method);
            throw new IllegalArgumentException("Unknown --method argument");
        }

        if (value == null)
        {
            logger.error(value);
            throw new IllegalArgumentException("Required value for --value argument not set"); // should not occur
        }

        logger.info("User: " + user + ";method: " + method + ";value:" + value);
        inputDataMap.put("method", method);
        inputDataMap.put("value", Paths.get(configurationService.getProperty("meditor.rootbase"), value));
        inputDataMap.put("mwmethod", "off");
    }
}
