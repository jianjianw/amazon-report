package com.nhsoft.interceptor;

import com.nhsoft.DynamicDataSourceContextHolder;
import com.nhsoft.report.dto.SystemBookProxy;
import com.nhsoft.report.util.ServiceDeskUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by yangqin on 2017/8/26.
 */
@Aspect
@Component
//顺序不能设太高 dubbo的service会失效
@Order(100)
public class DatabaseInterceptor {
	
	private static final Logger logger = LoggerFactory.getLogger(DatabaseInterceptor.class);
	
	// 定义切点Pointcut
	@Pointcut("execution(* com.nhsoft.report.service.*.*(..))")
	public void before() {
	}
	
	@Before("before()")
	public void interceptor(JoinPoint jp){
		
		Object[] objects = jp.getArgs();
		if(objects.length == 0){
			throw new RuntimeException("systemBookCode not found");
		}
		Object object = objects[0];
		if(!(object instanceof  String)){
			throw new RuntimeException("systemBookCode not found");
		}
		String systemBookCode = (String) objects[0];
		SystemBookProxy systemBookProxy = ServiceDeskUtil.getSystemBookProxy(systemBookCode);
		if(systemBookProxy.getBookProxyPath().equals("pos3Server")){
			DynamicDataSourceContextHolder.setDataSourceType("ama");
			
		} else {
			DynamicDataSourceContextHolder.setDataSourceType("asn");
			
		}
		
		logger.info(String.format("systemBookCode = %s database = %s", systemBookCode, DynamicDataSourceContextHolder.getDataSourceType()));
	}
	
	
}
