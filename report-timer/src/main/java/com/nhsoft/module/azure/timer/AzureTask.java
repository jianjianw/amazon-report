package com.nhsoft.module.azure.timer;

import com.nhsoft.module.azure.service.YeShiBIService;
import com.nhsoft.module.report.dto.azure.BranchDaily;
import com.nhsoft.module.report.rpc.PosOrderRpc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;
@Component
public class AzureTask extends TimerTask {
    @Autowired
    private PosOrderRpc posOrderRpc;
    @Autowired
    private YeShiBIService yeShiBIService;
    public void run() {
        Calendar calendar = Calendar.getInstance();
        Date time = calendar.getTime();
        System.out.println("task运行了");
        List<BranchDaily> branchDailySummary = posOrderRpc.findBranchDailySummary("", time, time);
        yeShiBIService.insertBranchDaily(branchDailySummary);
    }
}
