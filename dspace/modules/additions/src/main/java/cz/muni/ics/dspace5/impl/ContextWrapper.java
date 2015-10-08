/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.impl;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import javax.annotation.PreDestroy;
import org.apache.log4j.Logger;
import org.dspace.core.Context;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Component
public class ContextWrapper implements ApplicationListener<ContextRefreshedEvent>
{

    private static final Logger logger = Logger.getLogger(ContextWrapper.class);
    private Context context;
    private static final Path configFilename = Paths.get("dspace.cfg");

    private DSpaceTools dSpaceTools;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event)
    {
        if (dSpaceTools == null)
        {
            // dirty solution, awire does not work when
            // bean implements ApplicationListener.
            // because of circular dependency
            dSpaceTools = event.getApplicationContext().getBean(DSpaceTools.class);
        }
        if (context == null)
        {
            DSpaceWrapper.getInstance();
            try
            {
                /*
                 hack for dspace cfg not found. the default fallback when property is not found
                 or passed, is to load config file related to position of ConfigurationManager class
                 however the config file neither exist in ConfigurationManager class location
                 nor is passed as classpath argument (like when commnad ./dpsace dsrun is executed)
                 */
                //TODO
                System.setProperty("dspace.configuration", dSpaceTools.getLocationPath().resolve(configFilename).toString());

                context = new Context();
                logger.debug("Context created.");
                context.turnOffAuthorisationSystem();
                logger.debug("Authorisation system disabled.");
            }
            catch (SQLException ssqlex)
            {
                logger.fatal(ssqlex);
            }
            logger.info("ContextWrapper bean has been initialized.");
        }
        else
        {
            logger.debug("ContextWrapper bean has already set Context.");
        }
    }

    @PreDestroy
    private void destroy() throws SQLException
    {
        // if spring fails to start, then context is not created
        // and calling method on null throws an exception which is 
        // later logged @WARN level in log.
        if(this.context != null)
        {
            logger.debug("Shutting down ContextWrapper bean.");
            this.context.complete();
            logger.debug("Context has been closed.");
            this.context.restoreAuthSystemState();
            logger.debug("Authorisation system restored.");
            this.context = null;
            logger.info("ContextWrapper bean has been shut down.");
        }        
    }

    public Context getContext() throws IllegalStateException
    {
        if (context == null)
        {
            throw new IllegalStateException("Context is not loaded. It should be autoloaded on when Spring Context is done with booting.");
        }
        return context;
    }

    public void setEperson(String epersonEmail)
    {
        getContext().setCurrentUser(dSpaceTools.findEPerson(epersonEmail));
    }
}
