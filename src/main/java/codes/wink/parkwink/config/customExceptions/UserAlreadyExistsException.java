package codes.wink.parkwink.config.customExceptions;

/**
 * Custom exception to be thrown when an attempt is made to create a user that already exists in the system.
 * Extends the standard Exception class.
 */
public class UserAlreadyExistsException extends Exception {

    /**
     * Constructs a new UserAlreadyExistsException with the specified detail message.
     *
     * @param message the detail message.
     */
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
