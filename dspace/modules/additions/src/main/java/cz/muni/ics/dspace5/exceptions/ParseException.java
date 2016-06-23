package cz.muni.ics.dspace5.exceptions;

/**
 * @author Dominik Szalai - emptulik at gmail.com on 6/23/16.
 */
public class ParseException extends Exception
{
    public ParseException()
    {
    }

    public ParseException(String message)
    {
        super(message);
    }

    public ParseException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public ParseException(Throwable cause)
    {
        super(cause);
    }

    public ParseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
