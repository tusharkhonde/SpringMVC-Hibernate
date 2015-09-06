package multitenant.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;

import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;


/**
 * Created by TUSHAR_SK on 8/1/15.
 */

@Configuration
@EnableTransactionManagement
public class DatabaseConfig {

    // ------------------------
        // PUBLIC METHODS
        // ------------------------

        /**
         * DataSource definition for database connection. Settings are read from
         * the application.properties file (using the env object).
         */
        @Bean
        @Primary
        public DataSource dataSource() throws SQLException{
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName(env.getProperty("db.driver"));
            dataSource.setUrl(env.getProperty("db.url"));
            dataSource.setUsername(env.getProperty("db.username"));
            dataSource.setPassword(env.getProperty("db.password"));
            return dataSource;
        }

        /**
         * Declare the JPA entity manager factory.
         */
        @Bean
        @Primary
        public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws SQLException{
            LocalContainerEntityManagerFactoryBean entityManagerFactory =
                    new LocalContainerEntityManagerFactoryBean();

            entityManagerFactory.setDataSource(dataSource());

            // Classpath scanning of @Component, @Service, etc annotated class
            entityManagerFactory.setPackagesToScan(
                    env.getProperty("entitymanager.packagesToScan"));

            // Vendor adapter
            HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
            entityManagerFactory.setJpaVendorAdapter(vendorAdapter);

            // Hibernate properties
            Properties additionalProperties = new Properties();
            additionalProperties.put(
                    "hibernate.dialect",
                    env.getProperty("hibernate.dialect"));
            additionalProperties.put(
                    "hibernate.show_sql",
                    env.getProperty("hibernate.show_sql"));
            additionalProperties.put(
                    "hibernate.hbm2ddl.auto",
                    env.getProperty("hibernate.hbm2ddl.auto"));
            entityManagerFactory.setJpaProperties(additionalProperties);

            return entityManagerFactory;
        }

        /**
         * Declare the transaction manager.
         */
        @Bean
        @Primary
        public JpaTransactionManager transactionManager() throws SQLException {
            JpaTransactionManager transactionManager =
                    new JpaTransactionManager();
            transactionManager.setEntityManagerFactory(entityManagerFactory.getObject());
            return transactionManager;
        }


        /**
         * PersistenceExceptionTranslationPostProcessor is a bean post processor
         * which adds an advisor to any bean annotated with Repository so that any
         * platform-specific exceptions are caught and then rethrown as one
         * Spring's unchecked data access exceptions (i.e. a subclass of
         * DataAccessException).
         */
        @Bean
        @Primary
        public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
            return new PersistenceExceptionTranslationPostProcessor();
        }

        // ------------------------
        // PRIVATE FIELDS
        // ------------------------

        @Autowired
        private Environment env;

        @Autowired
        private LocalContainerEntityManagerFactoryBean entityManagerFactory;

} // class DatabaseConfig
