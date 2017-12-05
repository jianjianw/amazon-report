package com.nhsoft.module.report.interceptor;


import com.nhsoft.module.report.DynamicDataSourceContextHolder;
import com.nhsoft.module.report.dto.SystemBookProxy;
import com.nhsoft.module.report.query.QueryBuilder;
import com.nhsoft.module.report.util.ServiceDeskUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Configuration
@Order(-1000)
public class AzureDateBaseInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(AzureDateBaseInterceptor.class);

    @Value("${db.name.map}")
    private String rdsNameMapStr;
    private Map<String, String> rdsNameMap = new HashMap<String, String>();

    @Pointcut("execution(* com.nhsoft.module.azure.service.*.*(..))")
    public void service() {
    }

    public synchronized  void init(){
        String[] array = rdsNameMapStr.split(",");
        for(int i = 0;i < array.length;i++){
            String[] subArray = array[i].split(":");
            rdsNameMap.put(subArray[0], subArray[1]);
        }
    }

    @Before("service()")
    public void doBefore(JoinPoint jp){
        if(rdsNameMap.isEmpty()){
            init();
        }
        Object[] objects = jp.getArgs();
        if(objects.length == 0){
            throw new RuntimeException("systemBookCode not found");
        }
        Object object = objects[0];
        String systemBookCode;
        if(object instanceof QueryBuilder){
            systemBookCode = ((QueryBuilder)object).getSystemBookCode();
        } else {
            if(!(object instanceof  String)){
                throw new RuntimeException("systemBookCode not found");
            }
            systemBookCode = (String) objects[0];

        }
        SystemBookProxy systemBookProxy = ServiceDeskUtil.getSystemBookProxy(systemBookCode);
        if(systemBookProxy == null){
            logger.info("systemBookProxy == null " + systemBookCode);

        }
        String rds = rdsNameMap.get(systemBookProxy.getBookProxyName());
        if(rds == null){
            throw new RuntimeException("rds not found");
        }
        DynamicDataSourceContextHolder.setDataSourceType(rds);
        String name = jp.getTarget().getClass().getName() + "." + jp.getSignature().getName();
        logger.info(String.format("systemBookCode = %s database = %s name = %s", systemBookCode, rds, name));
    }
}
