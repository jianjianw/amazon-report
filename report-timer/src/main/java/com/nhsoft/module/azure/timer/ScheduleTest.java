package com.nhsoft.module.azure.timer;

import com.nhsoft.module.azure.model.BranchDaily;
import com.nhsoft.module.report.rpc.PosOrderRpc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class ScheduleTest {

    @Autowired
    private PosOrderRpc posOrderRpc;


    @Scheduled(cron="10 * * * * *")
    public void test(){
        SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");
        Date dateFrom = null;
        Date dateTo = null;
        try {
            dateFrom = sdf.parse("2017-06-01");
            dateTo = sdf.parse("2017-12-04");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        final String systembookCode = "4344";
        List<BranchDaily> branchDailySummary = posOrderRpc.findBranchDailySummary(systembookCode, dateFrom, dateTo);
        System.out.println(branchDailySummary.toString());
    }
}
