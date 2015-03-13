/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.imports;

import cz.muni.ics.dspace5.impl.InputArguments;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import org.dspace.authorize.AuthorizeException;
import org.dspace.content.Collection;
import org.dspace.content.Community;
import org.dspace.content.Item;
import org.dspace.core.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Component
public class ImportTools
{
    @Autowired
    private InputArguments inputArguments;
    private static final Logger logger = Logger.getLogger(ImportTools.class);
    
    /**
     * Method saves given community inside database, and if it's proper time
     * then executes commit. Proper time is when
     * {@link ImportConfig#getCommitAfterNumber() } equals 0 or the same value
     * equals {@link ImportConfig#getCurentCommitNumber() }. Otherwise {@link ImportConfig#incrementCommitNumber()
     * } is called to increase current counter.
     *
     * @param community to be stored
     * @param context
     */
    public void saveAndCommit(Community community, Context context)
    {
        try
        {
            community.update();
            context.commit();
        }
        catch(SQLException | AuthorizeException ex)
        {
            safeFailLog(ex);
        }
    }
    
    
    public void saveAndCommit(Collection collection, Context context)
    {
        try
        {
            collection.update();
            context.commit();
        }
        catch(SQLException | AuthorizeException ex)
        {
            safeFailLog(ex);
        }
    }
    
    public void saveAndCommit(Item item, Context context)
    {
        try
        {
            item.update();
            context.commit();
        }
        catch(SQLException | AuthorizeException ex)
        {
            safeFailLog(ex);
        }
    }
    
    
    
    /**
     * Method logs occurred exception and closes application if {@link ImportConfig#failsOnError()
     * } returns true.
     *
     * @param ex to be logged.
     */
    public void safeFailLog(Exception ex)
    {
        logger.error(ex,ex.getCause());
        if(inputArguments.getTypedValue("failOnError"))
        {
            // rework into runtime exception
            System.exit(1);
        }
    }
}
