package codes.wink.parkwink.config.customExceptions;

/**
 * Custom exception to be thrown when an attempt is made to mark a ticket as paid before the parking session has ended.
 * Extends the standard Exception class.
 */
public class ParkingNotEndedException extends Exception {

    /**
     * Constructs a new ParkingNotEndedException with the specified detail message.
     *
     * @param message the detail message.
     */
    public ParkingNotEndedException(String message) {
        super(message);
    }
}
