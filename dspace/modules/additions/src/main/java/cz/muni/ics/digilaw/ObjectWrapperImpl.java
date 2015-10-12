/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.digilaw;

import cz.muni.ics.dspace5.api.module.ObjectWrapper;
import cz.muni.ics.dspace5.impl.DSpaceTools;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class ObjectWrapperImpl implements ObjectWrapper
{

    @Autowired
    private DSpaceTools dSpaceTools;

    private Path path;
    private String handle;
    private Object object;
    private List<ObjectWrapper> children;
    private LEVEL level;

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
    public void setChildren(List<ObjectWrapper> children)
    {
        this.children = children;
    }

    @Override
    public void setLevel(LEVEL level)
    {
        this.level = level;
    }

    @Override
    public LEVEL getLevel()
    {
        return level;
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
        // level.COM itself will never be compared
        // against any other top community

        // volume
        if (this.getLevel().equals(LEVEL.SUBCOM))
        {
            if (!o.getLevel().equals(LEVEL.SUBCOM))
            {
                throw new ClassCastException("Trying to compare SUBCOM with " + o.getLevel());
            }

            int volume1 = Integer.parseInt(StringUtils
                    .substringBefore(this.getPath().getFileName()
                            .toString(), ".xml"));

            int volume2 = Integer.parseInt(StringUtils
                    .substringBefore(o.getPath().getFileName()
                            .toString(), ".xml"));

            return volume1 - volume2;
        }
        else if(this.getLevel().equals(LEVEL.COL))
        {
            if(!o.getLevel().equals(LEVEL.COL))
            {
                throw new ClassCastException("Trying to compare COL with "+o.getLevel());
            }
            
            int issue1 = Integer.parseInt(dSpaceTools.getIssueNumber(this.getPath()));
            int issue2 = Integer.parseInt(dSpaceTools.getIssueNumber(o.getPath()));
            
            return issue1 - issue2;
        }
        else if( this.getLevel().equals(LEVEL.ITEM))
        {
            int article1 = Integer.parseInt(this.getPath().getFileName().toString().substring(1));
            int article2 = Integer.parseInt(o.getPath().getFileName().toString().substring(1));
            
            return article1-article2;
        }
        else
        {
            throw new ClassCastException("Trying to compare "+this.getLevel()+" with "+o.getLevel());
        }
    }

    @Override
    public String toString()
    {
        return "ObjectWrapperImpl{" + "handle=" + handle + ", level=" + level + '}';
    }

    @Override
    public void print()
    {
        int times = 0;
        
        switch(getLevel())
        {
            case COM:
                times = 0;
                break;
            case SUBCOM:
                times = 1;
                break;
            case COL:
                times = 2;
                break;
            case ITEM:
                times = 3;                
        }
        
        for(int i = 0; i < times; i++)
        {
            System.out.print("\t");
        }
        
        System.out.println(getLevel()+"@path "+getPath());
        
        if(children != null)
        {
            for(ObjectWrapper ow : children)
            {
                ow.print();
            }
        }        
    }
}
