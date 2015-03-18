/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.imports;

import cz.muni.ics.dspace5.core.ObjectWrapper;
import cz.muni.ics.dspace5.core.post.ItemPostProcessor;
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
import org.dspace.core.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Component
public class ImportItem
{
    private static final Logger logger = Logger.getLogger(ImportItem.class);
    private static final String ANY = "*";
    
    @Autowired
    private ItemPostProcessor itemPostProcessor;
    @Autowired
    private ImportTools importTools;
    
    private Context context;
     
    public Item importToDspace(ObjectWrapper objectWrapper, List<ObjectWrapper> parents, final Context context)
    {
        if(this.context == null)
        {
            this.context = context;
        }
        
        Item workingItem = findOrCreateItem(parents.get(parents.size() - 1 ), objectWrapper);
        
        if(workingItem != null)
        {
            logger.info("Processing metadata for handle:"+objectWrapper.getHandle()+" @path:- "+objectWrapper.getPath());
            List<Metadatum> metadata = itemPostProcessor.processMetadata(objectWrapper);
            
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
            
            itemPostProcessor.processItem(objectWrapper, workingItem, context);
            
            importTools.saveAndCommit(workingItem, context);
            
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
                    WorkspaceItem wi = WorkspaceItem.create(context, parentCollection, false);
                    logger.info("Installing " + target.getHandle());
                    result = InstallItem.installItem(context, wi, target.getHandle());
                }
                catch(SQLException | AuthorizeException | IOException ex)
                {
                    logger.error(ex,ex.getCause());
                }
                
                return result;
            }
            else
            {
                return result;
            }
        }
        else
        {
            return result;
        }        
    }
}
