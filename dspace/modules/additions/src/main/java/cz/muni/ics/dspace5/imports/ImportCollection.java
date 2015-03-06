/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.imports;

import cz.muni.ics.dspace5.core.ObjectWrapper;
import cz.muni.ics.dspace5.core.post.CollectionPostProcessor;
import cz.muni.ics.dspace5.impl.InputArguments;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.dspace.authorize.AuthorizeException;
import org.dspace.content.Collection;
import org.dspace.content.Community;
import org.dspace.content.Metadatum;
import org.dspace.core.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Component
public class ImportCollection
{
    private static final Logger logger = Logger.getLogger(ImportCollection.class);
    private static final String ANY = "*";
    
    @Autowired
    private InputArguments inputArguments;
    @Autowired
    private ImportItem importItem;
    @Autowired
    private CollectionPostProcessor collectionPostProcessor;
    @Autowired
    private ImportTools importTools;
    
    private Context context;
    
    public Collection importToDspace(ObjectWrapper objectWrapper, List<ObjectWrapper> parents, final Context context)
    {
        if(this.context == null)
        {
            this.context = context;
        }
        
        Collection workingCollection = findOrCreateCollection(parents.get(parents.size() - 1), objectWrapper);
        
        if(workingCollection != null)
        {
            List<Metadatum> metadata = collectionPostProcessor.processMetadata(objectWrapper, parents);
            
            for(Metadatum m : metadata)
            {
                workingCollection.clearMetadata(m.schema, m.element, m.qualifier, ANY);
            }
            
            for(Metadatum m : metadata)
            {
                logger.info(m.getField()+":- "+m.value);
                workingCollection.addMetadata(m.schema, m.element, m.qualifier, m.language, m.value);
            }
            
            collectionPostProcessor.processCollection(objectWrapper, workingCollection);
            
            importTools.saveAndCommit(workingCollection, context);
            
            if(objectWrapper.getChildren() != null && !objectWrapper.getChildren().isEmpty())
            {
                for(ObjectWrapper article : objectWrapper.getChildren())
                {
                    List<ObjectWrapper> newParents = new ArrayList<>();
                    newParents.addAll(parents);
                    objectWrapper.setObject(workingCollection);
                    newParents.add(objectWrapper);
                    
                    importItem.importToDspace(article, newParents, context);
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
