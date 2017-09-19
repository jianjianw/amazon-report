package com.nhsoft.report.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class RedisUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(RedisUtil.class);

	public static RedisTemplate<String, Object> redisTemplate;
	public static RedisTemplate<String, Object> cardRedisTemplate;
	private static boolean redisValid;

	public static boolean isRedisValid() {
		return redisValid;
	}
	
	public void setRedisValid(boolean redisValid) {
		RedisUtil.redisValid = redisValid;
	}

	public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
		RedisUtil.redisTemplate = redisTemplate;
	}
	
	public static void setCardRedisTemplate(RedisTemplate<String, Object> cardRedisTemplate) {
		RedisUtil.cardRedisTemplate = cardRedisTemplate;
	}

	public static void put(String key, Object value){
		if(redisValid){
			try {
				redisTemplate.opsForValue().set(key, value);
				
			} catch (Exception e) {
				logger.warn(e.getMessage(), e);
				
			}
			
		}
	}
	
	public static void put(String key, Object value, int expireTime){
		if(redisValid){
			try {
				redisTemplate.opsForValue().set(key, value, expireTime, TimeUnit.SECONDS);
				
			} catch (Exception e) {
				logger.warn(e.getMessage(), e);
				
			}
			
		}
	}
	
	public static void put(String key, Object value, Date expireTime){
		if(redisValid){
			try {
				redisTemplate.opsForValue().set(key, value);
				redisTemplate.expireAt(key, expireTime);
			} catch (Exception e) {
				logger.warn(e.getMessage(), e);
				
			}
			
		}
	}
	
	public static void setPut(String key, Object value){
		if(redisValid){
			try {
				
				redisTemplate.opsForSet().add(key, value);
				
			} catch (Exception e) {
				logger.warn(e.getMessage(), e);
				
			}
			
		}
	}
	
	public static void setPut(String key, Object value, int expireDays){
		if(redisValid){
			try {
				redisTemplate.opsForSet().add(key, value);
				redisTemplate.expire(key, expireDays, TimeUnit.DAYS);
			} catch(Exception e) {
				logger.warn(e.getMessage(), e);
			}
		}
	}
	
	public static void setPutList(String key, List values){
		if(values.size() == 0){
			return;
		}
		if(redisValid){
			try {
				Object[] objects = values.toArray(new Object[values.size()]);
				redisTemplate.opsForSet().add(key, objects);
			} catch(Exception e) {
				logger.warn(e.getMessage(), e);
			}
		}
	}
	
	public static void setPutList(String key, List values, int expireDays){
		if(values.size() == 0){
			return;
		}
		if(redisValid){
			try {
				Object[] objects = values.toArray(new Object[values.size()]);
				redisTemplate.opsForSet().add(key, objects);
				redisTemplate.expire(key, expireDays, TimeUnit.DAYS);
			} catch(Exception e) {
				logger.warn(e.getMessage(), e);
			}
		}
	}
	
	public static void setRemove(String key, Object value){
		if(redisValid){
			try {
				
				redisTemplate.opsForSet().remove(key, value);
				
			} catch (Exception e) {
				logger.warn(e.getMessage(), e);
				
			}
			
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
		if(redisValid){
			try {
				redisTemplate.delete(key);
				
			} catch (Exception e) {
				logger.warn(e.getMessage(), e);
			}
			
		}
	}
	
	public static void hashPut(String key, Object hashKey, Object value){
		if(redisValid){
			try {
				
				redisTemplate.opsForHash().put(key, hashKey, value);
			
			} catch (Exception e) {
				logger.warn(e.getMessage(), e);
				
			}
			
		}
	}
	
	public static Set findHashKeys(String key){
		if(redisValid){
			try {
				
				return redisTemplate.opsForHash().keys(key);
			
			} catch (Exception e) {
				logger.warn(e.getMessage(), e);
				return null;
				
			}
			
		}
		return null;
	}
	
	public static Map hashGetMap(String key){
		if(redisValid){
			try {

				return redisTemplate.opsForHash().entries(key);
				
			} catch (Exception e) {
				logger.warn(e.getMessage(), e);
				
			}
			
		}
		return null;
	}
	
	public static Object hashGet(String key, Object hashKey){
		if(redisValid){
			try {

				return redisTemplate.opsForHash().get(key, hashKey);
				
			} catch (Exception e) {
				logger.warn(e.getMessage(), e);
				
			}
			
		}
		return null;
	}
	
	public static void hashDelete(String key, Object hashKey){
		if(redisValid){
			try {

				redisTemplate.opsForHash().delete(key, hashKey);
				
			} catch (Exception e) {
				logger.warn(e.getMessage(), e);
				
			}
			
		}
	}
	
	public static void putCardDb(String key, Object value, Date expireTime){
		if(redisValid){
			try {
				cardRedisTemplate.opsForValue().set(key, value);
				cardRedisTemplate.expireAt(key, expireTime);
			} catch (Exception e) {
				logger.warn(e.getMessage(), e);
				
			}
			
		}
	}
	
	public static void deleteCardDb(String key){
		if(redisValid){
			try {
				cardRedisTemplate.delete(key);;
				
			} catch (Exception e) {
				logger.warn(e.getMessage(), e);
			}
			
		}
	}
	
	public static Object getCardDb(String key){
		
		try {
			
			return cardRedisTemplate.opsForValue().get(key);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			return null;
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
//		redisTemplate.opsForList().leftPush("test_list", 1);
//		redisTemplate.opsForList().leftPush("test_list", 2);
//		redisTemplate.opsForList().leftPush("test_list", 3);
//		redisTemplate.opsForList().leftPush("test_list", 4);
		//operations.append(" 你好");
//		redisTemplate.opsForHash().put(key, "age", Double.valueOf(2.00));
		//redisTemplate.opsForHyperLogLog().
//		System.out.println(redisTemplate.opsForHash().size(key));
		
//		Branch branch = new Branch();
//		branch.setId(new BranchId("4020", 1));
//		branch.setBranchName("1店");
//		redisTemplate.opsForHash().put(key, 1, branch);
//		 q
//		branch = new Branch();
//		branch.setId(new BranchId("4020", 2));
//		branch.setBranchName("2店");
//		redisTemplate.opsForHash().put(key, 2, branch);

		
		
	}
	
}
