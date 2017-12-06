package com.nhsoft.module.azure.listener;

import com.nhsoft.module.azure.model.ItemDailyDetail;
import com.nhsoft.module.azure.service.AzureService;
import com.nhsoft.module.report.rpc.PosOrderRpc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class ReportListener implements ApplicationListener<ContextRefreshedEvent> {


    @Autowired
    private PosOrderRpc posOrderRpc;
    @Autowired
    private AzureService azureService;

    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        System.out.println("所有bean初始化完成");
        SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");
        Date dateFrom = null;
        Date dateTo = null;
        try {
            dateFrom = sdf.parse("2016-01-01");
            dateTo = sdf.parse("2016-06-30");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        Date time = calendar.getTime();
      /*  //分店日销售汇总表
        List<BranchDaily> branchDailySummary =  posOrderRpc.findBranchDailySummary("4344", dateFrom, dateTo);
        azureService.insertBranchDaily("4344",branchDailySummary);*/


       /*//商品日销售汇总
        List<ItemDaily> itemDailySummary = posOrderRpc.findItemDailySummary("4344", dateFrom, dateTo);
        azureService.insertItemDaily("4344",itemDailySummary);*/



       //findItemDailyDetailSummary
        //商品日时段销售汇总
        List<ItemDailyDetail> itemDailyDetailSummary = posOrderRpc.findItemDailyDetailSummary("4344", dateFrom, dateTo);
        azureService.insertItemDailyDetail("4344",itemDailyDetailSummary);

       /* Timer timer = new Timer();
        timer.schedule(new AzureTask(),1000,1000*60*30);
        System.out.println("所有bean初始化完成");*/
        /*ThreadPoolTaskExecutor thread = new ThreadPoolTaskExecutor();
                thread.execute(new AzureTask());
                thread.shutdown();*/

    }
}
