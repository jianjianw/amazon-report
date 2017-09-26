package com.nhsoft.report.monitor;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Event;
import com.dianping.cat.message.Transaction;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class RpcMonitor {
    @Around(value="execution(public * com.nhsoft.report.rpc..*(..))")
    public void test(ProceedingJoinPoint joinPoint){
        String pageName = "";
        String serverIp = "";
        String method="";
        double amount = 0;
        Transaction t = Cat.newTransaction("URL", pageName);
        try {
            Cat.logEvent("URL.Server",serverIp, Event.SUCCESS,"ip="+serverIp+"&...");
            Cat.logEvent("SQL.Method",method);
            Cat.logMetricForCount("Count");
            Cat.logMetricForSum("sum",amount);
            joinPoint.proceed();
            t.setStatus(Transaction.SUCCESS);
        } catch (Throwable throwable) {
            t.setStatus(throwable);
        } finally {
            t.complete();
        }

    }
}
