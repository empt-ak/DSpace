/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.digilib.movingwall;

import cz.muni.ics.digilib.domain.Article;
import cz.muni.ics.digilib.domain.Issue;
import cz.muni.ics.digilib.domain.MonographicSeries;
import cz.muni.ics.digilib.domain.Monography;
import cz.muni.ics.digilib.domain.MonographyChapter;
import cz.muni.ics.digilib.domain.Periodical;
import cz.muni.ics.digilib.domain.Volume;
import cz.muni.ics.dspace5.impl.DSpaceTools;
import cz.muni.ics.dspace5.movingwall.MovingWall;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dspace.services.ConfigurationService;
import org.joda.time.DateTime;
import org.joda.time.Months;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Component
public class MovingWallFactoryBean
{
    private static final Logger logger = Logger.getLogger(MovingWallFactoryBean.class);
    private DateTime publDate = null;
    private DateTime endDate = null;
    private int movingWall = 0;
    private String rightsAccess = null;
    private boolean openAcces;
    private boolean ignore;
    private Set<String> skipNames;
    
    @Autowired
    private ConfigurationService configurationService;
    
    @PostConstruct
    private void init()
    {
        skipNames = new HashSet<>();
        // if no values are set then null is returned from config service
        String pNames = configurationService.getProperty("movingwall.ignore.types");
        
        if(!StringUtils.isEmpty(pNames))
        {
            Collections.addAll(skipNames, pNames.split(","));
            logger.info("MovingWall ignore set to types: "+skipNames);
        }
    }

    @Autowired
    private DSpaceTools dSpaceTools;
    
    public DateTime getPublDate()
    {
        return publDate;
    }

    public void setPublDate(DateTime publDate)
    {
        this.publDate = publDate;
    }

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

    public String getRightsAccess()
    {
        return rightsAccess;
    }

    public void setRightsAccess(String rightsAccess)
    {
        this.rightsAccess = rightsAccess;
    }

    public boolean isOpenAcces()
    {
        return openAcces;
    }

    public void setOpenAcces(boolean openAcces)
    {
        this.openAcces = openAcces;
    }

    public boolean isIgnore()
    {
        return ignore;
    }

    public void setIgnore(boolean ignore)
    {
        this.ignore = ignore;
    }
    
    public void parse(Monography monography)
    {
        if(monography != null)
        {
            setPublDate(toDate(monography.getPublYear()));
            setEndDate(toDate(monography.getEmbargoEndDate()));
            
            if(monography.getRightsAccess() != null)
            {
                setRightsAccess(monography.getRightsAccess().value());
            }            
            
            if(monography.getOpenAccess() != null)
            {
               openAcces = Boolean.parseBoolean(monography.getOpenAccess().value()); 
            }
        }
    }
    
    public void parse(MonographicSeries monographicSeries)
    {
        if(monographicSeries != null)
        {
            try
            {
                parseMovingWallValue(monographicSeries.getMovingWall());
            }
            catch(NumberFormatException nfe)
            {
                logger.error(nfe, nfe.getCause());
            }
        }
    }
    
    public void parse(MonographyChapter monographyChapter)
    {
        if(monographyChapter != null)
        {
            ignore = skipNames.contains(monographyChapter.getMonographyChapterType());
        }
    }
    
    public void parse(Periodical periodical)
    {
        if(periodical != null)
        {
            try
            {
                parseMovingWallValue(periodical.getMovingWall());
            }
            catch(NumberFormatException nfe)
            {
                logger.error(nfe,nfe.getCause());
            }
        }
    }
    
    public void parse(Volume volume)
    {
        // do nothing there is no restriction
    }
    
    public void parse(Issue issue)
    {
        if(issue != null)
        {
            setPublDate(toDate(issue.getPublYear()));
            setEndDate(toDate(issue.getEmbargoEndDate()));
        }
    }
    
    public void parse(Article article)
    {
        if(article != null)
        {
            setEndDate(toDate(article.getEmbargoEndDate()));
            ignore = skipNames.contains(article.getArticleType());
        }
    }    
    
    public MovingWall build()
    {
        if(endDate == null)
        {
            return new MovingWallImpl(publDate, 
                    publDate.plus(Months.months(movingWall)), 
                    movingWall, 
                    rightsAccess, 
                    openAcces,
                    ignore
            );
        }
        else
        {
            return new MovingWallImpl(publDate, endDate, movingWall, rightsAccess, openAcces, ignore);
        }
    }   
    
    public void reset()
    {
        this.endDate = null;
        this.movingWall = 0;
        this.publDate = null;
        this.rightsAccess = null;
    }
    
    private DateTime toDate(String value)
    {
        if(!StringUtils.isEmpty(value))
        {
            return dSpaceTools.parseDate(value);
        }
        else
        {
            return null;
        }
    }
    
    private void parseMovingWallValue(String value) throws NumberFormatException
    {
        if(!StringUtils.isEmpty(value))
        {
            movingWall = Integer.parseInt(value);
        }
    }

    @Override
    public String toString()
    {
        return "MovingWallFactoryBean{" + "publDate=" + publDate + ", endDate=" + endDate + ", movingWall=" + movingWall + ", rightsAccess=" + rightsAccess + ", openAcces=" + openAcces + '}';
    }
}
