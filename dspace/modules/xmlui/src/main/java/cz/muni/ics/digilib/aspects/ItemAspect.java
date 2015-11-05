/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.digilib.aspects;

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
import org.dspace.content.DSpaceObject;
import org.dspace.content.Item;
import org.xml.sax.SAXException;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class ItemAspect extends AbstractDSpaceTransformer
{
    @Override
    public void addBody(Body body) throws SAXException, WingException, UIException, SQLException, IOException, AuthorizeException, ProcessingException
    {
        DSpaceObject dso = HandleUtil.obtainHandle(objectModel);
        if(dso instanceof Item)
        {
            Item item = (Item) dso;
            Division home = body.addDivision("item-view");
            home.addReferenceSet("current-item", ReferenceSet.TYPE_DETAIL_VIEW).addReference(item);
        }            
    }    
}
