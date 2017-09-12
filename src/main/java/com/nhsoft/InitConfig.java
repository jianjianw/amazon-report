package com.nhsoft;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by yangqin on 2017/9/5.
 */

@Configuration
public class InitConfig {
	
	private static final Logger logger = LoggerFactory.getLogger(InitConfig.class);
	
	
	private String serviceDeskUrl;
	
	@Value("${redis.host}")
	private String REDIS_HOST_NAME;
	
	@Value("${redis.pass}")
	private  String REDIS_PASS;
	
	@Bean
	public RestTemplate restTemplate() {
		
		HttpComponentsClientHttpRequestFactory factory =  new HttpComponentsClientHttpRequestFactory();
		factory.setConnectTimeout(3000);
		factory.setReadTimeout(10000);
		RestTemplate restTemplate = new RestTemplate(factory);
		return restTemplate;
	}
	
	@Bean
	public RedisTemplate redisTemplate() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMinIdle(10);
		jedisPoolConfig.setMaxIdle(200);
		jedisPoolConfig.setMaxTotal(600);
		jedisPoolConfig.setTestOnBorrow(true);
		
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
		jedisConnectionFactory.setHostName(REDIS_HOST_NAME);
		jedisConnectionFactory.setPort(6379);
		jedisConnectionFactory.setPassword(REDIS_PASS);
		jedisConnectionFactory.setPoolConfig(jedisPoolConfig);
		jedisConnectionFactory.setTimeout(60000);
		
		RedisTemplate redisTemplate = new RedisTemplate();
		redisTemplate.setConnectionFactory(jedisConnectionFactory);
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new MyJackson2JsonRedisSerializer());
		return redisTemplate;
	}
	
	
	
}
