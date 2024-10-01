package by.malahovski;

import by.malahovski.config.AppConfig;
import by.malahovski.config.DatabaseConfig;
import by.malahovski.config.JacksonConfig;
import by.malahovski.config.JpaConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletRegistration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class WebApplication implements WebApplicationInitializer {

    private static final Logger logger = LogManager.getLogger(WebApplication.class);

    @Override
    public void onStartup(ServletContext servletContext) {
        logger.info("Инициализация веб приложения...");

        try {
            // Load Spring web application configuration
            AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
            context.register(AppConfig.class, DatabaseConfig.class, JpaConfig.class, JacksonConfig.class);

            // Create and register the DispatcherServlet
            DispatcherServlet servlet = new DispatcherServlet(context);
            ServletRegistration.Dynamic registration = servletContext.addServlet("dispatcher", servlet);
            registration.setLoadOnStartup(1);
            // Map to the root URL pattern
            registration.addMapping("/");

            logger.info("Веб приложение инициализировано успешно");
        } catch (Exception e) {
            logger.error("Сбой инициализации приложения", e);
        }
    }
}