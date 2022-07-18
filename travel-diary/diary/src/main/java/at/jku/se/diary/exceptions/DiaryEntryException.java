package at.jku.se.diary.exceptions;

/**
 * Exception class for DiaryEntryExceptions
 */
public class DiaryEntryException extends Throwable {
    /**
     * Constructor
      * @param message the message of the Exception
     */
    public DiaryEntryException(String message) {
        super(message);
    }
}
