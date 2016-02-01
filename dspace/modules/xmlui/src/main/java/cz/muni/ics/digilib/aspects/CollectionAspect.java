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
        fcbItems.add("Front-Matter");
        fcbItems.add("Back-Matter");
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

            Map<ItemSection, SortedSet<Item>> articles = new LinkedHashMap<>();
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

                if (fcbItems.contains(type))
                {
                    fcb.addReferenceSet(type, ReferenceSet.TYPE_SUMMARY_LIST).addReference(i);
                }
                else
                {
                    String position = i.getMetadata("digilib.position.article");
                    String name = i.getMetadata("dc.description.section");
                    ItemSection currentSection = new ItemSection();
                    currentSection.setPosition(Integer.parseInt(position));
                    if (name == null || name.isEmpty())
                    {
                        currentSection.setSectionName(noSectionCount + "");
                        currentSection.setRegularSection(false);
                        noSectionCount++;
                    }
                    else
                    {
                        currentSection.setSectionName(name);
                    }
                    
                    putToArticleMap(currentSection, i, sections, articles);
                }  
            }
            
            Division articlesDiv = home.addDivision("articles");
            
            for(Map.Entry<ItemSection,SortedSet<Item>> entry : articles.entrySet())
            {
                ReferenceSet sectionReferenceSet;
                if(entry.getKey().isRegularSection())
                {
                    sectionReferenceSet = articlesDiv.addReferenceSet(entry.getKey().getSectionName(), ReferenceSet.TYPE_SUMMARY_LIST);
                }
                else
                {
                    sectionReferenceSet = articlesDiv.addReferenceSet("nosection"+entry.getKey().getSectionName(), ReferenceSet.TYPE_SUMMARY_LIST);
                }
                
                for(Item item : entry.getValue())
                {
                    sectionReferenceSet.addReference(item);
                }
            }
        }
    }

    private void putToArticleMap(ItemSection currentSection, Item currentItem, List<ItemSection> sectionList, Map<ItemSection, SortedSet<Item>> articles)
    {
        if (getLast(sectionList) == null)
        {
            // its first item ever
            sectionList.add(currentSection);
            SortedSet<Item> sectionItems = new TreeSet<>(new ItemComparator());
            sectionItems.add(currentItem);
            articles.put(currentSection, sectionItems);
        }
        else
        {
            ItemSection previous = getLast(sectionList);
            if (!(currentSection.isRegularSection() ^ previous.isRegularSection()))
            {
                // items are either both regular or irregular
                if (currentSection.getSectionName().equals(previous.getSectionName()))
                {
                    //item belong to the same section
                    SortedSet<Item> sectionItems = articles.get(previous);
                    sectionItems.add(currentItem);
                    articles.put(previous, sectionItems);
                }
                else
                {
                    // items belong to different section
                    SortedSet<Item> sectionItems = new TreeSet<>(new ItemComparator());
                    sectionItems.add(currentItem);
                    articles.put(currentSection, sectionItems);
                    sectionList.add(currentSection);
                }
            }
            else
            {
                SortedSet<Item> sectionItems = new TreeSet<>(new ItemComparator());
                sectionItems.add(currentItem);
                articles.put(currentSection, sectionItems);
                sectionList.add(currentSection);
            }
        }
    }

    private ItemSection getLast(List<ItemSection> sectionList)
    {
        if (sectionList.isEmpty())
        {
            return null;
        }
        return sectionList.get(sectionList.size() - 1);
    }
}
