/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.objectmanagers;

import cz.muni.ics.dspace5.api.module.ObjectWrapper;
import cz.muni.ics.dspace5.exceptions.MovingWallException;
import java.sql.SQLException;
import java.util.List;
import org.apache.log4j.Logger;
import org.dspace.authorize.AuthorizeException;
import org.dspace.content.Collection;
import org.dspace.content.Community;
import org.dspace.content.Item;
import org.dspace.content.Metadatum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Component(value = "collectionManager")
public class CollectionManager extends AbstractDSpaceManager<Collection>
{
    private static final Logger logger = Logger.getLogger(CollectionManager.class);
    
    @Autowired
    @Qualifier(value = "itemManager")
    private DSpaceObjectManager<Item> itemManager;

    @Override
    public Collection resolveObjectInDSpace(ObjectWrapper objectWrapper, List<ObjectWrapper> parents)
    {
        Collection workingCollection = findOrCreateCollection(parents.get(parents.size() - 1), objectWrapper);
        
        if(workingCollection != null)
        {
            moduleManager.getModule(objectWrapper).getCollectionProcessor().setup(objectWrapper);
            
            List<Metadatum> metadata = moduleManager.getModule(objectWrapper).getCollectionProcessor().processMetadata(parents);
            
            for(Metadatum m : metadata)
            {
                workingCollection.clearMetadata(m.schema, m.element, m.qualifier, ANY);
            }

            for(Metadatum m : metadata)
            {
                logger.info(m.getField()+":- "+m.value);
                workingCollection.addMetadata(m.schema, m.element, m.qualifier, m.language, m.value);
            }
            
            moduleManager.getModule(objectWrapper).getCollectionProcessor().processCollection(workingCollection, parents); 
            
            if(inputDataMap.containsKey("mwmethod") && !inputDataMap.getValue("mwmethod").equals("off"))
            {
                try
                {
                    moduleManager.getModule(objectWrapper).getCollectionProcessor().movingWall(workingCollection);
                }
                catch(MovingWallException mw)
                {
                    logger.error(mw);
                }  
            }
            
            moduleManager.getModule(objectWrapper).getCollectionProcessor().clear();
            
            super.saveAndCommit(workingCollection);
            
            if(objectWrapper.getChildren() != null && !objectWrapper.getChildren().isEmpty())
            {
                for(ObjectWrapper article : objectWrapper.getChildren())
                {
                    objectWrapper.setObject(workingCollection);
                    
                    itemManager.resolveObjectInDSpace(article, dSpaceTools.createParentBranch(objectWrapper, parents));
                }
            }
            
            return workingCollection;
        }
        else
        {
            throw new RuntimeException("ERROR while creating or finding collection.");
        }
    }
    
    private Collection findOrCreateCollection(ObjectWrapper parent, ObjectWrapper child)
    {
        Community parentCommunity = null;
        Collection collection = null;
        
        try
        {
            parentCommunity = parent.getObject();
        }
        catch(ClassCastException ex)
        {
            throw new IllegalArgumentException("Parent is not a community",ex.getCause());
        }
        
        if(parentCommunity != null)
        {
            Collection[] colls = null;
            try
            {
                colls = parentCommunity.getCollections();
            }
            catch(SQLException ex)
            {
                logger.error(ex,ex.getCause());
            }
            
            if(colls != null && colls.length > 0)
            {
                for(Collection col : colls)
                {
                    if(col.getHandle().equals(child.getHandle()))
                    {
                        collection = col;
                        break;
                    }
                }
            }
            
            if(collection == null)
            {
                try
                {
                    collection = parentCommunity.createCollection(child.getHandle());
                }
                catch(SQLException | AuthorizeException ex)
                {
                    logger.error(ex);
                }                
            }
        }
        
        
        return collection;
    }
}
