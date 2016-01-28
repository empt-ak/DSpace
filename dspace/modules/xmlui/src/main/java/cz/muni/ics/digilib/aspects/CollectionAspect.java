/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.digilib.aspects;

import cz.muni.ics.digilib.aspects.comparators.ItemComparator;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
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
import org.dspace.content.Item;
import org.dspace.content.ItemIterator;
import org.xml.sax.SAXException;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class CollectionAspect extends AbstractDSpaceTransformer
{
    private final Set<String> fcbItems = new HashSet<>(3);

    public CollectionAspect()
    {
        fcbItems.add("Front-matter");
        fcbItems.add("Back-matter");
        fcbItems.add("Table-of-Contents");
    }

    @Override
    public void addBody(Body body) throws SAXException, WingException, UIException, SQLException, IOException, AuthorizeException, ProcessingException
    {
        DSpaceObject dso = HandleUtil.obtainHandle(objectModel);
        if (dso instanceof Collection)
        {
            Collection col = (Collection) dso;

            Division home = body.addDivision("collection-view");
            home.addReferenceSet("current-collection", ReferenceSet.TYPE_DETAIL_VIEW).addReference(col);

            // celebrities has volumes, monos and proceedings do not
            // double call on getparent results in nullpointer exception
            Community topComm = null;
            try
            {
                topComm = (Community) col.getParentObject().getParentObject();
            }
            catch (NullPointerException npe)
            {

            }

            if (topComm != null)
            {
                if (topComm.getMetadataByMetadataString("dc.type")[0].value.equals("JOURNAL"))
                {
                    ReferenceSet issueSiblings = home.addReferenceSet("issue-siblings", ReferenceSet.TYPE_SUMMARY_LIST);

                    Community volume = (Community) col.getParentObject();

                    for (Collection sibling : AspectUtils.getSortedIssuesForVolume(volume.getCollections()))
                    {
                        issueSiblings.addReference(sibling);
                    }
                }
            }
            
            Map<ItemSection,TreeSet<Item>> articles = new LinkedHashMap<>();
            List<ItemSection> sections = new ArrayList<>();
            Division fcb = home.addDivision("fcb");
            
            SortedSet<Item> sortedSet = new TreeSet<>(new ItemComparator());
            ItemIterator ii = col.getItems();
            while (ii.hasNext())
            {
                sortedSet.add(ii.next());
            }

            int noSectionCount = 0;
            for (Item i : sortedSet)
            {                
                String type = i.getMetadata("dc.type");
                
                if(fcbItems.contains(type))
                {
                    fcb.addReferenceSet(type, ReferenceSet.TYPE_SUMMARY_LIST).addReference(i);
                }                
                else
                {
                    String position = i.getMetadata("");
                    String name = i.getMetadata("");
                    ItemSection section = new ItemSection();
                    section.setPosition(Integer.parseInt(position));
//                    if()
//                    if(!sections.isEmpty())
//                    {
//                        if()
//                    }
//                    else
//                    {
//                        Section
//                    }
                }
            }
        }
    }
}
