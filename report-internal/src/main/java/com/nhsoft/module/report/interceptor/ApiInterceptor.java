package com.nhsoft.module.report.interceptor;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Configuration
public class ApiInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(ApiInterceptor.class);

    @Pointcut("execution(* com.nhsoft.module.report.api.*.*(..))")
    public void api() {
    }


    @Around(value="ApiInterceptor.api()")
    public void printLog(ProceedingJoinPoint point){

        ServletWebRequest servletContainer = (ServletWebRequest) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletContainer.getRequest();

        String url = request.getRequestURL().toString();
        long preTime = System.currentTimeMillis();
        try {
            Object proceed = point.proceed();
        } catch (Throwable throwable) {
            logger.error("拦截器异常");
        }

        long afterTime = System.currentTimeMillis();
        long diff = (afterTime - preTime)/1000;
        logger.info("请求路径为：" + url +"----------- 耗时："+diff);

    }

}
