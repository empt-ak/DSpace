/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.ics.dspace5.exceptions;

/**
 *
 * @author Dominik Szalai - emptulik at gmail.com
 */
public class MovingWallException extends Exception
{
    public MovingWallException()
    {
    }

    public MovingWallException(String message)
    {
        super(message);
    }

    public MovingWallException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public MovingWallException(Throwable cause)
    {
        super(cause);
    }

    public MovingWallException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
