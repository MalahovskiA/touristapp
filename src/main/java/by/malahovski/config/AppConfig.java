package by.malahovski.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;


/**
 * Application configuration class for setting up the Spring context.
 * This class configures the main components of the Spring application,
 * including enabling AspectJ proxying, Spring MVC, and transaction management.
 * It also sets up property placeholders and view resolution for the web layer.
 */
@Configuration
@EnableAspectJAutoProxy
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan(basePackages = "by.malahovski")
public class AppConfig implements WebMvcConfigurer {

    /**
     * Bean for configuring property placeholder resolution.
     * This method configures the {@link PropertySourcesPlaceholderConfigurer}
     * to load properties from the "application.yaml" file, which is located in
     * the classpath. This enables the application to use property placeholders
     * from externalized configuration files.
     *
     * @return a configured {@link PropertySourcesPlaceholderConfigurer}
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        configurer.setLocation(new ClassPathResource("application.yaml"));
        return configurer;
    }

    /**
     * Bean for resolving view names to JSP files.
     * This method configures an {@link InternalResourceViewResolver} to map
     * view names to JSP files located under the "/WEB-INF/views/" directory
     * with the ".jsp" suffix. It is used by Spring MVC to resolve views.
     *
     * @return a configured {@link InternalResourceViewResolver}
     */
    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/swagger-ui/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}