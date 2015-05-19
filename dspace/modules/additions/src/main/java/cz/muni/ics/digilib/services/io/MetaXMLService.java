/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.digilib.services.io;

import cz.muni.ics.dspace5.api.ObjectWrapper;
import java.util.List;
import org.dspace.content.Metadatum;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface MetaXMLService
{
    List<Metadatum> loadMetaXML(ObjectWrapper objectWrapper);
}
