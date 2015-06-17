/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.imports;

import cz.muni.ics.dspace5.api.module.ModuleManager;
import cz.muni.ics.dspace5.api.module.ObjectWrapper;
import cz.muni.ics.dspace5.impl.ContextWrapper;
import cz.muni.ics.dspace5.impl.DSpaceTools;
import cz.muni.ics.dspace5.impl.ImportDataMap;
import java.sql.SQLException;
import java.util.List;
import org.apache.log4j.Logger;
import org.dspace.authorize.AuthorizeException;
import org.dspace.content.Collection;
import org.dspace.content.Community;
import org.dspace.content.Item;
import org.dspace.core.Context;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public abstract class AbstractImport
{
    private static final Logger logger = Logger.getLogger(AbstractImport.class);
    
    @Autowired
    protected ModuleManager moduleManager;
    @Autowired
    protected ImportDataMap importDataMap;
    @Autowired
    protected ContextWrapper contextWrapper;
    @Autowired
    protected DSpaceTools dSpaceTools;

    /**
     * Method updates given community inside database and commits context.
     *
     * @param community to be updated (saved)
     *
     * @see Community#update()
     * @see Context#commit() 
     */
    protected void saveAndCommit(Community community)
    {
        try
        {
            community.update();
            contextWrapper.getContext().commit();
        }
        catch (SQLException | AuthorizeException ex)
        {
            safeFailLog(ex);
        }
    }

    /**
     * Method updates given collection inside database and commits context.
     *
     * @param collection to be updated (saved)
     *
     * @see Context#commit()
     * @see Collection#update()
     */
    protected void saveAndCommit(Collection collection)
    {
        try
        {
            collection.update();
            contextWrapper.getContext().commit();
        }
        catch (SQLException | AuthorizeException ex)
        {
            safeFailLog(ex);
        }
    }

    /**
     * Method updates given item inside database, commits context and removes
     * {@code item} from context {@code cache}. Removing item from context cache
     * is crucial as cache would be populated with items and never removed. This
     * would result in extremely big {@code HashMap} which would hold not only
     * Items, but all sublevels such as {@code Bundle} and {@code Bitstream}. If
     * {@code -Xmx} flag is not set high enough, then populating cache leads to
     * {@link OutOfMemoryError}. Therefore after we are done with {@code Item}
     * then calling {@link Item#decache() } is required.
     *
     * @param item
     */
    protected void saveAndCommit(Item item)
    {
        try
        {
            item.update();
            contextWrapper.getContext().commit();
            // DO NOT EVER REMOVE THIS LINE
            // once object is loaded out of database then its stored inside
            // context cache, however it is never deleted.
            // the only way how ot remove it calling this method
            // if item would not be decached then OutOfMemoryError WILL occur.
            item.decache();
        }
        catch (SQLException | AuthorizeException ex)
        {
            safeFailLog(ex);
        }
    }

    /**
     * Method logs occurred exception and closes application if {@code fail-on-error} argument was passed.
     *
     * @param ex to be logged.
     */
    protected void safeFailLog(Exception ex)
    {
        logger.error(ex, ex.getCause());
        if (importDataMap.getTypedValue("failOnError"))
        {
            // TODO ?
            // rework into runtime exception
            System.exit(1);
        }
    }
    
    
    /**
     * Method returns last member of given list.
     * @param <T> type of automatic cast
     * @param parents list of previous parents
     * @return previous parent
     * @throws ClassCastException if &lt;T&gt; is not the proper type for parent. 
     */
    protected <T> T getLastParent(List<ObjectWrapper> parents) throws ClassCastException
    {
        return (T) parents.get(parents.size()-1);
    }
}
