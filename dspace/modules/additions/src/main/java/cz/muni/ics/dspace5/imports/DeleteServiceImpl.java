/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.imports;

import cz.muni.ics.dspace5.core.CommandLineService;
import cz.muni.ics.dspace5.core.DeleteService;
import cz.muni.ics.dspace5.core.HandleService;
import cz.muni.ics.dspace5.impl.InputArguments;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.Logger;
import org.dspace.authorize.AuthorizeException;
import org.dspace.content.Bundle;
import org.dspace.content.Collection;
import org.dspace.content.Community;
import org.dspace.content.DSpaceObject;
import org.dspace.content.Item;
import org.dspace.content.ItemIterator;
import org.dspace.core.Constants;
import org.dspace.core.Context;
import org.dspace.handle.HandleManager;
import org.dspace.services.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Component(value = "deleteService")
public class DeleteServiceImpl implements DeleteService
{

    private static final Logger logger = Logger.getLogger(DeleteServiceImpl.class);
    private Context context = null;

    @Autowired
    private CommandLineService commandLineService;
    @Autowired
    private InputArguments inputArguments;
    @Autowired
    private HandleService handleService;
    @Autowired
    private ConfigurationService configurationService;

    private final List<String> handles = new ArrayList<>();

    @Override
    public void execute(String[] args)
    {
        boolean error = false;

        try
        {
            commandLineService.parseInput(args, CommandLineService.Mode.DELETE);
        }
        catch (ParseException pe)
        {
            error = true;
        }

        if (!error)
        {
            try
            {
                context = new Context();
            }
            catch (SQLException ex)
            {
                logger.fatal(ex, ex.getCause());
            }

            if (context != null)
            {
                logger.debug("Context created.");
                context.turnOffAuthorisationSystem();
                logger.debug("Disabled AuthorisationSystem.");

                String handle = null;

                if (inputArguments.getValue("mode").equals("path"))
                {
                    //TODO
                    Path deletePath = Paths.get(configurationService
                            .getProperty("meditor.rootbase"))
                            .resolve(inputArguments.getValue("value"));
                    
                    logger.info("Delete by path was selected [" + deletePath.toString() + "].");

                    handle = handleService.getHandleForPath(deletePath,context);
                }
                else
                {
                    handle = inputArguments.getValue("value");
                    logger.info("Delete by handle was selected [" + handle + "]");
                }

                DSpaceObject object = null;

                try
                {
                    object = HandleManager.resolveToObject(context, handle);
                }
                catch (SQLException ex)
                {
                    logger.error(ex, ex.getCause());
                }

                if (object != null)
                {
                    logger.debug("Object was found.");
                    handles.clear();

                    switch (object.getType())
                    {
                        case Constants.COMMUNITY:
                        {
                            logger.debug("Matched object is community.");
                            deleteCommunity((Community) object);
                        }
                        break;
                        case Constants.COLLECTION:
                        {
                            logger.debug("Matched object is collection.");
                            deleteCollection(null, (Collection) object);
                        }
                        break;
                        case Constants.ITEM:
                        {
                            logger.debug("Matched object is item.");
                            deleteItem(null, (Item) object);
                        }
                        break;
                    }
                }

                context.restoreAuthSystemState();
                logger.debug("Restored AuthorisationSystem");

                try
                {
                    executeBatchDelete(context.getDBConnection());
                    context.complete();
                    logger.info("Context closed.");
                }
                catch (SQLException ex)
                {
                    logger.fatal(ex, ex.getCause());
                }
            }
        }
    }

    private void deleteCommunity(Community community)
    {
        Community[] subcomms = null;

        try
        {
            subcomms = community.getSubcommunities();
        }
        catch (SQLException ex)
        {
            logger.error(ex, ex.getCause());
        }

        if (subcomms != null && subcomms.length > 0)
        {
            logger.info(subcomms.length + " subcommunities found. Attempting to delete them.");
            for (Community comm : subcomms)
            {
                deleteCommunity(comm);
            }
        }

        Collection[] colls = null;

        try
        {
            colls = community.getCollections();
        }
        catch (SQLException ex)
        {
            logger.error(ex, ex.getCause());
        }

        if (colls != null && colls.length > 0)
        {
            logger.info(colls.length + " collections found. Attempting to delete them.");
            for (Collection c : colls)
            {
                deleteCollection(community, c);
            }
        }

        //at this point we travelled thru whole tree so there are no ancestors
        logger.info("Deleting community [" + community.getName() + " with handle [" + community.getHandle() + "].");

        try
        {
            String handle = community.getHandle();
            community.delete();
            logger.info("Delete successful.");
            handles.add(handle);
        }
        catch (AuthorizeException | SQLException | IOException ex)
        {
            logger.error(ex, ex.getCause());
        }
    }

    private void deleteCollection(Community community, Collection col)
    {
        ItemIterator ii = null;

        try
        {
            ii = col.getAllItems();
        }
        catch (SQLException ex)
        {
            logger.debug(ex, ex.getCause());
        }

        if (ii != null)
        {
            try
            {
                while (ii.hasNext())
                {
                    deleteItem(col, ii.next());
                }
            }
            catch (SQLException ex)
            {
                logger.error(ex, ex.getCause());
            }
        }

        logger.info("Deleting collection [" + col.getName() + "] with handle [" + col.getHandle() + "]");

        try
        {
            String handle = col.getHandle();
            if (community == null)
            {
                Community comm = (Community) col.getParentObject();
                comm.removeCollection(col);
            }
            else
            {
                community.removeCollection(col);
            }
            handles.add(handle);
        }
        catch (SQLException | AuthorizeException | IOException ex)
        {
            logger.error(ex, ex.getCause());
        }

    }

    private void deleteItem(Collection collection, Item item)
    {
        handles.add(item.getHandle());
        logger.info("Deleting item [" + item.getHandle() + "] with name '" + item.getName() + "'.");

        Bundle[] bundles = null;

        try
        {
            bundles = item.getBundles();
        }
        catch (SQLException ex)
        {
            logger.debug(ex, ex.getCause());
        }

        if (bundles != null && bundles.length > 0)
        {
            for (Bundle b : bundles)
            {
                try
                {
                    item.removeBundle(b);
                }
                catch (SQLException | AuthorizeException | IOException ex)
                {
                    logger.debug(ex, ex.getCause());
                }
            }
        }

        if (collection == null)
        {
            try
            {
                Collection coll = (Collection) item.getOwningCollection();
                coll.removeItem(item);
            }
            catch (SQLException | AuthorizeException | IOException ex)
            {
                logger.error(ex, ex.getCause());
            }
        }
        else
        {
            try
            {
                collection.removeItem(item);
            }
            catch (SQLException | AuthorizeException | IOException ex)
            {
                logger.error(ex, ex.getCause());
            }
        }
    }

    /**
     * Method executes deletes from {@code handle} table. Method does not use
     * try/w/resources statement because we <b>cannot close</b> connection,
     * because if its closed later the work with {@code Context} class may fail
     * with {@code SQLException}.
     *
     * @param connection connection to database
     *
     * @throws SQLException if any error occurs.
     */
    private void executeBatchDelete(Connection connection) throws SQLException
    {
        final String sql = "DELETE FROM handle WHERE handle = ?";

        logger.info("Batch delete is being prepared.");

        PreparedStatement ps = connection.prepareStatement(sql);

        for (String s : handles)
        {
            ps.setString(1, s);
            ps.addBatch();
        }

        ps.executeBatch();

        connection.commit();
        
        logger.info("Batch delete executed, if no error has has appeared.");
    }
}
