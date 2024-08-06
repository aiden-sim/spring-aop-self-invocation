package com.example.demo.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(basePackages = {"com.example.demo.repository"})
public class RepositoryConfiguration {

    @Bean
    @Primary
    public DataSource dataSource() {
        return getDataSource();
    }

    private DataSource getDataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder
                .setType(EmbeddedDatabaseType.H2)
                .setName("testdb;NON_KEYWORDS=KEY,VALUE;MODE=MySQL")
                .build();
    }

    Properties hibernateProperties() {
        return new Properties() {
            {
                setProperty("hibernate.hbm2ddl.auto", "create-drop");
                setProperty("hibernate.show_sql", "true");
                setProperty("hibernate.format_sql", "true");
                setProperty("hibernate.use_sql_comments", "true");
                setProperty("hibernate.id.new_generator_mapping", "true");
                setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
                setProperty("hibernate.physical_naming_strategy", "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy");
            }
        };
    }

    @Primary
    @Bean
    public EntityManagerFactory entityManagerFactory() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("com.example.demo.domain");
        factory.setDataSource(dataSource());
        factory.setJpaProperties(hibernateProperties());
        factory.setPersistenceUnitName("core");
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    @Primary
    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory());
        txManager.setPersistenceUnitName("core");
        return txManager;
    }
}
