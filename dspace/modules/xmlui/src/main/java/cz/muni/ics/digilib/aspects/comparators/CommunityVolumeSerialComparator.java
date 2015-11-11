/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.digilib.aspects.comparators;

import java.util.Comparator;
import org.dspace.content.Community;

/**
 *
 * @author emptak
 */
public class CommunityVolumeSerialComparator implements Comparator<Community>
{

    @Override
    public int compare(Community o1, Community o2)
    {
        return Integer.valueOf(
                o1.getMetadataByMetadataString("digilib.position.volume")[0].value
        ).compareTo(
                Integer.valueOf(
                        o2.getMetadataByMetadataString("digilib.position.volume")[0].value
                )
        );
    }
    
}
