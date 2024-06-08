package codes.wink.parkwink.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filter for JWT-based authentication.
 * Intercepts HTTP requests and validates the JWT token.
 *
 * <p>Annotations:
 * <ul>
 * <li>@Component - Indicates that this class is a Spring component.</li>
 * <li>@RequiredArgsConstructor - Generates a constructor with required arguments for final fields.</li>
 * </ul>
 * </p>
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    /**
     * Service for managing JWT operations.
     */
    private final JwtService jwtService;

    /**
     * Service for loading user-specific data.
     */
    private final UserDetailsService userDetailsService;

    /**
     * Filters incoming HTTP requests to validate the JWT token.
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
        final String authHeader = request.getHeader("Authorization");
        final String token;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            // If the authentication type is not Bearer Token, proceed to the next filter in the chain
            filterChain.doFilter(request, response);
            return;
        }

        token = authHeader.substring(7); // Remove "Bearer " from the start of the token

        userEmail = jwtService.extractUsername(token);

        // If not logged in
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());

            authToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        filterChain.doFilter(request, response);
    }
}
