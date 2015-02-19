/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.impl;

import cz.muni.ics.dspace5.core.ObjectWrapper;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class ObjectWrapperImpl implements ObjectWrapper, Comparable<ObjectWrapper>
{
    @Autowired
    private DSpaceTools dSpaceTools;
    
    private Path path;
    private String handle;
    private Object object;
    private boolean isVolume;
    private List<ObjectWrapper> children;
    
    @Override
    public void setPath(Path path)
    {
        this.path = path;
    }

    @Override
    public void setHandle(String handle)
    {
        this.handle = handle;
    }

    @Override
    public void setObject(Object object)
    {
        this.object = object;
    }

    @Override
    public void setVolume(boolean isVolume)
    {
        this.isVolume = isVolume;
    }

    @Override
    public void setChildren(List<ObjectWrapper> children)
    {
        this.children = children;
    }

    @Override
    public Path getPath()
    {
        return path;
    }

    @Override
    public String getHandle()
    {
        return handle;
    }

    @Override
    public boolean isVolume()
    {
        return isVolume;
    }

    @Override
    public List<ObjectWrapper> getChildren()
    {
        return children;
    }

    @Override
    public <T> T getObject()
    {
        return (T) object;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.handle);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final ObjectWrapperImpl other = (ObjectWrapperImpl) obj;
        return Objects.equals(this.handle, other.handle);
    }

    @Override
    public int compareTo(ObjectWrapper o)
    {
        // fileName has following format: volumeNumber-Year-IssueNumber
        String[] arrayFileName1 = this.path.getFileName().toString().split("-");
        String[] arrayFileName2 = o.getPath().getFileName().toString().split("-");
        if(this.isVolume != o.isVolume())
        {
            return -1;
        }
        
        if(this.isVolume)
        { 
            //volume number is @first place
            Integer volume1number = Integer.parseInt(arrayFileName1[0]);
            Integer volume2number = Integer.parseInt(arrayFileName2[0]);
            
            //p1 and p2 now contains 2 numbers which we only have to compare.
            
            return volume1number.compareTo(volume2number);
        }
        else
        {
            int level = dSpaceTools.getPathLevel(this.path);
            
            //level cannot be 0 or at least shouldn't be comparable.
            //0 means object wrapper points to serial folder e.g. serial/0_Thisisnice
            
            if(level == 1)
            {
                //collection
                Integer prefix1 = Integer.parseInt(arrayFileName1[2]);
                Integer prefix2 = Integer.parseInt(arrayFileName2[2]);
                
                return prefix1.compareTo(prefix2);
            }
            else if(level == 2) //@meditor: NOVY MEDITOR BUDE MAT NA LVL 2 VOLUME !!!!
            {
                //item (article) starts with #
                //having "#1","#3","#26","#16","#2"
                //will by default sort as #1, #16, #2, #26, #3
                //because of lexicographical order 
                //so we trim # out and then compare them as numbers
                Integer article1 = Integer.parseInt(this.path.getFileName().toString().substring(1));
                Integer article2 = Integer.parseInt(o.getPath().getFileName().toString().substring(1));
                
                return article1.compareTo(article2);
            }
            else if(level == 3)
            {
                return -1;
            }
            
            return -1;
        }
    }
}
