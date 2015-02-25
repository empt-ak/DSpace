/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.imports;

import cz.muni.ics.dspace5.core.CommandLineService;
import cz.muni.ics.dspace5.core.HandleService;
import cz.muni.ics.dspace5.core.ImportService;
import cz.muni.ics.dspace5.core.ObjectWrapper;
import cz.muni.ics.dspace5.impl.DSpaceTools;
import cz.muni.ics.dspace5.impl.io.FolderProvider;
import cz.muni.ics.dspace5.impl.InputArguments;
import cz.muni.ics.dspace5.impl.ObjectWrapperFactory;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.Logger;
import org.dspace.core.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Component(value = "importService")
public class ImportServiceImpl implements ImportService
{

    private static final Logger logger = Logger.getLogger(ImportServiceImpl.class);
    private Context context;
    
    //awires
    @Autowired
    private ObjectWrapperFactory objectWrapperFactory;
    @Autowired
    private CommandLineService commandLineService;    
    @Autowired
    private ImportCommunity importCommunity;
    @Autowired
    private ImportCollection importCollection;
    @Autowired
    private ImportItem importItem;
    @Autowired
    private InputArguments inputArguments;
    @Autowired
    private DSpaceTools dspaceTools;
    @Autowired
    private FolderProvider fileProvider;
    @Autowired
    private HandleService handleService;

    @Override
    public void execute(String[] args)
    {
        boolean error = false;

        try
        {
            commandLineService.parseInput(args, CommandLineService.Mode.IMPORT);
        }
        catch (ParseException pe)
        {
            error = true;
        }

        if (!error)
        {
            // this will be always null until i find out
            // how to call synchronized method in spring
            if (context == null)
            {
                try
                {
                    context = new Context();
                }
                catch (SQLException ex)
                {
                    logger.info(ex, ex.getCause());
                }
            }
            context.turnOffAuthorisationSystem();

            int level = dspaceTools.getPathLevel(inputArguments.getTypedValue("path", Path.class));

            switch (level)
            {
                case 0:
                {
                    ObjectWrapper ow = objectWrapperFactory.createObjectWrapper();
                    ow.setPath(inputArguments.getTypedValue("path", Path.class));
                    ow.setHandle(handleService.getHandleForPath(inputArguments.getTypedValue("path", Path.class), context));

                    resolveChildren(ow, level + 1);

                    importCommunity.importToDspace(ow, true, context);
                }
                break;
                case 1:
                {
                    // import collection and handle volume
                }
                break;
                case 2:
                {
                    //import item
                }
                break;
                case 3:
                {
                    //import item
                }
                break;
                default:
                    throw new IllegalArgumentException("Given path is not valid. Level should be [0/1/2] but was [" + level + "]");
            }

            context.restoreAuthSystemState();
            try
            {
                context.commit();
                context.complete();
            }
            catch (SQLException ex)
            {
                logger.error(ex, ex.getCause());
            }
        }
    }

    /**
     * Depth first search for recreating tree structure of root import object.
     *
     * @param objectWrapper target object of which children (in directory tree
     *                      structure) we would like to retrieve
     */
    private void resolveChildren(ObjectWrapper objectWrapper, int level)
    {
        if (inputArguments.getValue("mode").equals("update"))
        {
            if (level == 1 || level == 0)
            {
                level--;
                List<ObjectWrapper> issues = new ArrayList<>();
                Set<ObjectWrapper> volumes = new TreeSet<>();

                List<Path> paths = fileProvider.getFoldersFromPath(objectWrapper.getPath());

                for (Path p : paths)
                {
//                    ObjectWrapper ow = new ObjectWrapperImpl(p, handleService.getHandleForPath(p), null, null);
//                    ObjectWrapper volume = new ObjectWrapperImpl(p, handleService.getVolumeHandle(p), null, null);
                    ObjectWrapper ow = objectWrapperFactory.createObjectWrapper();
                    ow.setPath(p);
                    ow.setHandle(handleService.getHandleForPath(p,context));
                    
                    ObjectWrapper volume = objectWrapperFactory.createObjectWrapper();
                    volume.setPath(p);
                    volume.setHandle(handleService.getVolumeHandle(p,context));

                    resolveChildren(ow, level - 1);

                    volumes.add(volume);
                    issues.add(ow);
                }
                
                for(ObjectWrapper volume : volumes)
                {
                    List<ObjectWrapper> toAddIssues = new ArrayList<>();
                    
                    String volumeNumber = volume.getPath().getFileName()
                            .toString().split("-")[0];
                    
                    for(ObjectWrapper issue : issues)
                    {
                        if(issue.getPath().getFileName().toString().startsWith(volumeNumber))
                        {
                            toAddIssues.add(issue);
                        }
                    }
                    
                    volume.setChildren(toAddIssues);
                }

                objectWrapper.setChildren(new ArrayList<>(volumes));
            }
            else
            {
                List<ObjectWrapper> children = new ArrayList<>();
                List<Path> paths = fileProvider.getFoldersFromPath(objectWrapper.getPath());

                for (Path p : paths)
                {
                    ObjectWrapper ow = objectWrapperFactory.createObjectWrapper();
                    ow.setPath(p);
                    ow.setHandle(handleService.getHandleForPath(p,context));

                    resolveChildren(ow, level);
                    children.add(ow);
                }
                objectWrapper.setChildren(children);
            }
        }
    }
}
