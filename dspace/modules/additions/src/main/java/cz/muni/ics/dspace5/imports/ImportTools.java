/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.imports;

import cz.muni.ics.dspace5.impl.AbstractTools;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import org.dspace.authorize.AuthorizeException;
import org.dspace.content.Collection;
import org.dspace.content.Community;
import org.dspace.content.Item;
import org.dspace.core.Context;
import org.springframework.stereotype.Component;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Component
public class ImportTools extends AbstractTools
{
    private static final Logger logger = Logger.getLogger(ImportTools.class);

    /**
     * Method updates given community inside database and commits context.
     *
     * @param community to be updated (saved)
     *
     * @see Community#update()
     * @see Context#commit()
     */
    public void saveAndCommit(Community community)
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
    public void saveAndCommit(Collection collection)
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
    public void saveAndCommit(Item item)
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
    public void safeFailLog(Exception ex)
    {
        logger.error(ex, ex.getCause());
        if (importDataMap.getTypedValue("failOnError"))
        {
            // TODO ?
            // rework into runtime exception
            System.exit(1);
        }
    }
}
