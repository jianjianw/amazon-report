package com.nhsoft.module.azure.timer;

import com.nhsoft.module.azure.model.Branch;
import com.nhsoft.module.azure.model.BranchDaily;
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
    public void BranchDailyMinute(){     //分店销售汇总(每半个小时执行一次)  Scheduled(cron="0 */30 * * * *")
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String systembookCode = "4410";
        List<BranchDaily> branchDailySummary = posOrderRpc.findBranchDailySummary(systembookCode, date, date);
        azureService.insertBranchDaily(systembookCode,branchDailySummary);
    }


    @Scheduled(cron="0 0 2-4 * * *")
    public void BranchDailyHour(){     //分店销售汇总(每天凌晨2点执行,更新前三天的数据)
        Calendar calendar = Calendar.getInstance();
        Date dateTo = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH,-3);
        Date dateFrom = calendar.getTime();
        String systembookCode = "4410";
        List<BranchDaily> branchDailySummary = posOrderRpc.findBranchDailySummary(systembookCode, dateFrom, dateTo);
        azureService.insertBranchDaily(systembookCode,branchDailySummary);
    }


    @Scheduled(cron="0 0,30 * * * *")
    public void itemDailyDetailMinute(){        //商品日时段销售汇总(从凌晨开始，每个小时的0分和30分执行一次)
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String systembookCode = "4410";
        List<ItemDailyDetail> itemDailyDetailSummary = posOrderRpc.findItemDailyDetailSummary(systembookCode, date, date);
        azureService.insertItemDailyDetail(systembookCode,itemDailyDetailSummary);
    }

    @Scheduled(cron="0 0 2-4 * * *")
    public void insertBranch(){                 //每天凌晨2店-4点 每个小时执行一次
        String systembookCode = "4410";
        List<Branch> branch = branchRpc.findBranch(systembookCode);
        azureService.insertBranch(systembookCode,branch);
    }

}
