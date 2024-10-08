package by.malahovski.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;


/**
 * Configuration class for setting up JPA and Hibernate.
 * This class configures the JPA {@link EntityManagerFactory}, Hibernate properties,
 * and the transaction manager. It also enables JPA repositories in the specified package.
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "by.malahovski.repository")
public class JpaConfig {

    /**
     * The data source used for database connections.
     */
    private final DataSource dataSource;

    /**
     * Hibernate dialect, specifying the SQL dialect to be used by Hibernate.
     */
    @Value("${spring.jpa.properties.hibernate.dialect}")
    private String hibernateDialect;

    /**
     * Flag to show SQL queries in the logs.
     */
    @Value("${spring.jpa.show-sql}")
    private String showSql;

    /**
     * Hibernate property for specifying how schema generation is handled.
     */
    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String hbm2ddlAuto;

    /**
     * Constructor for injecting the data source.
     *
     * @param dataSource the {@link DataSource} used for connecting to the database
     */
    public JpaConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Configures the {@link EntityManagerFactory} bean.
     * This method sets up the JPA entity manager factory, which is responsible for managing
     * the persistence of entities. It configures the Hibernate vendor adapter and applies
     * JPA properties such as the SQL dialect, show SQL flag, and DDL auto settings.
     *
     * @return a configured {@link LocalContainerEntityManagerFactoryBean}
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("by.malahovski.model");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.dialect", hibernateDialect);
        jpaProperties.put("hibernate.show_sql", showSql);
        jpaProperties.put("hibernate.hbm2ddl.auto", hbm2ddlAuto);

        em.setJpaProperties(jpaProperties);

        return em;
    }

    /**
     * Configures the JPA transaction manager.
     * This method sets up the {@link JpaTransactionManager}, which is responsible for
     * managing transactions in JPA. It binds the transaction manager to the
     * {@link EntityManagerFactory}.
     *
     * @param entityManagerFactory the {@link EntityManagerFactory} to be managed
     * @return a configured {@link JpaTransactionManager}
     */
    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }
}