/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.metadata;

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
    
    public MetadataWrapper()
    {
        this.metadata = new ArrayList<>();
    }

    public List<Metadatum> getMetadata()
    {
        return metadata;
    }

    public void setMetadata(List<Metadatum> metadata)
    {
        this.metadata = metadata;
    }
    
    public void push(List<Metadatum> metadata)
    {
        this.metadata.addAll(metadata);
    }
    
    public void put(Metadatum metadatum)
    {
        metadata.add(metadatum);
    }
}
