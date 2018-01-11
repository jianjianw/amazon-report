package com.nhsoft.module.report.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Configuration
public class RedisUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(RedisUtil.class);
	
	private static Map<String, RedisTemplate> customRedises = new HashMap<String, RedisTemplate>();
	private static Map<String, String> redisNameMap = new HashMap<String, String>();
	
	private static String redisNameMapStr;
	
	@Value("${redis.name.map}")
	public void setRedisNameMapStr(String redisNameMapStr) {
		RedisUtil.redisNameMapStr = redisNameMapStr;
	}
	
	public static void setCustomRedises(Map customRedises) {
		RedisUtil.customRedises = customRedises;
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
	
	public static void test(){
		String key = "test_hash";
//		customRedises.get(DynamicDataSourceContextHolder.getDataSourceType()).opsForList().leftPush("test_list", 1);
//		customRedises.get(DynamicDataSourceContextHolder.getDataSourceType()).opsForList().leftPush("test_list", 2);
//		customRedises.get(DynamicDataSourceContextHolder.getDataSourceType()).opsForList().leftPush("test_list", 3);
//		customRedises.get(DynamicDataSourceContextHolder.getDataSourceType()).opsForList().leftPush("test_list", 4);
		//operations.append(" 你好");
//		customRedises.get(DynamicDataSourceContextHolder.getDataSourceType()).opsForHash().put(key, "age", Double.valueOf(2.00));
		//customRedises.get(DynamicDataSourceContextHolder.getDataSourceType()).opsForHyperLogLog().
//		System.out.println(customRedises.get(DynamicDataSourceContextHolder.getDataSourceType()).opsForHash().size(key));
		
//		Branch branch = new Branch();
//		branch.setId(new BranchId("4020", 1));
//		branch.setBranchName("1店");
//		customRedises.get(DynamicDataSourceContextHolder.getDataSourceType()).opsForHash().put(key, 1, branch);
//		 q
//		branch = new Branch();
//		branch.setId(new BranchId("4020", 2));
//		branch.setBranchName("2店");
//		customRedises.get(DynamicDataSourceContextHolder.getDataSourceType()).opsForHash().put(key, 2, branch);

		
		
	}
	
}
