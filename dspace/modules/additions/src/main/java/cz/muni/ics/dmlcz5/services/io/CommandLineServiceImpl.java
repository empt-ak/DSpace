package cz.muni.ics.dmlcz5.services.io;

import cz.muni.ics.dspace5.exceptions.ParseException;
import cz.muni.ics.dspace5.impl.InputDataMap;
import cz.muni.ics.dspace5.impl.io.CLIDataProcessor;
import cz.muni.ics.dspace5.impl.io.CLIOption;
import cz.muni.ics.dspace5.impl.io.CommandLineService;
import joptsimple.OptionException;
import joptsimple.OptionParser;
import org.apache.log4j.Logger;
import org.dspace.services.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Dominik Szalai - emptulik at gmail.com on 6/23/16.
 */
public class CommandLineServiceImpl implements CommandLineService
{
    private static final Logger logger = Logger.getLogger(CommandLineServiceImpl.class);
    private static final Logger setupLogger = Logger.getLogger(ParseSetup.class);

    private Map<String, List<CLIOption>> options;
    private Map<String, ParseSetup> setups = new HashMap<>(3);

    @Autowired
    private InputDataMap inputDataMap;
    @Autowired
    private ConfigurationService configurationService;

    public void setOptions(Map<String, List<CLIOption>> options)
    {
        this.options = options;
    }

    @Override
    public CLIDataProcessor parse(String processorName, String... data) throws ParseException
    {
        try
        {
            switch (processorName)
            {
                case "import":
                    logger.info("Parsing input");
                    return new CLIDataProcessorImport(setups.get(processorName).getParser().parse(data), inputDataMap, configurationService);
                default:
                    return null;
            }
        }
        catch (OptionException ex)
        {
            try
            {
                printHelp(processorName);
                throw new ParseException(ex.getMessage());
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public void printHelp(String processorName) throws IOException
    {
        System.out.println("Usage of cz.muni.ics.dspace5." + processorName + ":");
        setups.get(processorName).getParser().printHelpOn(System.out);
    }

    @Override
    public void afterPropertiesSet() throws Exception
    {
        if (options == null || options.isEmpty())
        {
            throw new Exception("Options not set.");
        }
        else
        {
            for (String mode : options.keySet())
            {
                logger.info("Mode: " + mode + " with options: " + options.get(mode));
                setups.put(mode, new ParseSetup(options.get(mode)));
            }
        }
    }

    private class ParseSetup
    {


        private OptionParser parser = new OptionParser();

        public ParseSetup(List<CLIOption> options)
        {
            setupLogger.info("Init.");
            for (CLIOption clo : options)
            {
                setupLogger.info("Setting up option " + clo);
                if (clo.hasArgs() && clo.isRequired())
                {
                    parser.accepts(clo.getOption(), clo.getDescription())
                            .withRequiredArg().required();
                }
                else if (clo.hasArgs() && !clo.isRequired())
                {
                    parser.accepts(clo.getOption(), clo.getDescription())
                            .withOptionalArg();
                }
                else if (!clo.hasArgs() && clo.isRequired())
                {
                    parser.accepts(clo.getOption(), clo.getDescription())
                            .withRequiredArg().required();
                }
                else
                {
                    parser.accepts(clo.getOption(), clo.getDescription())
                            .withOptionalArg();
                }
            }
            options.clear();
        }


        public OptionParser getParser()
        {
            return parser;
        }
    }
}
