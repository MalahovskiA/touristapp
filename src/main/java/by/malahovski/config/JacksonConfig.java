package by.malahovski.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Configuration class for customizing Jackson's {@link ObjectMapper}.
 * This class registers the {@link JavaTimeModule} with the {@link ObjectMapper} to ensure
 * proper handling of Java 8 date and time classes (e.g., {@code LocalDate}, {@code LocalDateTime}).
 */
@Configuration
public class JacksonConfig {

    /**
     * Configures and provides a customized {@link ObjectMapper} bean.
     * The {@link ObjectMapper} is configured with the {@link JavaTimeModule}
     * to support the serialization and deserialization of Java 8 time classes.
     *
     * @return a configured {@link ObjectMapper}
     */
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }
}