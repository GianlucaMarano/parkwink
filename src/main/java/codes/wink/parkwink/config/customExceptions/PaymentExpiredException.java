package codes.wink.parkwink.config.customExceptions;

/**
 * Custom exception to be thrown when a payment is attempted more than 10 minutes after the parking session has ended.
 * This exception indicates that the ticket should be updated and the payment recalculated.
 * Extends the standard Exception class.
 */
public class PaymentExpiredException extends Exception {

    /**
     * Constructs a new PaymentExpiredException with the specified detail message.
     *
     * @param message the detail message.
     */
    public PaymentExpiredException(String message) {
        super(message);
    }
}
