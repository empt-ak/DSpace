/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5;

import cz.muni.ics.dspace5.core.DeleteService;
import cz.muni.ics.dspace5.impl.DSpaceWrapper;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class Delete
{
    public static void main(String[] args)
    {
        DSpaceWrapper.getInstance().getBean("deleteService", DeleteService.class).execute(args);
    }
}
