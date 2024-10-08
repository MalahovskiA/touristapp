package by.malahovski.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;


/**
 * Configuration class for setting up the database and Liquibase.
 * This class defines beans to configure the data source, JDBC template,
 * and Liquibase for database migration. The configuration values are injected
 * from external properties using the @Value annotation.
 */
@Configuration
public class DatabaseConfig {

    /**
     * The URL of the database to connect to.
     */
    @Value("${spring.datasource.url}")
    private String url;

    /**
     * The username for the database connection.
     */
    @Value("${spring.datasource.username}")
    private String username;

    /**
     * The password for the database connection.
     */
    @Value("${spring.datasource.password}")
    private String password;

    /**
     * The fully qualified name of the JDBC driver class.
     */
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    /**
     * The location of the Liquibase changelog file.
     */
    @Value("${liquibase.change-log}")
    private String changeLog;

    /**
     * Configures the data source bean.
     * This method configures a {@link DriverManagerDataSource} using the provided
     * database connection properties (URL, username, password, and driver class name).
     *
     * @return the configured {@link DataSource}
     */
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        return dataSource;
    }

    /**
     * Configures the JdbcTemplate bean.
     * This method sets up a {@link JdbcTemplate} that simplifies the use of JDBC
     * for database interactions by utilizing the configured {@link DataSource}.
     *
     * @return the configured {@link JdbcTemplate}
     */
    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    /**
     * Configures the Liquibase bean for database migration.
     * This method configures a {@link SpringLiquibase} instance that integrates Liquibase
     * for managing database migrations. It uses the same {@link DataSource} and applies
     * changes defined in the specified changelog file.
     *
     * @return the configured {@link SpringLiquibase}
     */
    @Bean
    public SpringLiquibase liquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource());
        liquibase.setChangeLog(changeLog);
        return liquibase;
    }
}