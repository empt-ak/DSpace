/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.digilaw.aspects;

import cz.muni.ics.digilaw.aspects.comparators.LandingPageComparator;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import org.apache.cocoon.ProcessingException;
import org.dspace.app.xmlui.cocoon.AbstractDSpaceTransformer;
import org.dspace.app.xmlui.utils.UIException;
import org.dspace.app.xmlui.wing.WingException;
import org.dspace.app.xmlui.wing.element.Body;
import org.dspace.app.xmlui.wing.element.Division;
import org.dspace.app.xmlui.wing.element.ReferenceSet;
import org.dspace.authorize.AuthorizeException;
import org.dspace.content.Community;
import org.xml.sax.SAXException;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class MainAspect extends AbstractDSpaceTransformer
{
    @Override
    public void addBody(Body body) throws SAXException, WingException, UIException, SQLException, IOException, AuthorizeException, ProcessingException
    {
        Division home = body.addDivision("landing-page", "index");
        
        Community[] topcomms = Community.findAllTop(context);
        
        SortedMap<String,List<Community>> map = new TreeMap<>(new LandingPageComparator());
        
        for(Community comm : topcomms)
        {
            String category = comm.getMetadataByMetadataString("dc.type")[0].value;
            if(map.containsKey(category))
            {
                List<Community> temp = map.get(category);
                temp.add(comm);
                map.put(category, temp);
            }
            else
            {
                List<Community> temp = new ArrayList<>();
                temp.add(comm);
                map.put(category, temp);
            }
        }
        
        
        for(String category : map.keySet())
        {
            Division topcomGroup = home.addDivision("topcoms", category);
            ReferenceSet rs = topcomGroup.addReferenceSet("topcom-listing", ReferenceSet.TYPE_SUMMARY_LIST);
            for(Community c : map.get(category))
            {
                rs.addReference(c);
            }
        }
        
    }
    
}
