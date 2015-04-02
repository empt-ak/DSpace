/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.digilib.services.impl.movingwall;

import cz.muni.ics.dspace5.movingwall.MWLocker;
import cz.muni.ics.dspace5.movingwall.MWLockerProvider;
import org.dspace.content.Collection;
import org.dspace.content.Community;
import org.dspace.content.Item;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public abstract class MWLockerProviderImpl implements MWLockerProvider
{
    public abstract MWLocker getItemLocker();
    public abstract MWLocker getCollectionLocker();
    public abstract MWLocker getCommunityLocker();
    
    @Override
    public MWLocker getLocker(Class clasz) throws IllegalArgumentException
    {
        if(clasz.equals(Collection.class))
        {
            return getCollectionLocker();
        }
        else if(clasz.equals(Community.class))
        {
            return getCommunityLocker();
        }
        else if (clasz.equals(Item.class))
        {
            return getItemLocker();
        }
        else
        {
            throw new IllegalArgumentException("Given class ["+clasz+" is not supported.");
        }
    }    
}
