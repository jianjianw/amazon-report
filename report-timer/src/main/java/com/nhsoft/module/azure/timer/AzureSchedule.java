package com.nhsoft.module.azure.timer;

import com.nhsoft.module.azure.model.*;
import com.nhsoft.module.azure.service.AzureService;
import com.nhsoft.module.report.rpc.BranchRpc;
import com.nhsoft.module.report.rpc.PosOrderRpc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class AzureSchedule {

    private static final Logger logger = LoggerFactory.getLogger(AzureSchedule.class);

    @Autowired
    private PosOrderRpc posOrderRpc;
    @Autowired
    private AzureService azureService;
    @Autowired
    private BranchRpc branchRpc;

    @Scheduled(cron="0 */30 * * * *")
    public void saveBranchDailyMinute(){     //分店销售汇总(每30分钟执行一次)  Scheduled(cron="0 */30 * * * *")
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String systemBookCode = "4410";
        List<BranchDaily> branchDailySummary = posOrderRpc.findBranchDailySummary(systemBookCode, date, date);
        azureService.batchSaveBranchDailies(systemBookCode,branchDailySummary);
    }

    @Scheduled(cron="0 0 2-4 * * *")
    public void saveBranchDailyHour(){     //分店销售汇总(每天凌晨2点执行,更新前七天的数据)
        Calendar calendar = Calendar.getInstance();
        Date dateTo = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH,-7);
        Date dateFrom = calendar.getTime();
        String systemBookCode = "4410";
        List<BranchDaily> branchDailySummary = posOrderRpc.findBranchDailySummary(systemBookCode, dateFrom, dateTo);
        azureService.batchSaveBranchDailies(systemBookCode,branchDailySummary);
    }




    @Scheduled(cron="* */30 * * * *")       //每30分钟更新一次当天的额数据
    public void saveBranchDailyDirectMinute(){
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String systemBookCode = "4410";
        List<BranchDailyDirect> branchDailyDirectSummary = posOrderRpc.findBranchDailyDirectSummary(systemBookCode, date, date);
        azureService.batchSaveBranchDailyDirects(systemBookCode,branchDailyDirectSummary);
    }

    @Scheduled(cron="0 0 2-4 * * *")        //每天凌晨更新前2天的数据
    public void saveBranchDailyDirectHour(){
        Calendar calendar = Calendar.getInstance();
        Date dateTo = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH,-2);
        Date dateFrom = calendar.getTime();
        String systemBookCode = "4410";
        List<BranchDailyDirect> branchDailyDirectSummary = posOrderRpc.findBranchDailyDirectSummary(systemBookCode, dateFrom, dateTo);
        azureService.batchSaveBranchDailyDirects(systemBookCode,branchDailyDirectSummary);
    }
    @Scheduled(cron="0 0 2-3 * * *")
    public void deleteBranchDailyDirect(){      //每天凌晨删除2天以前的数据
        String systemBookCode = "4410";
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-2);
        Date date = calendar.getTime();
        azureService.batchDeleteBranchDailyDirects(systemBookCode,date,date);
    }




    @Scheduled(cron="0 0,30 * * * *")
    public void saveItemDailyDetailMinute(){        //商品日时段销售汇总(从凌晨开始，每个小时的0分和30分执行一次)
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String systemBookCode = "4410";
        List<Integer> posItemNums = azureService.findPosItemNums(systemBookCode);
        List<ItemDailyDetail> itemDailyDetailSummary = posOrderRpc.findItemDailyDetailSummary(systemBookCode, date, date,posItemNums);
        azureService.batchSaveItemDailyDetails(systemBookCode,itemDailyDetailSummary);
    }

    @Scheduled(cron="0 0 2-3 * * *")
    public void saveItemDailyDetailHour(){        //商品日时段销售汇总(汇总昨天的数据)
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        Date date = calendar.getTime();
        String systemBookCode = "4410";
        //在商品维度表里面查询商品编码
        List<Integer> posItemNums = azureService.findPosItemNums(systemBookCode);
        List<ItemDailyDetail> itemDailyDetailSummary = posOrderRpc.findItemDailyDetailSummary(systemBookCode, date, date,posItemNums);
        azureService.batchSaveItemDailyDetails(systemBookCode,itemDailyDetailSummary);
    }

    @Scheduled(cron = "0 0 2-3 * * *")
    public void deleteItemDetailDaily(){        //删除2天前的数据（保存两天的数据）
        String systemBookCode = "4410";
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-2);
        Date date = calendar.getTime();
        azureService.batchDeleteItemDetailDailies(systemBookCode,date,date);
    }




    @Scheduled(cron="0 0 2-3 * * *")
    public void insertBranch(){                 //每天凌晨2店-4点 每个小时执行一次
        String systemBookCode = "4410";
        List<Branch> branch = branchRpc.findBranch(systemBookCode);
        azureService.batchSaveBranchs(systemBookCode,branch);
    }





}
