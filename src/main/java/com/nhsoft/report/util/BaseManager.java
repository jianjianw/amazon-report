package com.nhsoft.report.util;


import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseManager {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	private Cache cache;
	
	public void setCache(Cache cache) {
		this.cache = cache;
	}

	protected Element getElementFromCache(String key) {
		return cache.get(key);
	}
	
	protected void putElementToCache(Element element) {
		cache.put(element);
	}
	
	protected boolean removeElementFromCache(Element element) {
		return cache.removeElement(element);
	}

	protected boolean removeElementFromCache(String key) {		
		return cache.remove(key);
	}
	
	protected void removeAll() {		
		cache.removeAll();
	}
	
}
