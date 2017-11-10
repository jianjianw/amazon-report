package com.nhsoft.module.report.util;

import com.nhsoft.module.report.util.DateUtil;
import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;
import java.util.Date;

public class SpringCacheKeyGenerator implements KeyGenerator {

	@Override
	public Object generate(Object target, Method method, Object... params) {
        StringBuilder key = new StringBuilder();  
        key.append("AMA_").append(target.getClass().getSimpleName()).append(".").append(method.getName()).append(":");  
        if (params.length == 0) {  
            return key.toString();  
        } 
        int i = 1;
        String paramKey = null;
        for (Object param : params) {  
            if(param == null){
            	paramKey = "null";
            } else if(param instanceof Date){
            	paramKey = DateUtil.getDateShortStr((Date) param);
            } else {
            	paramKey = param.toString();
            }
            key.append("  ").append(i).append(":").append(paramKey);	
            i++;
        }		
		return key.toString();
	}

}
