package com.nhsoft.module.azure.timer;

import com.nhsoft.module.azure.model.BranchDaily;
import com.nhsoft.module.azure.service.AzureService;
import com.nhsoft.module.report.rpc.PosOrderRpc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;
@Component
public class AzureTask extends TimerTask {


    @Autowired
    private PosOrderRpc posOrderRpc;
    @Autowired
    private AzureService yeShiBIService;
    public void run() {
        SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");
        Date dateFrom = null;
        Date daetTo = null;
        try {
            dateFrom = sdf.parse("2017-6-01");
            daetTo = sdf.parse("2017-11-30");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        Date time = calendar.getTime();
        System.out.println("task运行了");
        List<BranchDaily> branchDailySummary =  posOrderRpc.findBranchDailySummary("4344", dateFrom, daetTo);
        yeShiBIService.insertBranchDaily("4344",branchDailySummary);
    }
}
