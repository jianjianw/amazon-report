package com.nhsoft.report.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class RedisUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(RedisUtil.class);

	@Autowired
	public static RedisTemplate<String, Object> redisTemplate;
	
	public static void put(String key, Object value){
		
		try {
			redisTemplate.opsForValue().set(key, value);
			
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			
		}
			
		
	}
	
	public static void put(String key, Object value, int expireTime){
	
		try {
			redisTemplate.opsForValue().set(key, value, expireTime, TimeUnit.SECONDS);
			
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			
		}
			
		
	}
	
	public static void put(String key, Object value, Date expireTime){
	
		try {
			redisTemplate.opsForValue().set(key, value);
			redisTemplate.expireAt(key, expireTime);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			
		}
			
		
	}
	
	public static void setPut(String key, Object value){
		
		try {
			
			redisTemplate.opsForSet().add(key, value);
			
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			
		}
			
		
	}
	
	public static void setPut(String key, Object value, int expireDays){
		
		try {
			redisTemplate.opsForSet().add(key, value);
			redisTemplate.expire(key, expireDays, TimeUnit.DAYS);
		} catch(Exception e) {
			logger.warn(e.getMessage(), e);
		}
		
	}
	
	public static void setPutList(String key, List values){
		if(values.isEmpty()){
			return;
		}
	
		try {
			Object[] objects = values.toArray(new Object[values.size()]);
			redisTemplate.opsForSet().add(key, objects);
		} catch(Exception e) {
			logger.warn(e.getMessage(), e);
		}
		
	}
	
	public static void setPutList(String key, List values, int expireDays){
		if(values.isEmpty()){
			return;
		}
		
		try {
			Object[] objects = values.toArray(new Object[values.size()]);
			redisTemplate.opsForSet().add(key, objects);
			redisTemplate.expire(key, expireDays, TimeUnit.DAYS);
		} catch(Exception e) {
			logger.warn(e.getMessage(), e);
		}
		
	}
	
	public static void setRemove(String key, Object value){
		
		try {
			
			redisTemplate.opsForSet().remove(key, value);
			
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			
		}
			
		
	}
	
	public static Set setGet(String key){
		
		try {
			return redisTemplate.opsForSet().members(key);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			return null;
		}
	}
	
	public static boolean setExist(String key, Object value){
		
		try {
			return redisTemplate.opsForSet().isMember(key, value);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			return false;
		}
	}

	public static boolean setIfAbsent(String key, Object value, int expireTime){

		try {
			boolean result = redisTemplate.opsForValue().setIfAbsent(key, value);
			if(result && expireTime != 0) {
				redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
			}
			return result;
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			return false;
		}
	}
	
	public static Object get(String key){
	
		try {
			
			return redisTemplate.opsForValue().get(key);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			return null;
		}
	}
	
	public static Long increment(String key, long value){
		try {
			return redisTemplate.opsForValue().increment(key, value);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			return null;
		}
	}
	
	public static void delete(String key){
		
		try {
			redisTemplate.delete(key);
			
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		}
		
		
	}
	
	public static void hashPut(String key, Object hashKey, Object value){
		
		try {
			
			redisTemplate.opsForHash().put(key, hashKey, value);
		
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			
		}
			
		
	}
	
	public static Set findHashKeys(String key){
		
		try {
			
			return redisTemplate.opsForHash().keys(key);
		
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			return null;
			
		}
	}
	
	public static Map hashGetMap(String key){
		
		try {

			return redisTemplate.opsForHash().entries(key);
			
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			return null;
		}

	}
	
	public static Object hashGet(String key, Object hashKey){
		
		try {

			return redisTemplate.opsForHash().get(key, hashKey);
			
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			return null;
		}

	}
	
	public static void hashDelete(String key, Object hashKey){
		
		try {

			redisTemplate.opsForHash().delete(key, hashKey);
			
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			
		}
			
		
	}
	
	public static boolean tryLock(String key) {
		return tryLock(key, 10);
	}
	
	public static boolean tryLock(String key, int second) {
		try {
			second *= 20;
			int currentSecond = 0;
			while(currentSecond <= second) {
				Boolean gotten = redisTemplate.opsForValue().setIfAbsent(key, "locked");
				if(gotten != null && gotten) {
					return true;
				}
				Thread.sleep(50);
				currentSecond++;
			}
			return false;
		} catch(Exception e) {
			return false;
		}
	}
	
	public static void releaseLock(String key) {
		redisTemplate.delete(key);
	}
	
	public static String getKey(String pre, String systemBookCode, Integer branchNum){
		return pre.concat(systemBookCode).concat("|").concat(branchNum.toString());
	}
	
	public static void test(){
		String key = "test_hash";

		
		
	}
	
}
