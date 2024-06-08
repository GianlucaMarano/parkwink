package codes.wink.parkwink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main class for the Parkwink application.
 * This class contains the main method which is the entry point of the Spring Boot application.
 *
 * <p>Annotations:
 * <ul>
 * <li>@SpringBootApplication - Indicates a configuration class that declares one or more @Bean methods and also triggers auto-configuration and component scanning. It is a convenience annotation that combines @Configuration, @EnableAutoConfiguration, and @ComponentScan.</li>
 * </ul>
 * </p>
 */
@SpringBootApplication
public class ParkwinkApplication {

    /**
     * The main method which serves as the entry point for the Spring Boot application.
     *
     * @param args command-line arguments (optional).
     */
    public static void main(String[] args) {
        SpringApplication.run(ParkwinkApplication.class, args);
    }
}
