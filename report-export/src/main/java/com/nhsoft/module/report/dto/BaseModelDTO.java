package com.nhsoft.module.report.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class BaseModelDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6126013058816081651L;
	public Map<String, Object> map = new HashMap<String, Object>();
	
	public void set(String key, Object value){
		map.put(key, value);
	}
	
	public Object get(String key){
		return map.get(key);
	}

	public Map<String, Object> getMap() {
		return map;
	}
	
	
}
