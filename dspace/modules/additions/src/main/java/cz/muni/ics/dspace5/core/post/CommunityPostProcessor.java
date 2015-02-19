/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.core.post;

import cz.muni.ics.dspace5.core.ObjectWrapper;
import cz.muni.ics.dspace5.impl.MetadataRow;
import java.util.List;
import org.dspace.content.Community;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface CommunityPostProcessor
{

    /**
     * Method takes given objectWrapper and processes it into {@code List} of
     * metadata. The way metadata are processed is matter of implementation.
     * {@code ObjectWrapper} contains all necessary information such as path,
     * handle and {@code Meta} object. From path we can recreate given
     * <b>.xml</b> file.
     *
     * @param objectWrapper  from which we extract metadata
     * @param isTopCommunity flag whether we import topcomm, or volume, or
     *                       something lower.
     *
     * @return List of metadata from given objectwrapper.
     *
     * @throws IllegalArgumentException if objectwrapper is null, or does not
     *                                  have a path
     */
    List<MetadataRow> processMetadata(ObjectWrapper objectWrapper, boolean isTopCommunity) throws IllegalArgumentException;

    /**
     * If anything else need to be done to {@code Community} object, then call
     * this method. The usual call is to append thumbnail preview for community,
     * add references and so on.
     *
     * @param objectWrapper holder for current object to be processsed
     * @param community     to be modified
     *
     * @throws IllegalArgumentException if objectWrapper is null or does not
     *                                  have set path. Also thrown when
     *                                  community is null.
     */
    void processCommunity(ObjectWrapper objectWrapper, Community community) throws IllegalArgumentException;
}
