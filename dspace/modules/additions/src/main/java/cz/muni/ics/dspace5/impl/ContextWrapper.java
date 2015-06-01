/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.impl;

import java.sql.SQLException;
import javax.annotation.PreDestroy;
import org.apache.log4j.Logger;
import org.dspace.core.Context;
import org.springframework.stereotype.Component;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Component
public class ContextWrapper
{
    private static final Logger logger = Logger.getLogger(ContextWrapper.class);
    private Context context;

    public Context getContext() throws IllegalStateException
    {
        if(context == null)
        {
            throw new IllegalStateException("Context is not set yet. It has to be set using #setContext method.");
        }
        return context;
    }

    public void setContext(Context context)
    {
        this.context = context;
    }
    
    @PreDestroy
    private void destroy() throws SQLException
    {
        logger.info("Shutting down ContextWrapper bean.");
        this.context.complete();
        this.context.restoreAuthSystemState();
        this.context = null;
    }
}
