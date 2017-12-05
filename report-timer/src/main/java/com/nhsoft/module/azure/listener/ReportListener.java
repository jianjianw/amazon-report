package com.nhsoft.module.azure.listener;

import com.nhsoft.module.azure.service.YeShiBIService;
import com.nhsoft.module.report.dto.azure.BranchDaily;
import com.nhsoft.module.report.rpc.PosOrderRpc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class ReportListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private PosOrderRpc posOrderRpc;
    @Autowired
    private YeShiBIService yeShiBIService;


    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        Calendar calendar = Calendar.getInstance();
        Date time = calendar.getTime();
        List<BranchDaily> branchDailySummary = posOrderRpc.findBranchDailySummary("", time, time);
        yeShiBIService.insertBranchDaily(branchDailySummary);
    }
}
