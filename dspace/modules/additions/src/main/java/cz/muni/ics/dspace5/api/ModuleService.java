/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.api;

import cz.muni.ics.dspace5.api.processors.CollectionProcessor;
import cz.muni.ics.dspace5.api.processors.CommunityProcessor;
import cz.muni.ics.dspace5.api.processors.ItemProcessor;
import java.io.FileNotFoundException;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface ModuleService
{
    ObjectWrapperResolver getObjectWrapperResolver() throws FileNotFoundException;
    CommunityProcessor getCommunityProcessor();
    CollectionProcessor getCollectionProcessor();
    ItemProcessor getItemProcessor();
}
