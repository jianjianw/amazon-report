package com.nhsoft.module.azure.listener;

import com.nhsoft.module.azure.model.BranchDaily;
import com.nhsoft.module.azure.model.ItemDailyDetail;
import com.nhsoft.module.azure.model.PosItemLat;
import com.nhsoft.module.azure.service.AzureService;
import com.nhsoft.module.azure.timer.AzureTask;
import com.nhsoft.module.report.rpc.PosItemRpc;
import com.nhsoft.module.report.rpc.PosOrderRpc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class ReportListener implements ApplicationListener<ContextRefreshedEvent> {


    @Autowired
    private PosOrderRpc posOrderRpc;
    @Autowired
    private AzureService azureService;
    @Autowired
    private PosItemRpc posItemRpc;

    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        System.out.println("所有bean初始化完成");
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


        Timer timer = new Timer();
        //timer.schedule(new AzureTask(),1000);
       /* //每半个小时更新数据
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
               Calendar innerCalendar = Calendar.getInstance();
                Date date = innerCalendar.getTime();
                List<BranchDaily> branchDailySummary = posOrderRpc.findBranchDailySummary(systembookCode, date, date);
                //
                azureService.insertBranchDaily(systembookCode,branchDailySummary);
                System.out.println(branchDailySummary.toString());
            }
        },1000*60*5,1000*60*30);


        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,2);//凌晨2点
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.add(Calendar.DATE,1);
        Date date = calendar.getTime();
        //凌晨2点更新数据指定时间完成任务(含更新前3天的数据)
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Calendar innerCalendar = Calendar.getInstance();
                Date dateFrom = innerCalendar.getTime();
                innerCalendar.add(Calendar.DAY_OF_MONTH,-3);
                Date dateTo = innerCalendar.getTime();
                List<BranchDaily> branchDailySummary = posOrderRpc.findBranchDailySummary("4020", dateFrom, dateTo);
                azureService.insertBranchDaily(systembookCode,branchDailySummary);
            }
        },date);*/


      /* //每半个小时更新数据  (商品时段信息)
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Calendar innerCalendar = Calendar.getInstance();
                Date date = innerCalendar.getTime();
                List<ItemDailyDetail> itemDailyDetailSummary = posOrderRpc.findItemDailyDetailSummary(systembookCode, date, date);
                azureService.insertItemDailyDetail(systembookCode,itemDailyDetailSummary);
            }
        },1000*60*5,1000*60*30);*/





        /*//分店日销售汇总表
        List<BranchDaily> branchDailySummary =  posOrderRpc.findBranchDailySummary("4410", dateFrom, dateTo);
        azureService.insertBranchDaily("4410",branchDailySummary);*/


       //商品日销售汇总
        /*List<ItemDaily> itemDailySummary = posOrderRpc.findItemDailySummary("4344", dateFrom, dateTo);
        azureService.insertItemDaily("4344",itemDailySummary);
*/

       //findItemDailyDetailSummary
        //商品日时段销售汇总
        /*List<ItemDailyDetail> itemDailyDetailSummary = posOrderRpc.findItemDailyDetailSummary("4344", dateFrom, dateTo);
        azureService.insertItemDailyDetail("4344",itemDailyDetailSummary);*/


        /*//导入商品维度
        List<PosItemLat> itemLat = posItemRpc.findItemLat("4344");
        azureService.insertPosItemLat("4344",itemLat);*/

        /*ThreadPoolTaskExecutor thread = new ThreadPoolTaskExecutor();
                thread.execute(new AzureTask());
                thread.shutdown();*/

    }
}
