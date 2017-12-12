package com.nhsoft.module.report;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import java.util.HashMap;
import java.util.Map;

@Configuration
@PropertySource({"classpath:azure.properties"})
public class AzureMultipleDateSourceConfig implements EnvironmentAware {

    private static final Logger logger = LoggerFactory.getLogger(AzureMultipleDateSourceConfig.class);
    private Map customDataSources = new HashMap<String, DruidDataSource>();

    private Map hibernateProperties = new HashMap();

    public static class AzureMultipleDataSource extends AbstractRoutingDataSource {

        @Override
        protected Object determineCurrentLookupKey() {
            return DynamicDataSourceContextHolder.getDataSourceType();
        }

    }

    @Override
    public void setEnvironment(Environment environment) {

        //多数据源
        RelaxedPropertyResolver propertyResolver = new RelaxedPropertyResolver(environment, "datasource.");
        String dsPrefixs = propertyResolver.getProperty("names");
        for (String dsPrefix : dsPrefixs.split(",")) {// 多个数据源
            Map<String, Object> dsMap = propertyResolver.getSubProperties(dsPrefix + ".");
            DruidDataSource ds = buildDruidDataSource(dsMap);
            customDataSources.put(dsPrefix, ds);
            logger.info("完成初始化数据源:" + dsPrefix);

        }
        propertyResolver = new RelaxedPropertyResolver(environment, "hibernate.");
        hibernateProperties = propertyResolver.getSubProperties("");

    }


    public DruidDataSource buildDruidDataSource(Map<String, Object> dsMap) {

        String driverClassName = dsMap.get("driver-class-name").toString();
        String url = dsMap.get("url").toString();
        String username = dsMap.get("username").toString();
        String password = dsMap.get("password").toString();

        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(url);
        druidDataSource.setDriverClassName(driverClassName);
        druidDataSource.setUsername(username);
        druidDataSource.setPassword(password);

        if(dsMap.containsKey("initialSize")){
            druidDataSource.setInitialSize(Integer.parseInt(dsMap.get("initialSize").toString()));

        }
        if(dsMap.containsKey("minIdle")){
            druidDataSource.setMinIdle(Integer.parseInt(dsMap.get("minIdle").toString()));

        }
        if(dsMap.containsKey("maxActive")){
            druidDataSource.setMaxActive(Integer.parseInt(dsMap.get("maxActive").toString()));

        }
        druidDataSource.setTimeBetweenEvictionRunsMillis(600000);
        druidDataSource.setMinEvictableIdleTimeMillis(600000);
        druidDataSource.setValidationQuery("SELECT 'x'");
        druidDataSource.setPoolPreparedStatements(true);
        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(20);
        druidDataSource.setTestOnBorrow(false);
        druidDataSource.setTestOnReturn(false);
        druidDataSource.setTestWhileIdle(true);
        druidDataSource.setRemoveAbandoned(true);
        druidDataSource.setRemoveAbandonedTimeout(3600);
        return druidDataSource;
    }

    @Bean(name = "azureSessionFactory")
    public LocalSessionFactoryBean sessionFactory(){

        logger.info("开始初始化azureSessionFactory");
        AzureMultipleDateSourceConfig.AzureMultipleDataSource multipleDataSource = new AzureMultipleDateSourceConfig.AzureMultipleDataSource();
        multipleDataSource.setTargetDataSources(customDataSources);
        multipleDataSource.setDefaultTargetDataSource(customDataSources.get("4344"));
        multipleDataSource.afterPropertiesSet();

        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(multipleDataSource);
        sessionFactory.getHibernateProperties().putAll(hibernateProperties);
        sessionFactory.setPackagesToScan("com.nhsoft.module.azure.model");
        sessionFactory.setPhysicalNamingStrategy(new SpringPhysicalNamingStrategy());
        return sessionFactory;

    }

    @Bean(name = "azureTransactionManager")
    public HibernateTransactionManager azureTransactionManager() {
        HibernateTransactionManager transactionManager =
                new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }

}
