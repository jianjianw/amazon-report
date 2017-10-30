package com.nhsoft.module.report.interceptor;

import com.dangdang.ddframe.rdb.sharding.api.HintManager;
import com.dianping.cat.Cat;
import com.dianping.cat.message.Event;
import com.dianping.cat.message.Transaction;
import com.nhsoft.module.report.DynamicDataSourceContextHolder;
import com.nhsoft.module.report.dto.APIException;
import com.nhsoft.module.report.dto.SystemBookProxy;
import com.nhsoft.module.report.shared.ServiceBizException;
import com.nhsoft.module.report.util.DateUtil;
import com.nhsoft.module.report.util.ServiceDeskUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangqin on 2017/8/26.
 */
@Aspect
@Configuration
//顺序不能设太高 dubbo的service会失效
@Order(-1000)
public class DatabaseInterceptor {
	
	private static final Logger logger = LoggerFactory.getLogger(DatabaseInterceptor.class);
	
	@Value("${rds.name.map}")
	private String rdsNameMapStr;
	private Map<String, String> rdsNameMap = new HashMap<String, String>();
	
	@Pointcut("execution(* com.nhsoft.module.report.rpc.*.*(..))")
	public void rpc() {
	}
	
	public synchronized  void init(){
		String[] array = rdsNameMapStr.split(",");
		for(int i = 0;i < array.length;i++){
			String[] subArray = array[i].split(":");
			rdsNameMap.put(subArray[0], subArray[1]);
		}
	}
	
	
	@Before("rpc()")
	public void doBefore(JoinPoint jp){
		if(rdsNameMap.isEmpty()){
			init();
		}
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
		String rds = rdsNameMap.get(systemBookProxy.getBookProxyName());
		if(rds == null){
			throw new RuntimeException("rds not found");
		}
		DynamicDataSourceContextHolder.setDataSourceType(rds);
		String name = jp.getTarget().getClass().getName() + "." + jp.getSignature().getName();
		
		logger.info(String.format("systemBookCode = %s database = %s name = %s", systemBookCode, rds, name));
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
	
	@Around("rpc()")
	public Object around(ProceedingJoinPoint jp)throws Throwable{

		String name = jp.getTarget().getClass().getName() + "." + jp.getSignature().getName();
		Long begin = System.currentTimeMillis();

		Transaction t = Cat.newTransaction("RPC", name);
		Integer size = null;
		try {
			Object object = jp.proceed(jp.getArgs());
			if (object instanceof List) {
				size = ((List) object).size();
			}
			t.setStatus(Transaction.SUCCESS);
			return object;
		}catch (ServiceBizException | APIException e) {
			t.setStatus(Transaction.SUCCESS);
			throw  e;
		} catch (Throwable throwable) {
			t.setStatus(Transaction.SUCCESS);
			t.setStatus(throwable);
			logger.error(throwable.getMessage(), throwable);
			throw throwable;
		} finally {
			Long end = System.currentTimeMillis();
			int diff = (int) ((end - begin) / 1000);
			if(diff > 10){
				logger.warn(String.format("接口[%s]耗时%d秒", name, diff));
				Cat.logEvent("SLOW RPC", name, Event.SUCCESS, createCondition(jp));

			}
			if(size != null && size > 10000){
				logger.warn(String.format("接口[%s]返回数据%d条", name, size));
				Cat.logEvent("BIG RPC", name, Event.SUCCESS, createCondition(jp));

			}
			t.complete();
		}
	}
	
	
}
