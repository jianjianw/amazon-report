package com.nhsoft;

import com.alibaba.druid.pool.DruidDataSource;
import com.dangdang.ddframe.rdb.sharding.api.ShardingDataSourceFactory;
import com.dangdang.ddframe.rdb.sharding.api.rule.DataSourceRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.ShardingRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.TableRule;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.TableShardingStrategy;
import com.dangdang.ddframe.rdb.sharding.jdbc.core.datasource.ShardingDataSource;
import com.nhsoft.report.sharding.AlipayLogTableShardingAlgorithm;
import com.nhsoft.report.sharding.ShardingDateSourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yangqin on 2017/8/26.
 */
@Configuration
public class MultipleDataSourceConfig implements EnvironmentAware {
	private static final Logger logger = LoggerFactory.getLogger(MultipleDataSourceConfig.class);
	
	private Map customDataSources = new HashMap<String, DruidDataSource>();
	private Map hibernateProperties = new HashMap();
	
	public static class MultipleDataSource extends AbstractRoutingDataSource {
		
		@Override
		protected Object determineCurrentLookupKey() {
			return DynamicDataSourceContextHolder.getDataSourceType();
		}
	}
	
	@Override
	public void setEnvironment(Environment environment) {
		RelaxedPropertyResolver propertyResolver = new RelaxedPropertyResolver(environment, "custom.datasource.");
		String dsPrefixs = propertyResolver.getProperty("names");
		for (String dsPrefix : dsPrefixs.split(",")) {// 多个数据源
			Map<String, Object> dsMap = propertyResolver.getSubProperties(dsPrefix + ".");
			DruidDataSource ds = buildDruidDataSource(dsMap);
			customDataSources.put(dsPrefix, ds);
			logger.info("完成初始化数据源" + dsPrefix);
		}
		
		propertyResolver = new RelaxedPropertyResolver(environment, "hibernate.");
		hibernateProperties = propertyResolver.getSubProperties("");
		
	}
	
	@Bean(name = "sessionFactory")
	public LocalSessionFactoryBean sessionFactory(){
		logger.info("开始初始化sessionFactory");
		MultipleDataSource multipleDataSource = new MultipleDataSource();
		multipleDataSource.setTargetDataSources(customDataSources);
		multipleDataSource.setDefaultTargetDataSource(customDataSources.get("ama"));
		multipleDataSource.afterPropertiesSet();
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(multipleDataSource);
		sessionFactory.getHibernateProperties().putAll(hibernateProperties);
		sessionFactory.setPackagesToScan("com.nhsoft.report.model");
		sessionFactory.setPhysicalNamingStrategy(new SpringPhysicalNamingStrategy());
		return sessionFactory;
		
	}

	@Bean(name = "shardingSessionFactory")
	public LocalSessionFactoryBean shardingSessionFactory(){
		logger.info("开始初始化shardingSessionFactory");
		DataSource dataSource = ShardingDateSourceConfig.getDateSource(customDataSources);
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		sessionFactory.getHibernateProperties().putAll(hibernateProperties);
		sessionFactory.setPackagesToScan("com.nhsoft.report.model");
		sessionFactory.setPhysicalNamingStrategy(new SpringPhysicalNamingStrategy());
		return sessionFactory;
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
	
	@Bean
	public HibernateTransactionManager transactionManager() {
		HibernateTransactionManager transactionManager =
				new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory().getObject());
		return transactionManager;
	}
}
