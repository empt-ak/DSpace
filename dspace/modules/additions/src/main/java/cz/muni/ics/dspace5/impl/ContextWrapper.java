/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.impl;

import org.dspace.core.Context;
import org.springframework.stereotype.Component;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Component
public class ContextWrapper
{
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
}
