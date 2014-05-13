/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package be.wolkmaan.klimtoren.web.config;

import java.util.Properties;
import javax.sql.DataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * @author karl
 */
@Configuration
@EnableTransactionManagement
@PropertySource({"classpath:hibernate.properties"})
public class PersistenceConfig {
    @Autowired
    Environment env;
    
    private static final String DIALECT = "hibernate.dialect";
    private static final String HBM2DDL = "hibernate.hbm2ddl.auto";
    private static final String IMPORT_FILES = "hibernate.hbm2ddl.import_files";
    private static final String CACHE_REGION = "hibernate.cache.region.factory_class";
    private static final String CACHE_USE_SECOND_LEVEL = "hibernate.cache.use_second_level_cache";
    private static final String CACHE_USE_QUERY_CACHE = "hibernate.cache.use_query_cache";
    
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName(env.getProperty("jdbc.driverClassName"));
        ds.setUrl(env.getProperty("jdbc.url"));
        ds.setUsername(env.getProperty("jdbc.username"));
        ds.setPassword(env.getProperty("jdbc.password"));
        return ds;
    }
    
    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sf = new LocalSessionFactoryBean();
        sf.setDataSource(dataSource());
        sf.setPackagesToScan(new String[] {"be.wolkmaan.klimtoren"}); //TODO: specify more
        sf.setHibernateProperties(hibernateProperties());
        return sf;
    }
    
    @Bean
    public HibernateTransactionManager transactionManager() {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory().getObject());
        return txManager;
    }

    private Properties hibernateProperties() {
        Properties p = new Properties();
        return new Properties() {
            {
                setProperty(DIALECT, env.getProperty(DIALECT));
                setProperty(HBM2DDL, env.getProperty(HBM2DDL));
                setProperty(IMPORT_FILES, env.getProperty(IMPORT_FILES));
                //TODO CACHE!
//                setProperty(CACHE_REGION, env.getProperty(CACHE_REGION));
//                setProperty(CACHE_USE_SECOND_LEVEL, env.getProperty(CACHE_USE_SECOND_LEVEL));
//                setProperty(CACHE_USE_QUERY_CACHE, env.getProperty(CACHE_USE_QUERY_CACHE));
            }  
        };
    }
}
