package at.jku.se.diary.exceptions;
/**
 * Exception class for TagEntry Exceptions
 */
public class TagEntryException extends Throwable{

    /**
     * Constructor
      * @param message the message of the exception
     */
    public TagEntryException(String message) {
        super(message);
    }
}
