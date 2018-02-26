package com.nhsoft.report.provider.interceptor;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Aspect
@Configuration
public class ApiInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(ApiInterceptor.class);


    @Pointcut("execution(* com.nhsoft.module.report.dao.*.*(..))")
    public void dao(){

    }

    @Around(value="ApiInterceptor.dao()")
    public Object printDaoLog(ProceedingJoinPoint point) throws Throwable  {

        String name = null;
        Object object;
        long diff = 0;
        long preTime = 0;
        Integer size = 0;
        try {
            name = point.getTarget().getClass().getName() + "." + point.getSignature().getName();
            preTime = System.currentTimeMillis();

            object = point.proceed(point.getArgs());

            if (object instanceof List) {
                size = ((List) object).size();
            }
            return object;
        } catch (Throwable throwable) {
            logger.error(throwable.getMessage(),throwable);
            throw throwable;
        } finally {
            long afterTime = System.currentTimeMillis();
            diff = (afterTime - preTime)/1000;
            if(diff > 10){
                logger.warn(String.format("接口[%s]耗时%d秒", name, diff));
            }
            if(size != null && size>10000){
                logger.warn(String.format("接口[%s]返回数据%d条", name, size));
            }

        }
    }


}
