package com.nhsoft.module.report.util;

import net.spy.memcached.MemcachedClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class MemCacheUtil {
	private static final Logger logger = LoggerFactory.getLogger(MemCacheUtil.class);

	private static MemcachedClient memcachedClient;
	private static MemcachedClient xmemcachedClient;
	private static boolean memcachedValid;

	public void setMemcachedClient(MemcachedClient memcachedClient) {
		MemCacheUtil.memcachedClient = memcachedClient;
	}
	
	public void setMemcachedValid(boolean memcachedValid) {
		MemCacheUtil.memcachedValid = memcachedValid;
	}

	public static boolean isMemcachedValid() {
		return memcachedValid;
	}
	
	public void setXmemcachedClient(MemcachedClient xmemcachedClient) {
		MemCacheUtil.xmemcachedClient = xmemcachedClient;
	}
	
	public static void put(String key, int expireTime, Object value)  {
		if(memcachedValid && value != null){
			try {
				memcachedClient.set(key, expireTime, value);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			
		}
	
	}
	
	public static void put(String key, Date expireTime, Object value){
		if(memcachedValid && value != null){
			try {
				memcachedClient.set(key, (int)(expireTime.getTime()/1000), value);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			
		}
	
	}
	
	public static void add(String key, int value){
		if(value == 0){
			return;
		}		
		memcachedClient.incr(key, value);
		
	}
	
	public static Object get(String key){
		if(memcachedValid){
			try {
				return memcachedClient.get(key);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return null;		
	}
	
	public static boolean exists(String key){
		if(memcachedValid){
			try {
				
				Object object = memcachedClient.get(key);
				if(object != null){
					return true;
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return false;		
	}
	
	public static void delete(String key){
		if(memcachedValid){
			try {
				memcachedClient.delete(key);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}
	
	public static String getKey(String pre, String systemBookCode, Integer branchNum){
		return pre.concat(systemBookCode).concat("|").concat(branchNum.toString());
	}

}
