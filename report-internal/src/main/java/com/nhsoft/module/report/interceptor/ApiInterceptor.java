package com.nhsoft.module.report.interceptor;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class ApiInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(ApiInterceptor.class);

    @Pointcut("execution(* com.nhsoft.module.report.api.*.*(..))")
    public void api() {
    }

    @Around(value="ApiInterceptor.api()")
    public Object printLog(ProceedingJoinPoint point) throws Throwable {

        String name = point.getTarget().getClass().getName() + "." + point.getSignature().getName();
        long preTime = System.currentTimeMillis();
        Object object = point.proceed(point.getArgs());
        long afterTime = System.currentTimeMillis();
        long diff = (afterTime - preTime) / 1000;
        logger.info("请求路径为：" + name + "----------- 耗时：" + diff);
        return object;

    }

}
