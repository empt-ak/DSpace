/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5;

import cz.muni.ics.dspace5.api.DSpaceExecutor;
import cz.muni.ics.dspace5.impl.DSpaceWrapper;

/**
 *
 * @author krejvl
 */
public class Init
{
    public static void main(String[] args)
    {
        DSpaceWrapper.getInstance().getBean("dspaceExecutor", DSpaceExecutor.class).execute("init", args);
    }
}


