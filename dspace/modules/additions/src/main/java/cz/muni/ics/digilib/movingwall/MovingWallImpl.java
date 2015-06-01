/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.digilib.movingwall;

import cz.muni.ics.dspace5.movingwall.MovingWall;
import org.joda.time.DateTime;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class MovingWallImpl implements MovingWall
{
    private DateTime publDate;
    private DateTime endDate;
    private int movingWall;
    private String rightsAccess;
    private boolean openAccess;
    private boolean ignore;

    public MovingWallImpl()
    {
    }

    public MovingWallImpl(DateTime publDate, DateTime endDate, int movingWall, String rightsAccess, boolean openAccess, boolean ignore)
    {
        this.publDate = publDate;
        this.endDate = endDate;
        this.movingWall = movingWall;
        this.rightsAccess = rightsAccess;
        this.openAccess = openAccess;
        this.ignore = ignore;
    }
    
    @Override
    public DateTime getPublDate()
    {
        return publDate;
    }

    @Override
    public boolean isOpenAccess()
    {
        return openAccess;
    }

    public void setOpenAccess(boolean openAccess)
    {
        this.openAccess = openAccess;
    }   

    public void setPublDate(DateTime publDate)
    {
        this.publDate = publDate;
    }

    @Override
    public DateTime getEndDate()
    {
        return endDate;
    }

    public void setEndDate(DateTime endDate)
    {
        this.endDate = endDate;
    }

    public int getMovingWall()
    {
        return movingWall;
    }

    public void setMovingWall(int movingWall)
    {
        this.movingWall = movingWall;
    }

    @Override
    public String getRightsAccess()
    {
        return rightsAccess;
    }

    public void setRightsAccess(String rightsAccess)
    {
        this.rightsAccess = rightsAccess;
    }
    
    @Override
    public boolean ignore()
    {
        return ignore;
    }

    @Override
    public String toString()
    {
        return "MovingWallImpl{" + "publDate=" + publDate + ", endDate=" + endDate + ", movingWall=" + movingWall + ", rightsAccess=" + rightsAccess + ", openAccess=" + openAccess + ", ignore=" + ignore + '}';
    }
}
