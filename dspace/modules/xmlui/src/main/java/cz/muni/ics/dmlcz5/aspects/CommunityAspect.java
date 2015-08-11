/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dmlcz5.aspects;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.cocoon.ProcessingException;
import org.dspace.app.xmlui.cocoon.AbstractDSpaceTransformer;
import org.dspace.app.xmlui.utils.HandleUtil;
import org.dspace.app.xmlui.utils.UIException;
import org.dspace.app.xmlui.wing.WingException;
import org.dspace.app.xmlui.wing.element.Body;
import org.dspace.app.xmlui.wing.element.Division;
import org.dspace.app.xmlui.wing.element.ReferenceSet;
import org.dspace.authorize.AuthorizeException;
import org.dspace.content.Collection;
import org.dspace.content.Community;
import org.dspace.content.DSpaceObject;
import org.xml.sax.SAXException;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class CommunityAspect extends AbstractDSpaceTransformer
{

    @Override
    public void addBody(Body body) throws SAXException, WingException, UIException, SQLException, IOException, AuthorizeException, ProcessingException
    {
        DSpaceObject dso = HandleUtil.obtainHandle(objectModel);
        if (dso instanceof Community)
        {
            Community comm = (Community) dso;

            if (comm.getParentCommunity() == null)
            {
                //@TODO else type mono/jour/celeb 
                //topcom
                Division home = body.addDivision("community-view", "top-comm");
                home.addReferenceSet("current-community", ReferenceSet.TYPE_DETAIL_VIEW).addReference(comm);

                //ReferenceSet rstable = home.addReferenceSet("volume-table", ReferenceSet.TYPE_SUMMARY_LIST);
                if (comm.getSubcommunities() != null && comm.getSubcommunities().length > 0)
                {
                    Map<String, List<Community>> map = prepareMap(comm.getSubcommunities());

                    for (String subTable : map.keySet())
                    {
                        Division divSubTable = home.addDivision("sub-table", subTable);

                        for (int i = 0; i < map.get(subTable).size(); i++)
                        {
                            Community volume = map.get(subTable).get(i);

                            ReferenceSet rs = divSubTable.addReferenceSet("volume", ReferenceSet.TYPE_SUMMARY_VIEW, null, String.valueOf(i));
                            rs.addReference(volume);

                            for (Collection issue : volume.getCollections())
                            {
                                rs.addReference(issue);
                            }
                        }
                    }
                }
                else if (comm.getCollections() != null && comm.getCollections().length > 0)
                {
                    Division debug = home.addDivision("DEBUG");
                    debug.addPara(comm.getCollections().length+"");
//                    switch (comm.getMetadataByMetadataString("dc.type")[0].value)
//                    {
//                        case "monograph":
//                        {
////                            SortedSet<Collection> set = new TreeSet<>(new CollectionMonographyComparator());
////                            set.addAll(Arrays.asList(comm.getCollections()));
//                            
//                            ReferenceSet rs = home.addReferenceSet("collection-list", ReferenceSet.TYPE_SUMMARY_LIST);
//                            for(Collection c : comm.getCollections())
//                            {
//                                rs.addReference(c);
//                            }
//                        }
//                        break;
//                        default:
//                        {
//                            ReferenceSet rs = home.addReferenceSet("collection-list", ReferenceSet.TYPE_SUMMARY_LIST);
//
//                            for (Collection c : comm.getCollections())
//                            {
//                                rs.addReference(c);
//                            }
//                        }
//                    }
                }
            }
            else
            {
                Division home = body.addDivision("community-view", "volume");
                ReferenceSet volume = home.addReferenceSet("volume", ReferenceSet.TYPE_DETAIL_VIEW);
                volume.addReference(comm);

                if (comm.getCollections() != null && comm.getCollections().length > 0)
                {
                    for (Collection col : comm.getCollections())
                    {
                        volume.addReference(col);
                    }
                }
            }
        }
    }

    private Map<String, List<Community>> prepareMap(Community[] volumes)
    {
        Map<String, List<Community>> map = new HashMap<>();
        if (volumes != null && volumes.length > 0)
        {
            // kontajnery, naplnenie a zoradenie        
            List<Community> data = new ArrayList<>();
            data.addAll(Arrays.asList(volumes));

            //Collections.sort(data, AspectComparators.volumeComparator);
            // najdeme stred
            int mid;
            if (data.size() % 2 != 0)
            {
                mid = (data.size() / 2) + 1;
            }
            else
            {
                mid = data.size() / 2;
            }

            // a vlozime pod polie
            map.put("left", data.subList(0, mid));
            map.put("right", data.subList(mid, data.size()));
        }

        return map;
    }

}
