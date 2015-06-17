/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.movingwall;

import java.nio.file.Path;
import org.joda.time.DateTime;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public interface MovingWall
{

    /**
     * Method returns Date specifying when embargo restriction is about to end.
     *
     * @return end date of embargo
     */
    DateTime getEndDate();

    /**
     * Method returns Date of publication of given object.
     *
     * @return publication date
     */
    DateTime getPublDate();

    /**
     * Method returns RigtsAccess value. If this field is not used in actual
     * schema, then null is returned.
     *
     * @return RigtsAccess value
     */
    String getRightsAccess();

    /**
     * Method returns isOpenAccess field (if field is used). If this field is
     * not used within schema, then null is returned.
     *
     * @return OpenAccess field value
     */
    boolean isOpenAccess();

    /**
     * Method specifying whether MovingWall method execution should be ignored.
     * An example might be some value overriding the whole moving wall creation.
     * E.g. one Article can be available, but the rest is locked.
     *
     * @return true if moving wall should be ignored, false otherwise.
     */
    boolean ignore();
    
    /**
     * Value for extra storage path, which is used for whole books, epub, etc. If value is not used, then null is returned.
     * @return path to extra storage, null if there is none.
     */
    Path extraStorage();
}
