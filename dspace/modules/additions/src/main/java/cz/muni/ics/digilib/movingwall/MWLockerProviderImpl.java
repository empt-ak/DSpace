/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.digilib.movingwall;

import cz.muni.ics.dspace5.movingwall.MWLocker;
import cz.muni.ics.dspace5.movingwall.MWLockerProvider;
import java.util.Arrays;
import java.util.List;
import org.dspace.content.Bitstream;
import org.dspace.content.Collection;
import org.dspace.content.DSpaceObject;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public abstract class MWLockerProviderImpl implements MWLockerProvider
{
    public abstract MWLocker getBitstreamLocker();
    public abstract MWLocker getCollectionLocker();
    
    private List<Class<? extends DSpaceObject>> implementedClasses = Arrays.asList(Bitstream.class,Collection.class);

    public void setImplementedClasses(List<Class<? extends DSpaceObject>> implementedClasses)
    {
        this.implementedClasses = implementedClasses;
    }
    
    @Override
    public MWLocker getLocker(Class<? extends DSpaceObject> clasz) throws UnsupportedOperationException
    {
        if(clasz.equals(Collection.class))
        {
            return getCollectionLocker();
        }
        else if(clasz.equals(Bitstream.class))
        {
            return getBitstreamLocker();
        }
        else
        {
            throw new UnsupportedOperationException("Given class ["+clasz+" is not supported.");
        }
    }    

    @Override
    public boolean isImplemented(Class<? extends DSpaceObject> clasz)
    {
        return implementedClasses.contains(clasz);
    }

    @Override
    public MWLocker getLocker(DSpaceObject dSpaceObject) throws UnsupportedOperationException
    {
        if(!isImplemented(dSpaceObject.getClass()))
        {
            throw new UnsupportedOperationException();
        }
        else
        {
            return getLocker(dSpaceObject.getClass());
        }
    }
}
