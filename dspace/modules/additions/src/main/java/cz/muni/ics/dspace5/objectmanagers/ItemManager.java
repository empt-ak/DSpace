/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.objectmanagers;

import cz.muni.ics.dspace5.api.module.ObjectWrapper;
import cz.muni.ics.dspace5.exceptions.MovingWallException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import org.apache.log4j.Logger;
import org.dspace.authorize.AuthorizeException;
import org.dspace.content.Collection;
import org.dspace.content.InstallItem;
import org.dspace.content.Item;
import org.dspace.content.ItemIterator;
import org.dspace.content.Metadatum;
import org.dspace.content.WorkspaceItem;
import org.springframework.stereotype.Component;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Component(value = "itemManager")
public class ItemManager extends AbstractDSpaceManager<Item>
{
    private static final Logger logger = Logger.getLogger(ItemManager.class);

    @Override
    public Item resolveObjectInDSpace(ObjectWrapper objectWrapper, List<ObjectWrapper> parents)
    {
        Item workingItem = findOrCreateItem(parents.get(parents.size() - 1 ), objectWrapper);
        
        if(workingItem != null)
        {
            moduleManager.getModule(objectWrapper).getItemProcessor().setup(objectWrapper);
            
            logger.info("Processing metadata for handle:"+objectWrapper.getHandle()+" @path:- "+objectWrapper.getPath());
            List<Metadatum> metadata = moduleManager.getModule(objectWrapper).getItemProcessor().processMetadata(parents);

            logger.info("Clearing metadata.");
            for(Metadatum m : metadata)
            {
                workingItem.clearMetadata(m.schema, m.element, m.qualifier, ANY);
            }

            for(Metadatum m : metadata)
            {
                logger.info("Setting metadata: "+m.getField()+":- "+m.value);
                workingItem.addMetadata(m.schema, m.element, m.qualifier, m.language, m.value);
            }            
            
            moduleManager.getModule(objectWrapper).getItemProcessor().processItem(workingItem, parents);
            
            try
            {
                moduleManager.getModule(objectWrapper).getItemProcessor().movingWall(workingItem);
            }
            catch(MovingWallException me)
            {
                logger.error(me);
            }
            
            moduleManager.getModule(objectWrapper).getItemProcessor().clear();
                        
            super.saveAndCommit(workingItem);
            
            // so there are no unneeded references to this list
            // if we are working with big tree
            parents.clear();
            
            return workingItem;
        }
        else
        {
            throw new RuntimeException("huehue");
        }
    }
    
    private Item findOrCreateItem(ObjectWrapper parent, ObjectWrapper target)
    {
        logger.debug("Finding items for collection: "+parent.getHandle());
        Item result = null;
        Collection parentCollection = parent.getObject();
        
        if(parentCollection != null)
        {
            try
            {
                ItemIterator ii = parentCollection.getAllItems();
                while(ii.hasNext())
                {
                    Item next = ii.next();
                    logger.debug("Comparing "+next.getHandle()+" against "+target.getHandle());
                    if(next.getHandle().equals(target.getHandle()))
                    {
                        result = next;
                        logger.debug("Match found");
                        break;
                    }
                }
                ii.close();
            }
            catch(SQLException ex)
            {
                logger.error(ex,ex.getCause());
            }
            
            if(result == null)
            {
                logger.debug("No entry found yet. "+target.getHandle()+" will be created.");
                try
                {
                    WorkspaceItem wi = WorkspaceItem.create(contextWrapper.getContext(), parentCollection, false);
                    logger.info("Installing " + target.getHandle());
                    result = InstallItem.installItem(contextWrapper.getContext(), wi, target.getHandle());
                }
                catch(SQLException | AuthorizeException | IOException ex)
                {
                    logger.error(ex,ex.getCause());
                }
                
                return result;
            }            
        }
        
        return result;
    }
}
