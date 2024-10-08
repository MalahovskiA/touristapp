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


/**
 * The {@code WebApplication} class is an implementation of the {@link WebApplicationInitializer}
 * interface that is responsible for initializing the web application context.
 * <p>
 * It configures the Spring application context and registers the {@link DispatcherServlet}
 * to handle incoming requests.
 * </p>
 */
public class WebApplication implements WebApplicationInitializer {

    private static final Logger logger = LogManager.getLogger(WebApplication.class);


    /**
     * This method is called during the startup of the web application.
     * It initializes the Spring application context and registers the
     * {@link DispatcherServlet} with the provided {@link ServletContext}.
     *
     * @param servletContext the {@link ServletContext} to be used for
     *                       registering the servlet.
     */
    @Override
    public void onStartup(ServletContext servletContext) {
        logger.info("Initializing web application...");

        try {
            AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
            context.register(AppConfig.class, DatabaseConfig.class, JpaConfig.class, JacksonConfig.class);

            DispatcherServlet servlet = new DispatcherServlet(context);
            ServletRegistration.Dynamic registration = servletContext.addServlet("dispatcher", servlet);
            registration.setLoadOnStartup(1);
            registration.addMapping("/");

            logger.info("The web application was initialized successfully.");
        } catch (Exception e) {
            logger.error("Application initialization failed", e);
        }
    }
}