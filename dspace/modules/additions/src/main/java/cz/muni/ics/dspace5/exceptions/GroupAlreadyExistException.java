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
public class GroupAlreadyExistException extends IllegalArgumentException
{

    public GroupAlreadyExistException()
    {
    }

    public GroupAlreadyExistException(String s)
    {
        super(s);
    }

    public GroupAlreadyExistException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public GroupAlreadyExistException(Throwable cause)
    {
        super(cause);
    }
   
}
