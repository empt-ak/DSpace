/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.digilib.services.io;

import cz.muni.ics.dspace5.api.CommandLine;
import cz.muni.ics.dspace5.api.CommandLineService;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public abstract class CommandLineServiceImpl implements CommandLineService
{
    protected abstract CommandLine getImportCommandLine();
    protected abstract CommandLine getDeleteCommandLine();
    protected abstract CommandLine getMovingWallCommandLine();
    protected abstract CommandLine getSetupCommandLine();

    @Override
    public CommandLine getCommandLine(String type) throws IllegalArgumentException, UnsupportedOperationException
    {
        if(type == null || type.isEmpty())
        {
            throw new IllegalArgumentException("Type is null or empty.");
        }
        
        switch(type)
        {
            case "import":
                return getImportCommandLine();
            case "delete":
                return getDeleteCommandLine();
            case "movingwall":
                return getMovingWallCommandLine();
            case "setup":
                return getSetupCommandLine();
            default:
                throw new UnsupportedOperationException("Given type does not exist possible values are [import/delete,movingwall] but was ["+type+"]");
        }
    }    
}
