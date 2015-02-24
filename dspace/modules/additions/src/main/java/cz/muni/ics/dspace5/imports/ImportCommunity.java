/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.imports;

import cz.muni.ics.dspace5.core.ObjectWrapper;
import cz.muni.ics.dspace5.core.post.CommunityPostProcessor;
import cz.muni.ics.dspace5.impl.InputArguments;
import cz.muni.ics.dspace5.impl.MetadataRow;
import java.sql.SQLException;
import java.util.List;
import org.apache.log4j.Logger;
import org.dspace.authorize.AuthorizeException;
import org.dspace.content.Community;
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
     * @param isTopComm     flag specifying whether given object is topcommunity
     *                      or not
     * @param context       context responsible for Database operations
     *
     * @return created Community stored by this method
     */
    public Community importToDspace(ObjectWrapper objectWrapper, boolean isTopComm, Context context)
    {
        this.context = context;

        Community comm = findCommunity(objectWrapper, isTopComm);
        if (comm == null)
        {
            logger.debug("Community with handle [" + objectWrapper.getHandle() + "] not found. Community will be created");
            try
            {
                comm = Community.create(null, context, objectWrapper.getHandle());
            }
            catch (AuthorizeException | SQLException ex)
            {
                safeFailLog(ex);
            }
        }

        if (comm != null)
        {
            List<MetadataRow> metadata = communityPostProcessor.processMetadata(objectWrapper, isTopComm);

            //values have to be cleared first because there may be multiple values
            // e.g. dc.title.alternative
            for (MetadataRow mr : metadata)
            {
                comm.clearMetadata(mr.getSchema(), mr.getElement(), mr.getQualifier(), ANY);
            }
            
            for (MetadataRow mr : metadata)
            {
                comm.addMetadata(mr.getSchema(), mr.getElement(), mr.getQualifier(), mr.getLanguage(), mr.getValue());
            }

            communityPostProcessor.processCommunity(objectWrapper, comm);

            saveAndCommit(comm);

            if (inputArguments.getValue("mode").equals("update"))
            {
                if(objectWrapper.getChildren() != null && !objectWrapper.getChildren().isEmpty())
                {
                    /*
                    @meditor: add some kind of resolver which will say what will be imported based on 
                    path level. e.g. /a/b is comunity a/b/c is subcomunity a/b/c/d sub too
                    but a/b/c/d/e will resolve to collection
                    */
                    if(objectWrapper.getChildren().get(0).isVolume())
                    {
                        for(ObjectWrapper child : objectWrapper.getChildren())
                        {
                            importToDspace(child, false, context);
                        }
                    }
                    else
                    {
                        for(ObjectWrapper child : objectWrapper.getChildren())
                        {
                            importCollection.importToDspace(child, context);
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
            for (Community topComm : communities)
            {
                if (topComm.getHandle().equals(objectWrapper.getHandle()))
                {
                    logger.debug("Community with handle [" + objectWrapper.getHandle() + "] found. Community will be updated");
                    result = topComm;
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
