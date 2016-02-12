/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.digilaw.aspects;

import java.io.IOException;
import java.sql.SQLException;
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
import org.dspace.content.ItemIterator;
import org.xml.sax.SAXException;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class CollectionAspect extends AbstractDSpaceTransformer
{

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

            ReferenceSet items = null;
            if (topComm != null)
            {
                if(topComm.getMetadataByMetadataString("dc.type")[0].value.equals("celebrity"))
                {
                    items = home.addReferenceSet("item-list", ReferenceSet.TYPE_SUMMARY_LIST);
                }
                else if(topComm.getMetadataByMetadataString("dc.type")[0].value.equals("serial"))
                {
                    ReferenceSet issueSiblings = home.addReferenceSet("issue-siblings", ReferenceSet.TYPE_SUMMARY_LIST);
                    
                    Community volume = (Community) col.getParentObject();
                    
                    for(Collection sibling : AspectUtils.getSortedIssuesForVolume(volume.getCollections()))
                    {
                        issueSiblings.addReference(sibling);
                    }
                    
                    items = home.addReferenceSet("item-list", ReferenceSet.TYPE_SUMMARY_LIST);
                }
            }
            else
            {
                items = home.addReferenceSet("item-list", ReferenceSet.TYPE_SUMMARY_LIST);
            }

            // by default items are returned in alphabetical order
//            SortedSet<Item> sortedSet = new TreeSet<>(new ItemComparator());
            ItemIterator ii = col.getItems();
            while (ii.hasNext())
            {
                items.addReference(ii.next());
            }

//            for (Item i : sortedSet)
//            {
//                items.addReference(i);
//            }
        }
    }
}
