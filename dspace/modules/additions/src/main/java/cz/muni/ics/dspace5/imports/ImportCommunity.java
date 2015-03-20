/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.imports;

import cz.muni.ics.dspace5.core.HandleService;
import cz.muni.ics.dspace5.core.ObjectWrapper;
import cz.muni.ics.dspace5.core.ObjectWrapper.LEVEL;
import cz.muni.ics.dspace5.core.post.CommunityPostProcessor;
import cz.muni.ics.dspace5.impl.ContextWrapper;
import cz.muni.ics.dspace5.impl.InputArguments;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.dspace.authorize.AuthorizeException;
import org.dspace.content.Community;
import org.dspace.content.Metadatum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Component
public class ImportCommunity
{

    private static final Logger logger = Logger.getLogger(ImportCommunity.class);
    private static final String ANY = "*";

    @Autowired
    private CommunityPostProcessor communityPostProcessor;
    @Autowired
    private ImportTools importTools;
    @Autowired
    private ImportCollection importCollection;
    @Autowired
    private ContextWrapper contextWrapper;
    @Autowired
    private HandleService handleService;

    /**
     * Method takes given objectWrapper (which has contain path to target object
     * and handle). Object itself and children are not required. At first step
     * community is found inside system (top or sub). If missing then its
     * created. Then we call {@link CommunityPostProcessor} which handles
     * operations done to community (such as providing metadata, or adding
     * thumbnail). After its done. Save and commit is done based on values
     * stored in {@link InputArguments}.
     *
     * @param objectWrapper target object wrapping required values which are
     *                      converted into community
     * @param parents
     *
     * @return created Community stored by this method
     */
    public Community importToDspace(ObjectWrapper objectWrapper, List<ObjectWrapper> parents)
    {
        logger.info("Commencing import of Community type.");
        logger.info(objectWrapper.getLevel()+" with handle@"+objectWrapper.getHandle()+" having following parents: "+parents);        
        
        boolean isTopComm = objectWrapper.getLevel().equals(LEVEL.COM);
        
        Community workingCommunity = null;
        
        logger.info("Given community is topComm? "+isTopComm);        
        if(isTopComm)
        {
            workingCommunity = findOrCreateTopCommunity(objectWrapper);
        }
        else
        {
            workingCommunity = findOrCreateCommunity(parents.get(parents.size()-1), objectWrapper);
        }
        
        if(workingCommunity == null)
        {
            throw new RuntimeException("Something went wrong as working community was not created.");
        }
        else
        {
            List<Metadatum> metadata = communityPostProcessor.processMetadata(objectWrapper, parents);
            
            //values have to be cleared first because there may be multiple values
            // e.g. dc.title.alternative
            for (Metadatum m : metadata)
            {
                workingCommunity.clearMetadata(m.schema, m.element, m.qualifier, ANY);
            }
            
            for (Metadatum m : metadata)
            {
                logger.info(m.getField()+":- "+m.value);
                workingCommunity.addMetadata(m.schema, m.element, m.qualifier, m.language, m.value);
            }
            
            communityPostProcessor.processCommunity(objectWrapper, workingCommunity);
            
            importTools.saveAndCommit(workingCommunity);
            
            if(objectWrapper.getChildren() != null && !objectWrapper.getChildren().isEmpty())
            {
                if(objectWrapper.getChildren().get(0).getLevel().equals(LEVEL.COL))
                {
                    for(ObjectWrapper issue : objectWrapper.getChildren())
                    {
                        List<ObjectWrapper> newParents = new ArrayList<>();
                        if(parents != null)
                        {
                            newParents.addAll(parents);
                        }
                        objectWrapper.setObject(workingCommunity);
                        newParents.add(objectWrapper);
                        
                        importCollection.importToDspace(issue, newParents);
                    }                    
                }
                else
                {
                    for(ObjectWrapper subComm : objectWrapper.getChildren())
                    {
                        List<ObjectWrapper> newParents = new ArrayList<>();
                        if(parents != null)
                        {
                            newParents.addAll(parents);
                        }
                        objectWrapper.setObject(workingCommunity);
                        newParents.add(objectWrapper);
                        
                        importToDspace(subComm, newParents);
                    } 
                }
            }
        }
        
        return workingCommunity;
    }
    
    /**
     * Calling method finds Parent community specified by {@code parent} and trying to find its child as subcommunity specified by {@code child}.
     * If child does not exist yet, then it is created and returned. Any failure results in {@link RuntimeException}.
     * @param parent parent wrapper
     * @param child child wrapper
     * @return community, existing or created, representing child wrapper
     */
    private Community findOrCreateCommunity(ObjectWrapper parent, ObjectWrapper child)
    {
        //@TODO speedup process by checking if parent has object stored int #getObject();
        //if so select to db is not required.
        Community[] communities = new Community[0];
        Community parentCommunity = null;
        try
        {
            communities = Community.findAll(contextWrapper.getContext());
            logger.debug(communities.length+" communities found via findAll");
        }
        catch(SQLException ex)
        {
            importTools.safeFailLog(ex);
        }
        
        if(communities != null && communities.length > 0)
        {
            for(Community comm : communities)
            {
                logger.debug("Comparing "+comm.getHandle()+ " against "+parent.getHandle() +"@level"+parent.getLevel());
                if(comm.getHandle().equals(parent.getHandle()))
                {
                    logger.debug("Parent found");
                    parentCommunity = comm;
                    break;
                }
            }
        }
        
        if(parentCommunity != null)
        {
            logger.debug("Resolving children for parent community "+parent.getHandle());
            Community[] children = null;
            
            Community result = null;
            
            try
            {
                children = parentCommunity.getSubcommunities();
                logger.debug(children.length + " children communities found.");
            }
            catch(SQLException ex)
            {
                importTools.safeFailLog(ex);
            }
            
            if(children != null && children.length > 0)
            {
                for(Community childComm : children)
                {
                    logger.debug("Matching subCommunity "+childComm.getHandle()+" against "+child.getHandle());
                    if(childComm.getHandle().equals(child.getHandle()))
                    {
                        logger.debug("Match found !");
                        result = childComm;
                        break;
                    }
                }
            }
            
            if(result == null)
            {
                logger.error("Given subcommunity does not exist yet. Attempting to create.");
                try
                {
                    result = parentCommunity.createSubcommunity(child.getHandle());    
                    logger.debug("Subcommunity "+child.getHandle()+" was created.");
                }
                catch(SQLException | AuthorizeException ex)
                {
                    logger.error("Error creating subcommunity "+child.getHandle()+" for parent "+parent.getHandle());
                    importTools.safeFailLog(ex);
                }
            }
            
            return result;            
        }
        else
        {
            logger.error("No parent community found.");
            throw new RuntimeException("Parent does not exist !");
        }
    }

    /**
     * Method finds related community to given {@code ObjectWrapper}. If none is
     * found (does not exist yet) then it is created.
     *
     * @param objectWrapper wrapper holding handle to community to be found
     *
     * @return Community with given handle, null if there is none yet
     */
    private Community findOrCreateTopCommunity(ObjectWrapper objectWrapper)
    {
        Community[] communities = new Community[0];
        Community result = null;
        try
        {
            communities = Community.findAllTop(contextWrapper.getContext());
            logger.debug(communities.length+" communities obtained via findAllTop");            
        }
        catch (SQLException exception)
        {
            importTools.safeFailLog(exception);
        }

        if (communities != null && communities.length > 0)
        {
            for (Community comm : communities)
            {
                logger.debug("Comparing "+comm.getHandle()+" against "+objectWrapper.getHandle());
                if (comm.getHandle().equals(objectWrapper.getHandle()))
                {
                    logger.debug("Community with handle [" + objectWrapper.getHandle() + "] found. Community will be updated");                   
                    
                    result = comm;
                    break;
                }
            }
        }
        
        if(result == null)
        {
            try
            {
                result = Community.create(null, contextWrapper.getContext(), objectWrapper.getHandle());
            }
            catch(SQLException | AuthorizeException ex)
            {
                importTools.safeFailLog(ex);
            }            
        }

        return result;
    }
}
