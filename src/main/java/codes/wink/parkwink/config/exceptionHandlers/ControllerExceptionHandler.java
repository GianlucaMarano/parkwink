package codes.wink.parkwink.config.exceptionHandlers;

import codes.wink.parkwink.utils.response.Response;
import codes.wink.parkwink.utils.response.RestError;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

/**
 * Global exception handler for controllers.
 * Provides centralized exception handling across all @RequestMapping methods.
 * Extends DefaultHandlerExceptionResolver to handle Spring's default exceptions.
 *
 * <p>Annotations:
 * <ul>
 * <li>@Order - Specifies the order in which this component should be considered among others.</li>
 * <li>@ControllerAdvice - Indicates that this class provides advice to all @RequestMapping methods.</li>
 * </ul>
 * </p>
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ControllerExceptionHandler extends DefaultHandlerExceptionResolver {

    /**
     * Handles generic exceptions by wrapping them in a RestError and returning a Response object.
     * The HTTP status is determined based on the type of error (see RestError class).
     *
     * @param e the exception to handle.
     * @return a Response object containing the RestError and the associated HTTP status.
     */
    @ExceptionHandler(Exception.class)
    public Response<RestError> HandleGenericException(Exception e) {
        RestError restError = new RestError(e);
        return new Response<>(restError, restError.getStatus());
    }
}
