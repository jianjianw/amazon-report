package com.nhsoft.module.azure.timer;

import com.nhsoft.module.azure.model.Branch;
import com.nhsoft.module.azure.model.BranchDaily;
import com.nhsoft.module.azure.model.ItemDaily;
import com.nhsoft.module.azure.model.ItemDailyDetail;
import com.nhsoft.module.azure.service.AzureService;
import com.nhsoft.module.report.rpc.BranchRpc;
import com.nhsoft.module.report.rpc.PosOrderRpc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class AzureSchedule {

    @Autowired
    private PosOrderRpc posOrderRpc;
    @Autowired
    private AzureService azureService;
    @Autowired
    private BranchRpc branchRpc;


    @Scheduled(cron="0 */30 * * * *")
    public void BranchDailyMinute(){     //分店销售汇总(每30分钟执行一次)  Scheduled(cron="0 */30 * * * *")
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String systemBookCode = "4410";
        List<BranchDaily> branchDailySummary = posOrderRpc.findBranchDailySummary(systemBookCode, date, date);
        azureService.insertBranchDaily(systemBookCode,branchDailySummary);
    }


    @Scheduled(cron="0 0 2-4 * * *")
    public void BranchDailyHour(){     //分店销售汇总(每天凌晨2点执行,更新前三天的数据)
        Calendar calendar = Calendar.getInstance();
        Date dateTo = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH,-3);
        Date dateFrom = calendar.getTime();
        String systemBookCode = "4410";
        List<BranchDaily> branchDailySummary = posOrderRpc.findBranchDailySummary(systemBookCode, dateFrom, dateTo);
        azureService.insertBranchDaily(systemBookCode,branchDailySummary);
    }

    @Scheduled(cron="0 0,30 * * * *")
    public void insertItem(){                 //从凌晨开始，每个小时的0分和30分执行一次 (商品日汇总)
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String systemBookCode = "4410";
        List<ItemDaily> itemDailySummary = posOrderRpc.findItemDailySummary(systemBookCode, date, date);
        azureService.insertItemDaily(systemBookCode,itemDailySummary);
    }



    @Scheduled(cron="0 0,30 * * * *")
    public void itemDailyDetailMinute(){        //商品日时段销售汇总(从凌晨开始，每个小时的0分和30分执行一次)
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String systemBookCode = "4410";
        List<ItemDailyDetail> itemDailyDetailSummary = posOrderRpc.findItemDailyDetailSummary(systemBookCode, date, date);
        azureService.insertItemDailyDetail(systemBookCode,itemDailyDetailSummary);
    }

    @Scheduled(cron="0 0 2-3 * * *")
    public void itemDailyDetailHour(){        //商品日时段销售汇总(从凌晨开始，每个小时的0分和30分执行一次)
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        Date date = calendar.getTime();
        String systemBookCode = "4410";
        List<ItemDailyDetail> itemDailyDetailSummary = posOrderRpc.findItemDailyDetailSummary(systemBookCode, date, date);
        azureService.insertItemDailyDetail(systemBookCode,itemDailyDetailSummary);
    }


    @Scheduled(cron="0 0 2-3 * * *")
    public void insertBranch(){                 //每天凌晨2店-4点 每个小时执行一次
        String systemBookCode = "4410";
        List<Branch> branch = branchRpc.findBranch(systemBookCode);
        azureService.insertBranch(systemBookCode,branch);
    }

    @Scheduled(cron = "0 0 2-3 * * *")
    public void deleteBranchDaily(){
        String systemBookCode = "4410";
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        azureService.deleteBranchDaily(systemBookCode,date,date);
    }

    @Scheduled(cron = "0 0 2-3 * * *")
    public void deleteItemDetailDaily(){//7
        String systemBookCode = "4410";
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-7);
        Date date = calendar.getTime();
        azureService.deleteItemDetailDaily(systemBookCode,date,date);
    }



}
