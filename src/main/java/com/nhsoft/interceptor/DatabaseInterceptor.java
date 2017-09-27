package com.nhsoft.interceptor;

import com.nhsoft.DynamicDataSourceContextHolder;
import com.nhsoft.report.dto.SystemBookProxy;
import com.nhsoft.report.util.DateUtil;
import com.nhsoft.report.util.ServiceDeskUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by yangqin on 2017/8/26.
 */
@Aspect
@Component
//顺序不能设太高 dubbo的service会失效
@Order(100)
public class DatabaseInterceptor {
	
	private static final Logger logger = LoggerFactory.getLogger(DatabaseInterceptor.class);
	
//	@Pointcut("execution(* com.nhsoft.report.service.*.*(..))")
//	public void service() {
//	}
	
	@Pointcut("execution(* com.nhsoft.report.rpc.*.*(..))")
	public void rpc() {
	}
	
	@Before("rpc()")
	public void doBefore(JoinPoint jp){
		
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
	
	private void doFieldsValue(Object paramObject, Field[] fields, StringBuffer queryBuffer) throws Exception{
		for(int j = 0;j < fields.length;j++){
			Field field = fields[j];
			field.setAccessible(true);
			if(field.getName().equals("serialVersionUID")){
				continue;
			}
			Object value = field.get(paramObject);
			if(value == null){
				continue;
			}
			if (value instanceof String
					|| value instanceof BigDecimal
					|| value instanceof Integer) {
				value = value.toString();
			} else if(value instanceof List){
				value = "list size = " + ((List)value).size();
			} else if(value instanceof Date){
				value = DateUtil.getDateShortStr((Date)value);
			}
			queryBuffer.append("&").append(field.getName()).append("=").append(value.toString());
		}
	}
	
	
	private String createCondition(ProceedingJoinPoint jp){
		try {
			StringBuilder condition = new StringBuilder();
			Object[] objects = jp.getArgs();
			for (int i = 0; i < objects.length; i++) {
				Object paramObject = objects[i];
				if(paramObject == null){
					continue;
				}
				if(i > 0){
					condition.append(" : ");
					
				}
				if (paramObject instanceof String
						|| paramObject instanceof BigDecimal
						|| paramObject instanceof Integer) {
					condition.append(paramObject.toString());
				} else if(paramObject instanceof List){
					condition.append("list size = " + ((List)paramObject).size());
				} else if(paramObject instanceof Date){
					condition.append(DateUtil.getDateShortStr((Date)paramObject));
				} else {
					Class c = paramObject.getClass();
					if(c.getName().startsWith("com.nhsoft.pos3.shared.queryBuilder")){
						Field[] fields = null;
						StringBuffer queryBuffer = new StringBuffer();
						Class superClass = c.getSuperclass();
						if(superClass != null){
							fields = superClass.getDeclaredFields();
							doFieldsValue(paramObject, fields, queryBuffer);
							
						}
						fields = c.getDeclaredFields();
						doFieldsValue(paramObject, fields, queryBuffer);
						condition.append(queryBuffer.toString());
						
					} else {
						condition.append(paramObject.toString());
						
					}
					
				}
			}
			return condition.toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "";
		}
	}
	
//	@Around("rpc()")
//	public Object around(ProceedingJoinPoint jp)throws Throwable{
//
//		String name = jp.getTarget().getClass().getName() + "." + jp.getSignature().getName();
//		Long begin = System.currentTimeMillis();
//
//		Transaction t = Cat.newTransaction("RPC", name);
//		Integer size = null;
//		try {
//			Object object = jp.proceed(jp.getArgs());
//			if (object instanceof List) {
//				size = ((List) object).size();
//			}
//			t.setStatus(Transaction.SUCCESS);
//			return object;
//		}catch (ServiceBizException | APIException e) {
//			t.setStatus(Transaction.SUCCESS);
//			throw  e;
//		} catch (Throwable throwable) {
//			t.setStatus(Transaction.SUCCESS);
//			t.setStatus(throwable);
//			throw throwable;
//		} finally {
//			Long end = System.currentTimeMillis();
//			int diff = (int) ((end - begin) / 1000);
//			if(diff > 10){
//				Cat.logEvent("SLOW RPC", name, Event.SUCCESS, createCondition(jp));
//
//			}
//			if(size != null && size > 10000){
//				Cat.logEvent("BIG RPC", name, Event.SUCCESS, createCondition(jp));
//
//			}
//			t.complete();
//		}
//	}
	
	
}
