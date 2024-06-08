package codes.wink.parkwink.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for registering interceptors in the Spring MVC application.
 * Implements the WebMvcConfigurer interface to customize the default Spring MVC configuration.
 *
 * <p>Annotations:
 * <ul>
 * <li>@Component - Indicates that this class is a Spring component.</li>
 * </ul>
 * </p>
 */
@Component
public class InterceptorConfig implements WebMvcConfigurer {

    /**
     * The ServiceInterceptor instance to be registered.
     */
    @Autowired
    private ServiceInterceptor serviceInterceptor;

    /**
     * Adds the ServiceInterceptor to the application's interceptor registry.
     *
     * @param registry the InterceptorRegistry to which the interceptor is added.
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(serviceInterceptor);
    }
}
