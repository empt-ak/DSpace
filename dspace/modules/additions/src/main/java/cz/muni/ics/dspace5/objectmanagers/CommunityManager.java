/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.objectmanagers;

import cz.muni.ics.dspace5.api.module.ObjectWrapper;
import cz.muni.ics.dspace5.api.module.ObjectWrapper.LEVEL;
import cz.muni.ics.dspace5.exceptions.MovingWallException;
import java.sql.SQLException;
import java.util.List;
import org.apache.log4j.Logger;
import org.dspace.authorize.AuthorizeException;
import org.dspace.content.Collection;
import org.dspace.content.Community;
import org.dspace.content.Metadatum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Component(value = "communityManager")
public class CommunityManager extends AbstractDSpaceManager<Community>
{

    private static final Logger logger = Logger.getLogger(CommunityManager.class);
    
    @Autowired
    @Qualifier(value = "collectionManager")
    private DSpaceObjectManager<Collection> collectionManager; 
    
    @Override
    public Community resolveObjectInDSpace(ObjectWrapper objectWrapper, List<ObjectWrapper> parents)
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
            moduleManager.getModule(objectWrapper).getCommunityProcessor().setup(objectWrapper);

            List<Metadatum> metadata = moduleManager.getModule(objectWrapper).getCommunityProcessor().processMetadata(parents);

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
            
            moduleManager.getModule(objectWrapper).getCommunityProcessor().processCommunity(workingCommunity, parents);  
            
            if(inputDataMap.containsKey("mwmethod") && !inputDataMap.getValue("mwmethod").equals("off"))
            {
                try
                {
                    moduleManager.getModule(objectWrapper).getCommunityProcessor().movingWall(workingCommunity);
                }
                catch(MovingWallException me)
                {
                    logger.error(me);
                }
            }
            
            
            moduleManager.getModule(objectWrapper).getCommunityProcessor().clear();
            
            super.saveAndCommit(workingCommunity);
            
            if(objectWrapper.getChildren() != null && !objectWrapper.getChildren().isEmpty())
            {
                if(objectWrapper.getChildren().get(0).getLevel().equals(LEVEL.COL))
                {
                    for(ObjectWrapper issue : objectWrapper.getChildren())
                    {
                        objectWrapper.setObject(workingCommunity);
                        
                        collectionManager.resolveObjectInDSpace(issue, dSpaceTools.createParentBranch(objectWrapper, parents));
                    }                    
                }
                else
                {
                    for(ObjectWrapper subComm : objectWrapper.getChildren())
                    {
                        objectWrapper.setObject(workingCommunity);
                        
                        resolveObjectInDSpace(subComm, dSpaceTools.createParentBranch(objectWrapper, parents));
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
            super.safeFailLog(ex);
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
                super.safeFailLog(ex);
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
                    super.safeFailLog(ex);
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
            super.safeFailLog(exception);
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
                super.safeFailLog(ex);
            }            
        }

        return result;
    }
}
