/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dmlcz5.services.io.math;

import cz.muni.ics.dspace5.api.module.ObjectWrapper;
import java.io.IOException;
import java.util.List;
import org.dspace.content.Metadatum;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface MathService
{
    List<Metadatum> loadMathFormulas(ObjectWrapper objectWrapper) throws IOException;
}
