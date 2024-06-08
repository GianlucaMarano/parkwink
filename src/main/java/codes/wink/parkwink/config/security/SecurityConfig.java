package codes.wink.parkwink.config.security;

import codes.wink.parkwink.config.exceptionHandlers.CustomAccessDeniedHandler;
import codes.wink.parkwink.config.exceptionHandlers.CustomAuthEntryPoint;
import codes.wink.parkwink.config.exceptionHandlers.FilterChainExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

/**
 * Security configuration class for the application.
 * Configures security filters, authentication providers, and exception handlers.
 *
 * <p>Annotations:
 * <ul>
 * <li>@Configuration - Indicates that this class contains Spring bean definitions.</li>
 * <li>@EnableWebSecurity - Enables Spring Security's web security support.</li>
 * <li>@EnableMethodSecurity - Enables method-level security based on annotations.</li>
 * <li>@RequiredArgsConstructor - Generates a constructor with required arguments for final fields.</li>
 * </ul>
 * </p>
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    /**
     * JWT authentication filter.
     */
    private final JwtAuthenticationFilter jwtAuthFilter;

    /**
     * Authentication provider.
     */
    private final AuthenticationProvider authenticationProvider;

    /**
     * Custom access denied handler.
     */
    private final CustomAccessDeniedHandler accessDeniedHandler;

    /**
     * Custom authentication entry point.
     */
    private final CustomAuthEntryPoint entryPoint;

    /**
     * Filter chain exception handler.
     */
    private final FilterChainExceptionHandler filterChainExceptionHandler;

    /**
     * Configures the security filter chain.
     *
     * @param http the HttpSecurity object to configure.
     * @return the configured SecurityFilterChain.
     * @throws Exception if an error occurs during configuration.
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(filterChainExceptionHandler, LogoutFilter.class)
                .exceptionHandling(exc -> exc.accessDeniedHandler(accessDeniedHandler).authenticationEntryPoint(entryPoint));

        return http.build();
    }

}
