/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.impl;

import java.util.ArrayList;
import java.util.List;
import org.dspace.content.Metadatum;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class MetadataWrapper
{
    private List<Metadatum> metadata;

    public List<Metadatum> getMetadata()
    {
        if(metadata == null)
        {
            metadata = new ArrayList<>();
        }
        return metadata;
    }

    public void setMetadata(List<Metadatum> metadata)
    {
        this.metadata = metadata;
    }
}
