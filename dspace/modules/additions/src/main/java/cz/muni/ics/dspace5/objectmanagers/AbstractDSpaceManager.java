/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.objectmanagers;

import cz.muni.ics.dspace5.api.module.ModuleManager;
import cz.muni.ics.dspace5.api.module.ObjectWrapper;
import cz.muni.ics.dspace5.impl.ContextWrapper;
import cz.muni.ics.dspace5.impl.DSpaceTools;
import cz.muni.ics.dspace5.impl.InputDataMap;
import java.sql.SQLException;
import java.util.List;
import org.apache.log4j.Logger;
import org.dspace.authorize.AuthorizeException;
import org.dspace.content.DSpaceObject;
import org.dspace.content.Item;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Common parent for all DSpace object managers.
 *
 * @author Dominik Szalai - emptulik at gmail.com
 * @param <T> type of manager
 */
public abstract class AbstractDSpaceManager<T extends DSpaceObject> implements DSpaceObjectManager<T>
{

    private static final Logger logger = Logger.getLogger(AbstractDSpaceManager.class);

    @Autowired
    protected ModuleManager moduleManager;
    @Autowired
    protected InputDataMap inputDataMap;
    @Autowired
    protected ContextWrapper contextWrapper;
    @Autowired
    protected DSpaceTools dSpaceTools;

    protected static final String ANY = Item.ANY;

    /**
     * Method takes {@code DSpaceObject} and updates it inside database and
     * commits it into context. If passed object is {@code Item} then it is
     * decached from context
     *
     * @param t to be updated and commited
     */
    protected void saveAndCommit(T t)
    {
        try
        {
            t.update();
            contextWrapper.getContext().commit();

            // DO NOT EVER REMOVE THIS LINE
            // once object is loaded out of database then its stored inside
            // context cache, however it is never deleted.
            // the only way how ot remove it calling this method
            // if item would not be decached then OutOfMemoryError WILL occur.
            if (t instanceof Item)
            {
                Item i = (Item) t;
                i.decache();
            }
        }
        catch (SQLException | AuthorizeException ex)
        {
            safeFailLog(ex);
        }
    }

    /**
     * Method logs occurred exception and closes application if
     * {@code fail-on-error} argument was passed.
     *
     * @param ex to be logged.
     */
    protected void safeFailLog(Exception ex)
    {
        logger.error(ex, ex.getCause());
        if (inputDataMap.getTypedValue("failOnError"))
        {
            // TODO ?
            // rework into runtime exception
            System.exit(1);
        }
    }

    /**
     * Method returns last member of given list.
     *
     * @param <T>     type of automatic cast
     * @param parents list of previous parents
     *
     * @return previous parent
     *
     * @throws ClassCastException if &lt;T&gt; is not the proper type for
     *                            parent.
     */
    protected <T> T getLastParent(List<ObjectWrapper> parents) throws ClassCastException
    {
        return (T) parents.get(parents.size() - 1);
    }
}
