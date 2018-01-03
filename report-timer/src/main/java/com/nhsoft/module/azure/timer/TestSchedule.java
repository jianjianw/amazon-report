package com.nhsoft.module.azure.timer;


import com.nhsoft.module.azure.model.BranchDaily;
import com.nhsoft.module.azure.service.AzureService;
import com.nhsoft.module.report.rpc.PosItemRpc;
import com.nhsoft.module.report.rpc.PosOrderRpc;
import org.hibernate.boot.model.source.spi.PluralAttributeElementSourceOneToMany;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledThreadPoolExecutor;

//@Component
public class TestSchedule implements InitializingBean {




    @Autowired
    private PosOrderRpc posOrderRpc;
    @Autowired
    private AzureService azureService;

    @Value("${datasource.names}")
    private String str;

    String[] systemBookCode;
    public void afterPropertiesSet() throws Exception {
        systemBookCode = str.split(",");
    }

    @Autowired
    private Executor taskExecutor;

    //@Scheduled(cron="0 * * * * *")
    public void saveBranchDailyMinute(){
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        int size = systemBookCode.length;
        for (int i = 0; i <size ; i++) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
            }

            List<BranchDaily> branchDailySummary = posOrderRpc.findBranchDailys(systemBookCode[i], date, date);
            azureService.batchSaveBranchDailies(systemBookCode[i],branchDailySummary,date,date);
        }


    }

    //@Scheduled(cron="0 * * * * *")
    public void test(){

        System.out.println("我执行了");

    }

    //@Scheduled(cron="0 * * * * *")
    public void test2(){
        int size = systemBookCode.length;
        for (int i = 0; i <size ; i++) {

            System.out.println(systemBookCode[i]);

        }


    }

}
