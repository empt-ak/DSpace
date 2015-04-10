/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.digilib.movingwall;

import cz.muni.ics.dspace5.exceptions.MovingWallException;
import cz.muni.ics.dspace5.movingwall.MWLockerProvider;
import cz.muni.ics.dspace5.movingwall.MovingWallService;
import java.util.Map;
import org.dspace.content.Bitstream;
import org.dspace.content.Collection;
import org.dspace.content.DSpaceObject;
import org.dspace.core.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Component(value = "mowingWallService")
public class MovingWallServiceImpl implements MovingWallService
{
    @Autowired
    private MWLockerProvider mWLockerProvider;

    @Override
    public void execute(String[] args)
    {
        throw new UnsupportedOperationException("Not supported yet."); //TODO
    }

    @Override
    public void lock(DSpaceObject dSpaceObject, Map<String, Object> dataMap) throws IllegalArgumentException, MovingWallException
    {
        switch(dSpaceObject.getType())
        {
            case Constants.BITSTREAM:
                mWLockerProvider.getLocker(Bitstream.class).lockObject(dSpaceObject, dataMap);
                break;
            case Constants.COLLECTION:
                mWLockerProvider.getLocker(Collection.class).lockObject(dSpaceObject, dataMap);
                break;
            default:
                throw new IllegalArgumentException("Invalid type of dSpaceObject.");
        }
    }

    @Override
    public void unlock(DSpaceObject dSpaceObject, Map<String, Object> dataMap) throws IllegalArgumentException, MovingWallException
    {
        switch(dSpaceObject.getType())
        {
            case Constants.BITSTREAM:
                mWLockerProvider.getLocker(Bitstream.class).unlockObject(dSpaceObject, dataMap);
                break;
            case Constants.COLLECTION:
                mWLockerProvider.getLocker(Collection.class).unlockObject(dSpaceObject, dataMap);
                break;
            default:
                throw new IllegalArgumentException("Invalid type of dSpaceObject.");
        }
    }    
}
