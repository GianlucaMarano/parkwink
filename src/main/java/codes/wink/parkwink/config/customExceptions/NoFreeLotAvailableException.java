package codes.wink.parkwink.config.customExceptions;

/**
 * Custom exception to be thrown when no free parking lot is available.
 * Extends the standard Exception class.
 */
public class NoFreeLotAvailableException extends Exception {

    /**
     * Constructs a new NoFreeLotAvailableException with the specified detail message.
     *
     * @param message the detail message.
     */
    public NoFreeLotAvailableException(String message) {
        super(message);
    }
}
