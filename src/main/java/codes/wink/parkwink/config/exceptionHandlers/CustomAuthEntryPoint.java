package codes.wink.parkwink.config.exceptionHandlers;

import codes.wink.parkwink.utils.response.Body;
import codes.wink.parkwink.utils.response.RestError;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Custom authentication entry point for handling authentication exceptions.
 * This class is invoked when an unauthenticated user attempts to access a secured resource.
 * It responds with a 401 Unauthorized status and a JSON body containing the error details.
 *
 * <p>Annotations:
 * <ul>
 * <li>@Component - Indicates that this class is a Spring component.</li>
 * </ul>
 * </p>
 */
@Component
public class CustomAuthEntryPoint implements AuthenticationEntryPoint {

    /**
     * Handles authentication exceptions by sending a 401 Unauthorized response with a JSON body.
     *
     * @param request       the HttpServletRequest object.
     * @param response      the HttpServletResponse object.
     * @param authException the authentication exception that triggered this entry point.
     * @throws IOException      if an input or output error occurs during request processing.
     * @throws ServletException if an error occurs during request processing.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        response.resetBuffer();
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        RestError err = new RestError(authException);
        Body<RestError> res = new Body<>(err, HttpStatus.UNAUTHORIZED);
        String jsonRes = new ObjectMapper().writeValueAsString(res);
        response.getWriter().write(jsonRes);
        response.flushBuffer();
    }
}
