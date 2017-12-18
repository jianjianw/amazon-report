package com.nhsoft.module.azure.timer;

import com.nhsoft.module.azure.model.*;
import com.nhsoft.module.azure.service.AzureService;
import com.nhsoft.module.report.dto.BranchDTO;
import com.nhsoft.module.report.rpc.BranchRpc;
import com.nhsoft.module.report.rpc.PosOrderRpc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    String systemBook = "4410";

    @Scheduled(cron="0 */30 * * * *")
    public void saveBranchDailyMinute(){     //分店销售汇总(每30分钟执行一次)  Scheduled(cron="0 */30 * * * *")
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        List<BranchDaily> branchDailySummary = posOrderRpc.findBranchDailySummary(systemBook, date, date);
        azureService.batchSaveBranchDailies(systemBook,branchDailySummary,date,date);
    }

    @Scheduled(cron="0 0 2-4 * * *")////更新历史1
    public void saveBranchDailyHour(){     //分店销售汇总(每天凌晨2点执行,更新前七天的数据)
        Calendar calendar = Calendar.getInstance();
        Date dateTo = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH,-7);
        Date dateFrom = calendar.getTime();
        List<BranchDaily> branchDailySummary = posOrderRpc.findBranchDailySummary(systemBook, dateFrom, dateTo);
        azureService.batchSaveBranchDailies(systemBook,branchDailySummary,dateFrom,dateTo);
    }

    @Scheduled(cron="0 0,30 * * * *")
    public void test1(){
        logger.info("我是定时器1");
        System.out.println("我是定时器1");
    }

    @Scheduled(cron="0 0,30 * * * *")
    public void test2(){
        logger.info("我是定时器2");
        System.out.println("我是定时器2");
    }




    @Scheduled(cron="* */30 * * * *")       //每30分钟更新一次当天的额数据
    public void saveBranchDailyDirectMinute(){
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        List<BranchDaily> branchDailySummary = posOrderRpc.findBranchDailySummary(systemBook, date, date);
        List<BranchDailyDirect> list = new ArrayList<BranchDailyDirect>();
        for (int i = 0; i <branchDailySummary.size() ; i++) {
            BranchDaily branchDaily = branchDailySummary.get(i);
            BranchDailyDirect branchDailyDirect = new BranchDailyDirect();
            branchDailyDirect.setSystemBookCode(branchDaily.getSystemBookCode());
            branchDailyDirect.setBranchNum(branchDaily.getBranchNum());
            branchDailyDirect.setShiftTableBizday(branchDaily.getShiftTableBizday());
            branchDailyDirect.setDailyMoney(branchDaily.getDailyMoney());
            branchDailyDirect.setDailyQty(branchDaily.getDailyQty());
            branchDailyDirect.setShiftTableDate(branchDaily.getShiftTableDate());
            branchDailyDirect.setDailyPrice(branchDaily.getDailyPrice());
            branchDailyDirect.setTargetMoney(branchDaily.getTargetMoney());
            list.add(branchDailyDirect);
        }
        azureService.batchSaveBranchDailyDirects(systemBook,list,date,date);
    }

    @Scheduled(cron="0 0 2-3 * * *")     ////更新历史2
    public void saveBranchDailyDirectHour(){    //每天凌晨更新前2天的数据
        Calendar calendar = Calendar.getInstance();
        Date dateTo = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        Date dateFrom = calendar.getTime();
        List<BranchDaily> branchDailySummary = posOrderRpc.findBranchDailySummary(systemBook, dateFrom, dateTo);
        List<BranchDailyDirect> list = new ArrayList<BranchDailyDirect>();
        for (int i = 0; i <branchDailySummary.size() ; i++) {
            BranchDaily branchDaily = branchDailySummary.get(i);
            BranchDailyDirect branchDailyDirect = new BranchDailyDirect();
            branchDailyDirect.setSystemBookCode(branchDaily.getSystemBookCode());
            branchDailyDirect.setBranchNum(branchDaily.getBranchNum());
            branchDailyDirect.setShiftTableBizday(branchDaily.getShiftTableBizday());
            branchDailyDirect.setDailyMoney(branchDaily.getDailyMoney());
            branchDailyDirect.setDailyQty(branchDaily.getDailyQty());
            branchDailyDirect.setShiftTableDate(branchDaily.getShiftTableDate());
            branchDailyDirect.setDailyPrice(branchDaily.getDailyPrice());
            branchDailyDirect.setTargetMoney(branchDaily.getTargetMoney());
            list.add(branchDailyDirect);
        }
        azureService.batchSaveBranchDailyDirects(systemBook,list,dateFrom,dateTo);
    }
    @Scheduled(cron="0 0 2-3 * * *")
    public void deleteBranchDailyDirect(){      //保留两天数据
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        Date date = calendar.getTime();
        azureService.batchDeleteBranchDailyDirects(systemBook,date,date);
    }

    @Scheduled(cron="0 0,30 * * * *")
    public void saveItemDailyDetailMinute(){        //商品日时段销售汇总(从凌晨开始，每个小时的0分和30分执行一次)
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        List<Integer> posItemNums = azureService.findPosItemNums(systemBook);
        List<ItemDailyDetail> itemDailyDetailSummary = posOrderRpc.findItemDailyDetailSummary(systemBook, date, date,posItemNums);
        azureService.batchSaveItemDailyDetails(systemBook,itemDailyDetailSummary,date,date);
    }

    @Scheduled(cron="0 0 2-3 * * *")     ////更新历史3
    public void saveItemDailyDetailHour(){        //商品日时段销售汇总(汇总昨天的数据)
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        Date date = calendar.getTime();
        //在商品维度表里面查询商品编码
        List<Integer> posItemNums = azureService.findPosItemNums(systemBook);
        List<ItemDailyDetail> itemDailyDetailSummary = posOrderRpc.findItemDailyDetailSummary(systemBook, date, date,posItemNums);
        azureService.batchSaveItemDailyDetails(systemBook,itemDailyDetailSummary,date,date);
    }

    @Scheduled(cron = "0 0 2-3 * * *")
    public void deleteItemDetailDaily(){        //删除2天前的数据（保存两天的数据）
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        Date date = calendar.getTime();
        azureService.batchDeleteItemDetailDailies(systemBook,date,date);
    }


    @Scheduled(cron="0 0 2-3 * * *")
    public void insertBranch(){                 //每天凌晨2店-4点 每个小时执行一次
        List<BranchDTO> brachDTO = branchRpc.findInCache(systemBook);
        List<Branch> list = new ArrayList<Branch>();
        if (brachDTO.isEmpty()) {
            return;
        }
        for (int i = 0; i < brachDTO.size(); i++) {
            BranchDTO branchDTO = brachDTO.get(i);
            Branch branch = new Branch();
            branch.setBranchNum(branchDTO.getBranchNum());
            branch.setBranchCode(branchDTO.getBranchCode());
            branch.setBranchName(branchDTO.getBranchName());
            branch.setBranchActived(branchDTO.getBranchActived());
            branch.setBranchRdc(branchDTO.getBranchRdc());
            branch.setBranchType(branchDTO.getBranchType());
            branch.setBranchArea(branchDTO.getBranchArea());
            branch.setBranchEmployeeCount(branchDTO.getBranchEmployeeCount());
            branch.setBranchCreateTime(branchDTO.getBranchCreateTime());
            list.add(branch);
        }
        azureService.batchSaveBranchs(systemBook, list);
    }






}
