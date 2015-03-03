/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.imports;

import cz.muni.ics.dspace5.core.ObjectWrapper;
import java.util.List;
import org.dspace.content.Collection;
import org.dspace.core.Context;
import org.springframework.stereotype.Component;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
@Component
public class ImportCollection
{
    public Collection importToDspace(ObjectWrapper objectWrapper, List<ObjectWrapper> parents, Context context)
    {
        return null;
    }
}
