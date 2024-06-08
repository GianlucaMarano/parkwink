package codes.wink.parkwink.config.context;

import codes.wink.parkwink.entities.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.NoSuchElementException;

/**
 * Security configuration class for setting up authentication-related beans.
 * Provides beans for password encoding, user details service, authentication provider, and authentication manager.
 *
 * <p>Annotations:
 * <ul>
 * <li>@Order - Specifies the order in which this configuration should be considered among others.</li>
 * <li>@Configuration - Indicates that this class contains Spring bean definitions.</li>
 * </ul>
 * </p>
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@Configuration
public class SecurityContext {

    /**
     * UserRepository instance for accessing user data.
     */
    @Autowired
    private UserRepository repo;

    /**
     * Creates a BCryptPasswordEncoder bean for encoding passwords.
     *
     * @return a BCryptPasswordEncoder instance.
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Creates a UserDetailsService bean for loading user-specific data.
     *
     * @return a UserDetailsService instance.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> repo.findByEmail(username)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
    }

    /**
     * Creates an AuthenticationProvider bean for authentication.
     * Uses DaoAuthenticationProvider with a custom UserDetailsService and password encoder.
     *
     * @return an AuthenticationProvider instance.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Creates an AuthenticationManager bean for managing authentication.
     *
     * @param config the AuthenticationConfiguration instance.
     * @return an AuthenticationManager instance.
     * @throws Exception if an error occurs during authentication manager creation.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
