package com.learn.learningproject.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

/*
created by Rahul sawaria on 17/05/21
*/
@Configuration
public class DbConfig {

    @Autowired
    private Environment environment;

    public static final String PROPERTY_SHOW_SQL = "hibernate.show_sql";
    public static final String PROPERTY_DIALECT = "hibernate.dialect";
    public static final String PROPERTY_AUTO = "hibernate.hbm2ddl.auto";
    public static final String PROPERTY_FORMAT_SQL = "hibernate.format_sql";
    public static final String PROPERTY_CONNECTION_RELEASE_MODE = "hibernate.connection.release_mode";
    public static final String PROPERTY_COORDINATOR_CLASS = "hibernate.transaction.coordinator_class";
    public static final String PROPERTY_CACHE = "hibernate.cache.use_second_level_cache";
    public static final String PROPERTY_QUERY_CACHE = "hibernate.cache.use_query_cache";


    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(
                environment.getRequiredProperty("jdbc.url"),
                environment.getRequiredProperty("jdbc.username"),
                environment.getRequiredProperty("jdbc.password"));
        dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
        return dataSource;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put(PROPERTY_DIALECT, environment.getRequiredProperty("hibernate.dialect"));
        properties.put(PROPERTY_SHOW_SQL, "false");
        properties.put(PROPERTY_AUTO, "none");
        properties.put(PROPERTY_FORMAT_SQL, Boolean.TRUE);
        properties.put(PROPERTY_COORDINATOR_CLASS, "jdbc");
        properties.put(PROPERTY_CONNECTION_RELEASE_MODE, "after_statement");
        properties.put(PROPERTY_CACHE, Boolean.FALSE);
        properties.put(PROPERTY_QUERY_CACHE, Boolean.FALSE);
        return properties;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean factory = new LocalSessionFactoryBean();
        factory.setDataSource(getDataSource());
        factory.setHibernateProperties(hibernateProperties());
        factory.setPackagesToScan("com.learn.learningproject");
        return factory;
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory factory) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(factory);
        return transactionManager;
    }
}
