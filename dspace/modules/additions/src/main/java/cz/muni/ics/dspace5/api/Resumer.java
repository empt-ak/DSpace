/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.api;

import java.nio.file.Path;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface Resumer
{
    void register(Path path);
    void reset(Path path);
    void resume(Path path);
    void add(Path root, Path path);
    void remove(Path root, Path path);
}
