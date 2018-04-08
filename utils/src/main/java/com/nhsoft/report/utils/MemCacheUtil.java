package com.nhsoft.report.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class MemCacheUtil {
	private static final Logger logger = LoggerFactory.getLogger(MemCacheUtil.class);

	private static Map<String, RedisTemplate> customRedises = new HashMap<String, RedisTemplate>();
	private static Map<String, String> redisNameMap = new HashMap<String, String>();

	private static String redisNameMapStr;

	@Value("${redis.name.map}")
	public void setRedisNameMapStr(String redisNameMapStr) {
		MemCacheUtil.redisNameMapStr = redisNameMapStr;
	}

	public static void setCustomRedises(Map customRedises) {
		MemCacheUtil.customRedises = customRedises;
	}

	public static void initRedis(){
		if(redisNameMap.isEmpty()){
			init();
		}
	}


	public static synchronized  void init(){
		if(!redisNameMap.isEmpty()){
			return;
		}
		String[] array = redisNameMapStr.split(",");
		for(int i = 0;i < array.length;i++){
			String[] subArray = array[i].split(":");
			redisNameMap.put(subArray[0], subArray[1]);
		}
	}

	public static void put(String key, Object value){
		initRedis();
		try {
			customRedises.get(redisNameMap.get(DynamicDataSourceContextHolder.getDataSourceType())).opsForValue().set(key, value);

		} catch (Exception e) {
			logger.warn(e.getMessage(), e);

		}


	}

	public static void put(String key, Object value, int expireTime){
		initRedis();
		try {
			customRedises.get(redisNameMap.get(DynamicDataSourceContextHolder.getDataSourceType())).opsForValue().set(key, value, expireTime, TimeUnit.SECONDS);

		} catch (Exception e) {
			logger.warn(e.getMessage(), e);

		}


	}

	public static void put(String key, Object value, Date expireTime){
		initRedis();
		try {
			RedisTemplate redisTemplate = customRedises.get(redisNameMap.get(DynamicDataSourceContextHolder.getDataSourceType()));
			redisTemplate.opsForValue().set(key, value);
			redisTemplate.expireAt(key, expireTime);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);

		}


	}

	public static void setPut(String key, Object value){
		initRedis();
		try {

			customRedises.get(redisNameMap.get(DynamicDataSourceContextHolder.getDataSourceType())).opsForSet().add(key, value);

		} catch (Exception e) {
			logger.warn(e.getMessage(), e);

		}


	}

	public static void setPutList(String key, List values){
		if(values.size() == 0){
			return;
		}
		initRedis();
		try {
			Object[] objects = values.toArray(new Object[values.size()]);
			customRedises.get(redisNameMap.get(DynamicDataSourceContextHolder.getDataSourceType())).opsForSet().add(key, objects);
		} catch(Exception e) {
			logger.warn(e.getMessage(), e);
		}

	}

	public static void setRemove(String key, Object value){
		initRedis();
		try {

			customRedises.get(redisNameMap.get(DynamicDataSourceContextHolder.getDataSourceType())).opsForSet().remove(key, value);

		} catch (Exception e) {
			logger.warn(e.getMessage(), e);

		}


	}

	public static Set setGet(String key){
		initRedis();
		try {
			return customRedises.get(redisNameMap.get(DynamicDataSourceContextHolder.getDataSourceType())).opsForSet().members(key);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			return null;
		}
	}
	public static Object get(String key){
		initRedis();
		try {

			return customRedises.get(redisNameMap.get(DynamicDataSourceContextHolder.getDataSourceType())).opsForValue().get(key);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			return null;
		}
	}


	public static Object hashGet(String key, Object hashKey){
		initRedis();
		try {

			return customRedises.get(redisNameMap.get(DynamicDataSourceContextHolder.getDataSourceType())).opsForHash().get(key, hashKey);

		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			return null;
		}

	}

	public static void hashPut(String key,Object hashKey,Object value,long expireTime){
		initRedis();
		try {
			RedisTemplate redisTemplate = customRedises.get(redisNameMap.get(DynamicDataSourceContextHolder.getDataSourceType()));
			redisTemplate.opsForHash().put(key,hashKey,value);
			redisTemplate.expire(key,expireTime,TimeUnit.SECONDS);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);

		}
	}



}
