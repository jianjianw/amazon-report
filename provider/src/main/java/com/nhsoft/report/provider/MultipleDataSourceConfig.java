package com.nhsoft.report.provider;

import com.alibaba.druid.pool.DruidDataSource;
import com.dangdang.ddframe.rdb.sharding.api.ShardingDataSourceFactory;
import com.dangdang.ddframe.rdb.sharding.api.rule.DataSourceRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.ShardingRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.TableRule;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.DatabaseShardingStrategy;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.NoneDatabaseShardingAlgorithm;
import com.nhsoft.amazon.server.remote.service.PosOrderRemoteService;
import com.nhsoft.report.utils.DynamicDataSourceContextHolder;
import com.nhsoft.report.provider.sharding.AlipayLogSharding;
import com.nhsoft.report.provider.sharding.PosItemLogSharding;
import com.nhsoft.report.provider.sharding.PosOrderSharding;
import com.nhsoft.report.utils.MemCacheUtil;
import com.nhsoft.report.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;
import redis.clients.jedis.JedisPoolConfig;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yangqin on 2017/8/26.
 */
@Configuration
@PropertySource({"classpath:sharding.properties"})
public class MultipleDataSourceConfig implements EnvironmentAware {
	private static final Logger logger = LoggerFactory.getLogger(MultipleDataSourceConfig.class);
	
	private Map customDataSources = new HashMap<String, DruidDataSource>();
	
	private Map hibernateProperties = new HashMap();

	@Value("${dpc.url}")
	private String DPC_URL;
	@Value("${sharding.pos_order.book_codes}")
	private String posOrderShardingBooks;
	private String defaultSourceName = null;

	public static class MultipleDataSource extends AbstractRoutingDataSource {
		
		@Override
		protected Object determineCurrentLookupKey() {
			return DynamicDataSourceContextHolder.getDataSourceType();
		}
	}
	
	@Override
	public void setEnvironment(Environment environment) {
		//多数据源
		RelaxedPropertyResolver propertyResolver = new RelaxedPropertyResolver(environment, "custom.datasource.");
		String dsPrefixs = propertyResolver.getProperty("names");
		for (String dsPrefix : dsPrefixs.split(",")) {// 多个数据源
			Map<String, Object> dsMap = propertyResolver.getSubProperties(dsPrefix + ".");
			DruidDataSource ds = buildDruidDataSource(dsMap);
			customDataSources.put(dsPrefix, ds);
			if(defaultSourceName == null){
				defaultSourceName = dsPrefix;
			}
			logger.info("完成初始化数据源:" + dsPrefix);

		}
		//多REDIS
		propertyResolver = new RelaxedPropertyResolver(environment, "custom.redis.");
		dsPrefixs = propertyResolver.getProperty("names");
		Map customRedises = new HashMap<String, RedisTemplate>();
		Map customRedises2 = new HashMap<String, RedisTemplate>();
		for (String dsPrefix : dsPrefixs.split(",")) {// 多个数据源
			Map<String, Object> dsMap = propertyResolver.getSubProperties(dsPrefix + ".");
			RedisTemplate ds = buildRedisTemplate(dsMap);
			customRedises.put(dsPrefix, ds);
			RedisTemplate redisTemplate2 = buildRedisTemplate2(dsMap);
			customRedises2.put(dsPrefix,redisTemplate2);
			logger.info("完成初始化REDIS:" + dsPrefix);
			
		}
		RedisUtil.setCustomRedises(customRedises);
		MemCacheUtil.setCustomRedises(customRedises2);
		propertyResolver = new RelaxedPropertyResolver(environment, "hibernate.");
		hibernateProperties = propertyResolver.getSubProperties("");
		
	}


	private RedisTemplate buildRedisTemplate(Map<String, Object> dsMap) {


		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMinIdle(10);
		jedisPoolConfig.setMaxIdle(200);
		jedisPoolConfig.setMaxTotal(600);
		jedisPoolConfig.setTestOnBorrow(true);

		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
		jedisConnectionFactory.setHostName(dsMap.get("redis.host").toString());
		jedisConnectionFactory.setPort(6379);
		jedisConnectionFactory.setPassword(dsMap.get("redis.pass").toString());
		jedisConnectionFactory.setPoolConfig(jedisPoolConfig);
		jedisConnectionFactory.setTimeout(60000);
		jedisConnectionFactory.setDatabase(0);
		jedisConnectionFactory.afterPropertiesSet();		//Cannot get Jedis connection
		
		RedisTemplate redisTemplate = new RedisTemplate();
		redisTemplate.setConnectionFactory(jedisConnectionFactory);
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new MyJackson2JsonRedisSerializer());
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}


	private RedisTemplate buildRedisTemplate2(Map<String, Object> dsMap) {



		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMinIdle(10);
		jedisPoolConfig.setMaxIdle(30);
		jedisPoolConfig.setMaxTotal(100);
		jedisPoolConfig.setTestOnBorrow(true);

		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
		jedisConnectionFactory.setHostName(dsMap.get("redis.host").toString());
		jedisConnectionFactory.setPort(6379);
		jedisConnectionFactory.setPassword(dsMap.get("redis.pass").toString());
		jedisConnectionFactory.setPoolConfig(jedisPoolConfig);
		jedisConnectionFactory.setTimeout(60000);
		jedisConnectionFactory.setDatabase(13);
		jedisConnectionFactory.afterPropertiesSet();		//Cannot get Jedis connection

		RedisTemplate redisTemplate = new RedisTemplate();
		redisTemplate.setConnectionFactory(jedisConnectionFactory);
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new MyJackson2JsonRedisSerializer());
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}

	@Bean(name = "sessionFactory")
	public LocalSessionFactoryBean sessionFactory(){
		
		logger.info("开始初始化sessionFactory");
		MultipleDataSource multipleDataSource = new MultipleDataSource();
		multipleDataSource.setTargetDataSources(customDataSources);
		multipleDataSource.setDefaultTargetDataSource(customDataSources.get(defaultSourceName));
		multipleDataSource.afterPropertiesSet();

		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(multipleDataSource);
		sessionFactory.getHibernateProperties().putAll(hibernateProperties);
		sessionFactory.setPackagesToScan("com.nhsoft.module.report.model");
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
	
	
	@Bean(name = "shardingDataSource")
	public DataSource shardingDataSource() {
		
		DataSourceRule dataSourceRule = new DataSourceRule(customDataSources, defaultSourceName);
		
		TableRule alipayLogTableRule = AlipayLogSharding.createTableRule(dataSourceRule);
		TableRule posItemLogTableRule = PosItemLogSharding.createTableRule(dataSourceRule);
		TableRule posOrderTableRule = PosOrderSharding.createTableRule(dataSourceRule, posOrderShardingBooks);
		TableRule paymentTableRule = PosOrderSharding.createPaymentTableRule(dataSourceRule, posOrderShardingBooks);
		TableRule posOrderDetailTableRule = PosOrderSharding.createPosOrderDetailTableRule(dataSourceRule, posOrderShardingBooks);
		TableRule posOrderKitDetailTableRule = PosOrderSharding.createPosOrderKitDetailTableRule(dataSourceRule, posOrderShardingBooks);
		
		ShardingRule shardingRule = ShardingRule.builder()
				.dataSourceRule(dataSourceRule)
				.tableRules(Arrays.asList(alipayLogTableRule, posItemLogTableRule
						,posOrderTableRule, paymentTableRule, posOrderDetailTableRule, posOrderKitDetailTableRule
				))
				.databaseShardingStrategy(new DatabaseShardingStrategy("none", new NoneDatabaseShardingAlgorithm()))
				.build();


		DataSource dataSource = null;
		try {
			dataSource = ShardingDataSourceFactory.createDataSource(shardingRule);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			logger.error("创建SharingDateSource失败");
		}
		return dataSource;
	}
	
	@Bean(name = "shardingSessionFactory")
	public LocalSessionFactoryBean shardingSessionFactory(){
		
		logger.info("开始初始化shardingSessionFactory");
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(shardingDataSource());
		sessionFactory.getHibernateProperties().putAll(hibernateProperties);
		sessionFactory.setPackagesToScan("com.nhsoft.module.report.model");
		sessionFactory.setPhysicalNamingStrategy(new SpringPhysicalNamingStrategy());
		return sessionFactory;
		
	}

	@Bean
	public HttpInvokerProxyFactoryBean posOrderRemoteService() {
		HttpInvokerProxyFactoryBean httpInvokerProxyFactoryBean = new HttpInvokerProxyFactoryBean();
		httpInvokerProxyFactoryBean.setServiceUrl(DPC_URL+"/posOrderRemoteService");
		httpInvokerProxyFactoryBean.setServiceInterface(PosOrderRemoteService.class);
		return httpInvokerProxyFactoryBean;
	}

	@Bean
	public HibernateTransactionManager transactionManager() {
		HibernateTransactionManager transactionManager =
				new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory().getObject());
		return transactionManager;
	}
}
