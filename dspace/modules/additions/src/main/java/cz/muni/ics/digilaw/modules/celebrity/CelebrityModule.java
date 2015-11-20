/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.digilaw.modules.celebrity;

import cz.muni.ics.dspace5.api.module.CollectionProcessor;
import cz.muni.ics.dspace5.api.module.CommunityProcessor;
import cz.muni.ics.dspace5.api.module.ItemProcessor;
import cz.muni.ics.dspace5.api.module.ModuleService;
import cz.muni.ics.dspace5.api.module.ObjectWrapperResolver;
import java.io.FileNotFoundException;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public abstract class CelebrityModule implements ModuleService
{
    @Override
    public abstract ObjectWrapperResolver getObjectWrapperResolver() throws FileNotFoundException;

    @Override
    public abstract CommunityProcessor getCommunityProcessor();

    @Override
    public abstract CollectionProcessor getCollectionProcessor();

    @Override
    public abstract ItemProcessor getItemProcessor();
}
