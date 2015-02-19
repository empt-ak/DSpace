/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.core;

import java.nio.file.Path;
import java.util.List;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface ObjectWrapper
{
    Path getPath();
    String getHandle();
    <T> T getObject();
    List<ObjectWrapper> getChildren();
    void setChildren(List<ObjectWrapper> children);
}
