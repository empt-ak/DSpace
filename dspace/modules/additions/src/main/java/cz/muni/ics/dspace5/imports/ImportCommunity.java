/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.imports;

import cz.muni.ics.dspace5.core.ObjectWrapper;
import cz.muni.ics.dspace5.core.ObjectWrapper.LEVEL;
import cz.muni.ics.dspace5.core.post.CommunityPostProcessor;
import cz.muni.ics.dspace5.impl.InputArguments;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.dspace.authorize.AuthorizeException;
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
public class ImportCommunity
{

    private static final Logger logger = Logger.getLogger(ImportCommunity.class);
    private static final String ANY = "*";

    @Autowired
    private CommunityPostProcessor communityPostProcessor;
    @Autowired
    private InputArguments inputArguments;
    @Autowired
    private ImportCollection importCollection;

    private Context context;

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
     * @param context       context responsible for Database operations
     *
     * @return created Community stored by this method
     */
    public Community importToDspace(ObjectWrapper objectWrapper, List<ObjectWrapper> parents, Context context)
    {
        this.context = context;
        
        boolean isTopComm = objectWrapper.getLevel().equals(LEVEL.COM);

        Community comm = findCommunity(objectWrapper, isTopComm);
        if (comm == null)
        {
            logger.debug("Community with handle [" + objectWrapper.getHandle() + "] not found. Community will be created");
            if(isTopComm)
            {
                try
                {
                    comm = Community.create(null, context, objectWrapper.getHandle());
                }
                catch (AuthorizeException | SQLException ex)
                {
                    safeFailLog(ex);
                }
            }
            else
            {
                //TODO this is a bit nasty workaround shouldn't be isVolume
                //but something more intelligent like OW should have stored
                //what kind of object it is mapped to
                
//                Community parent = findCommunity(parents.get(parents.size()-1), false);
//                
//                if(parent == null)
//                {
//                    throw new RuntimeException("Parent of child ["+objectWrapper.getHandle()+"] does not exist.");
//                }
//                else
//                {
//                    try
//                    {
//                        comm = parent.createSubcommunity(objectWrapper.getHandle());
//                    }
//                    catch (SQLException | AuthorizeException ex)
//                    {
//                        logger.fatal(ex,ex.getCause());
//                    }
//                }                
            }
            
        }

        if (comm != null)
        {
            List<Metadatum> metadata = communityPostProcessor.processMetadata(objectWrapper, parents);

            //values have to be cleared first because there may be multiple values
            // e.g. dc.title.alternative
            for (Metadatum m : metadata)
            {
                comm.clearMetadata(m.schema, m.element, m.qualifier, ANY);
            }
            
            for (Metadatum m : metadata)
            {
                logger.info(m.getField()+":- "+m.value);
                comm.addMetadata(m.schema, m.element, m.qualifier, m.language, m.value);
            }

            communityPostProcessor.processCommunity(objectWrapper, comm);

            saveAndCommit(comm);
            
            if(objectWrapper.getChildren() != null && !objectWrapper.getChildren().isEmpty())
            {
                if(isTopComm)
                {
                    for(ObjectWrapper volume : objectWrapper.getChildren())
                    {
                        List<ObjectWrapper> parentz = new ArrayList<>(1);
                        parentz.add(objectWrapper);
                        
                        importToDspace(volume, parentz, context);
                    }
                }
                else
                { 
                    // just to be sure, otherwise there is a error
                    // @meditor: moze to by inac kedze tam bude povoleno viacero
                    // vnorenych podkomunit.
                    if(objectWrapper.getChildren().get(0).getLevel().equals(LEVEL.COL))
                    {
                        for(ObjectWrapper issue : objectWrapper.getChildren())
                        {
                            List<ObjectWrapper> parentz = new ArrayList<>();
                            if(parents != null)
                            {
                                parentz.addAll(parents);
                            }
                            
                            parentz.add(objectWrapper);
                            
                            importCollection.importToDspace(issue, parentz, context);
                        }
                    }
                }
            }
            
            return comm;
        }
        else
        {
            throw new RuntimeException("FATAL ERROR: It was not possible to create community.");
        }
    }

    /**
     * Method finds related community to given {@code ObjectWrapper}. If none is
     * found (does not exist yet) then null is returned.
     *
     * @param objectWrapper wrapper holding handle to community to be found
     *
     * @return Community with given handle, null if there is none yet
     */
    private Community findCommunity(ObjectWrapper objectWrapper, boolean findInTop)
    {
        Community[] communities = new Community[0];
        Community result = null;
        try
        {
            if (findInTop)
            {
                communities = Community.findAllTop(context);
            }
            else
            {
                communities = Community.findAll(context);
                //result.getSubcommunities()
            }
        }
        catch (SQLException exception)
        {
            safeFailLog(exception);
        }

        if (communities != null && communities.length > 0)
        {
            for (Community comm : communities)
            {
                if (comm.getHandle().equals(objectWrapper.getHandle()))
                {
                    if(findInTop)
                    {
                        logger.debug("Community with handle [" + objectWrapper.getHandle() + "] found. Community will be updated");
                    }
                    else
                    {
                        logger.debug("Any parent community with handle [" + objectWrapper.getHandle() + "] found.");
                    }
                    
                    result = comm;
                    break;
                }
            }
        }

        return result;
    }

    /**
     * Method saves given community inside database, and if it's proper time
     * then executes commit. Proper time is when
     * {@link ImportConfig#getCommitAfterNumber() } equals 0 or the same value
     * equals {@link ImportConfig#getCurentCommitNumber() }. Otherwise {@link ImportConfig#incrementCommitNumber()
     * } is called to increase current counter.
     *
     * @param community to be stored
     */
    private void saveAndCommit(Community community)
    {
        try
        {
            community.update();
            context.commit();
        }
        catch (SQLException | AuthorizeException ex)
        {
            safeFailLog(ex);
        }
    }

    /**
     * Method logs occurred exception and closes application if {@link ImportConfig#failsOnError()
     * } returns true.
     *
     * @param ex to be logged.
     */
    private void safeFailLog(Exception ex)
    {
        logger.error(ex, ex.getCause());
        if (inputArguments.getTypedValue("failOnError"))
        {
            System.exit(1);
        }
    }
}
