package codes.wink.parkwink.config.exceptionHandlers;

import codes.wink.parkwink.utils.response.Body;
import codes.wink.parkwink.utils.response.RestError;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Custom access denied handler for handling access denied exceptions.
 * This class is invoked when an authenticated user attempts to access a resource they do not have permissions for.
 * It responds with a 403 Forbidden status and a JSON body containing the error details.
 *
 * <p>Annotations:
 * <ul>
 * <li>@Component - Indicates that this class is a Spring component.</li>
 * </ul>
 * </p>
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    /**
     * Handles access denied exceptions by sending a 403 Forbidden response with a JSON body.
     *
     * @param request               the HttpServletRequest object.
     * @param response              the HttpServletResponse object.
     * @param accessDeniedException the access denied exception that triggered this handler.
     * @throws IOException      if an input or output error occurs during request processing.
     * @throws ServletException if an error occurs during request processing.
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.resetBuffer();
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        RestError err = new RestError(accessDeniedException);
        Body<RestError> res = new Body<>(err, HttpStatus.FORBIDDEN);
        String jsonRes = new ObjectMapper().writeValueAsString(res);
        response.getWriter().write(jsonRes);
        response.flushBuffer();
    }
}
