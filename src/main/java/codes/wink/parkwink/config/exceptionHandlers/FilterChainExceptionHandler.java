package codes.wink.parkwink.config.exceptionHandlers;

import codes.wink.parkwink.utils.response.Body;
import codes.wink.parkwink.utils.response.RestError;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filter for handling exceptions that occur during the request processing in the filter chain.
 * Catches exceptions, wraps them in a RestError, and writes a JSON response with the error details.
 *
 * <p>Annotations:
 * <ul>
 * <li>@Component - Indicates that this class is a Spring component.</li>
 * </ul>
 * </p>
 */
@Component
public class FilterChainExceptionHandler extends OncePerRequestFilter {

    /**
     * Handles the request and response, catching any exceptions that occur during the filter chain execution.
     *
     * @param request     the HttpServletRequest object.
     * @param response    the HttpServletResponse object.
     * @param filterChain the FilterChain object.
     * @throws ServletException if an error occurs during request processing.
     * @throws IOException      if an input or output error occurs during request processing.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            response.resetBuffer();
            RestError err = new RestError(e);
            response.setStatus(err.getStatus().value());
            response.setContentType("application/json");
            Body<RestError> res = new Body<>(err, err.getStatus());
            String jsonRes = new ObjectMapper().writeValueAsString(res);
            response.getWriter().write(jsonRes);
            response.flushBuffer();
        }
    }

}
