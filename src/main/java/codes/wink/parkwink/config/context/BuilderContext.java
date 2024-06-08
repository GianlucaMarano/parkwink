package codes.wink.parkwink.config.context;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for setting up the application context related to object mapping.
 * Provides a ModelMapper bean with customized configuration.
 *
 * <p>Annotations:
 * <ul>
 * <li>@Configuration - Indicates that this class contains Spring bean definitions.</li>
 * </ul>
 * </p>
 */
@Configuration
public class BuilderContext {

    /**
     * Creates and configures a ModelMapper bean.
     * The ModelMapper is configured to skip null values during the mapping process.
     *
     * @return a configured ModelMapper instance.
     */
    @Bean
    public ModelMapper newMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setSkipNullEnabled(true);
        return mapper;
    }
}
