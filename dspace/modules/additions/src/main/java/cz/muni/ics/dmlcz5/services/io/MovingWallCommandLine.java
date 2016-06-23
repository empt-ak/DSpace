/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dmlcz5.services.io;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class MovingWallCommandLine //extends AbstractCommandLine
{

//    private static final Logger logger = Logger.getLogger(MovingWallCommandLine.class);
//
//    private Set<String> movingWallOptions;
//
//    public void setMovingWallOptions(Set<String> movingWallOptions)
//    {
//        this.movingWallOptions = movingWallOptions;
//    }
//
//    @Override
//    public void setOptions(List<Option> options)
//    {
//        for (Option o : options)
//        {
//            logger.debug("Adding option: " + o);
//            this.options.addOption(o);
//        }
//    }
//
//    @Override
//    public void process(String[] args) throws IllegalArgumentException, ParseException
//    {
//        org.apache.commons.cli.CommandLine cmd = getParsedCommandLine(args, options);
//
//
//        String method = cmd.getOptionValue("method","auto");
//
//        if(!movingWallOptions.contains(method))
//        {
//            throw new IllegalArgumentException();
//        }
//
//        inputDataMap.put("mwmethod", method);
//
//        super.parseUser(cmd);
//
//        inputDataMap.put("value", Paths.get(configurationService.getProperty("meditor.rootbase"), cmd.getOptionValue("p")));
//
//        if (cmd.hasOption("u"))
//        {
//            inputDataMap.put("user", cmd.getOptionValue("u"));
//        }
//
//        inputDataMap.put("mwonly", true);
//    }
}
