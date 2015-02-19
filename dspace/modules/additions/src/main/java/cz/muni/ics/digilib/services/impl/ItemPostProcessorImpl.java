/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.digilib.services.impl;

import cz.muni.ics.dspace5.core.ObjectWrapper;
import cz.muni.ics.dspace5.core.post.ItemPostProcessor;
import cz.muni.ics.dspace5.impl.MetadataRow;
import java.util.List;
import org.apache.log4j.Logger;
import org.dspace.content.Item;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class ItemPostProcessorImpl implements ItemPostProcessor
{
    private static final Logger logger = Logger.getLogger(ItemPostProcessorImpl.class);
    
    @Override
    public List<MetadataRow> processMetadata(ObjectWrapper objectWrapper) throws IllegalArgumentException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void processItem(ObjectWrapper objectWrapper, Item item) throws IllegalArgumentException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
