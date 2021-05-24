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

import static com.learn.learningproject.Constant.*;

/*
created by Rahul sawaria on 17/05/21
*/
@Configuration
public class DbConfig {

    @Autowired
    private Environment environment;

    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(
                environment.getRequiredProperty(JDBC_URL),
                environment.getRequiredProperty(JDBC_USERNAME),
                environment.getRequiredProperty(JDBC_PASSWORD));
        dataSource.setDriverClassName(environment.getRequiredProperty(JDBC_DRIVER));
        return dataSource;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put(HIBERNATE_DIALECT, environment.getRequiredProperty(HIBERNATE_DIALECT));
        properties.put(HIBERNATE_SHOW_SQL, "false");
        properties.put(HIBERNATE_DDL_AUTO, "none");
        properties.put(HIBERNATE_FORMAT_SQL, Boolean.TRUE);
        properties.put(HIBERNATE_COORDINATOR_CLASS, "jdbc");
        properties.put(HIBERNATE_CONNECTION_RELEASE_MODE, "after_statement");
        properties.put(HIBERNATE_SECOND_LEVEL_CACHE, Boolean.FALSE);
        properties.put(HIBERNATE_QUERY_CACHE, Boolean.FALSE);
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
