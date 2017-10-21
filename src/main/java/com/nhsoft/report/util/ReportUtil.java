package com.nhsoft.report.util;


import com.nhsoft.module.report.query.ReportInfo;
import com.nhsoft.module.report.query.ReportKey;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ReportUtil<T> {
	
	private Map<String, T> map = new HashMap<String, T>();
	private Class<T> cls;
	private List<Field> keyFields = new ArrayList<Field>();
	private List<Field> infoFields = new ArrayList<Field>();
	private List<Field> dataFields = new ArrayList<Field>();
	private T pt = null;
	
	public ReportUtil(Class<T> cls) {
		this.cls = cls;
        Field[] fields = cls.getDeclaredFields();
        for(Field field : fields) {
        	field.setAccessible(true);
        	ReportKey reportKey = field.getAnnotation(ReportKey.class);
        	if(reportKey != null) {
        		keyFields.add(field);
        		continue;
        	}
        	ReportInfo reportInfo = field.getAnnotation(ReportInfo.class);
        	if(reportInfo != null) {
        		infoFields.add(field);
        		continue;
        	}
        	dataFields.add(field);
        }
	}
	
	public void add(T t) {
		try {
			if(t == null) {
				return;
			}
			if(keyFields.size() == 0) {
				map.put(map.size()+"", t);
				pt = null;
				return;
			}
			String keyStr = createKeyStr(t);
			T persistent = map.get(keyStr);
			if(persistent == null) {
				map.put(keyStr, t);
				pt = null;
			} else {
				Field[] fields = t.getClass().getDeclaredFields();
				for(Field field : fields) {
					if(field.getName().equals("serialVersionUID")) {
						continue;
					}
					if(keyFields.contains(field)) {
						continue;
					}
					field.setAccessible(true);
					Object newData = field.get(t);
					if(newData == null) {
						continue;
					}
					Object oldData = field.get(persistent);
					if(oldData == null || infoFields.contains(field)) {
						field.set(persistent, newData);
					}
					else if(oldData instanceof BigDecimal) {
						field.set(persistent, ((BigDecimal)oldData).add((BigDecimal)newData));
					}
					else if(oldData instanceof Integer) {
						field.set(persistent, (Integer)oldData+(Integer)newData);
					}
					else if(oldData instanceof Long) {
						field.set(persistent, (Long)oldData+(Long)newData);
					}
					else if(oldData instanceof Float) {
						field.set(persistent, (Float)oldData+(Float)newData);
					}
					else if(oldData instanceof Double) {
						field.set(persistent, (Double)oldData+(Double)newData);
					}
					else if(oldData instanceof String) {
						field.set(persistent, (String)oldData+","+(String)newData);
					}
					else {
						field.set(persistent, newData);
					}
					field.set(t, null);
				}
			}
		} catch(Exception e) {
			
		}
	}
	
	public T getInstance() {
		try {
			if(pt == null) {
				return cls.newInstance();
			} else {
				return pt;
			}
		} catch (Exception e) {
			return null;
		}
	}
	
	public void add(Map<String, Object> map) {
		try {
			T t = cls.newInstance();
			for(Field field : cls.getDeclaredFields()) {
				Object object = map.get(field.getName());
				Class<?> fieldType = field.getType();
				if(object != null && fieldType.isInstance(object)) {
					field.set(t, object);
				}
			}
			add(t);
		} catch (Exception e) {
			
		}
	}
	
	public List<T> toList() {
		return new ArrayList<T>(map.values());
	}
	
	private String createKeyStr(T t) throws Exception {
		try {
			List<String> keyStrs = new ArrayList<String>();
			for(Field field : keyFields) {
				Object keyAttribute = field.get(t);
				if(keyAttribute != null) {
					keyStrs.add(keyAttribute.toString());
				} else {
					keyStrs.add("");
				}
			}
			return String.join("_", keyStrs);
		} catch(Exception e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}
}
