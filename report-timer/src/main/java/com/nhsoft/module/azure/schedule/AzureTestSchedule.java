package com.nhsoft.module.azure.schedule;

import com.nhsoft.module.azure.model.*;
import com.nhsoft.module.azure.service.AzureService;
import com.nhsoft.module.report.dto.*;
import com.nhsoft.module.report.rpc.*;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.*;

//@Component
public class AzureTestSchedule implements InitializingBean {


    @Autowired
    private PosOrderRpc posOrderRpc;
    @Autowired
    private AzureService azureService;
    @Autowired
    private BranchRpc branchRpc;
    @Autowired
    private AdjustmentOrderRpc adjustmentOrderRpc;
    @Autowired
    private PosItemRpc posItemRpc;
    @Autowired
    private ReportRpc reportRpc;

    @Value("${datasource.names}")
    private String str;

    String[] systemBookCode;

    public void afterPropertiesSet() throws Exception {
        systemBookCode = str.split(",");
        //移除测试账套12345
        systemBookCode = ArrayUtils.remove(systemBookCode, systemBookCode.length - 1);
    }


    @Scheduled(cron="0 0 2-3 * * *")////更新历史1
    public void saveBranchDailyHour(){     //分店销售汇总(每天凌晨2点执行,更新前七天的数据)
        Calendar calendar = Calendar.getInstance();
        Date dateTo = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH,-7);
        Date dateFrom = calendar.getTime();
        int size = systemBookCode.length;
        for (int i = 0; i < size ; i++) {
            List<BranchDaily> branchDailySummary = posOrderRpc.findBranchDailySummary(systemBookCode[i], dateFrom, dateTo);
            azureService.batchSaveBranchDailies(systemBookCode[i],branchDailySummary,dateFrom,dateTo);
        }

    }

    @Scheduled(cron="0 */30 * * * *")       //每30分钟更新一次当天的额数据
    public void saveBranchDailyDirectMinute(){
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        int size = systemBookCode.length;
        for (int i = 0; i < size ; i++) {
            List<BranchDaily> branchDailySummary = posOrderRpc.findBranchDailySummary(systemBookCode[i], date, date);

            int dailySize = branchDailySummary.size();
            List<BranchDailyDirect> list = new ArrayList<BranchDailyDirect>(dailySize);
            for (int j = 0; j < dailySize; j++) {
                BranchDaily branchDaily = branchDailySummary.get(j);
                BranchDailyDirect branchDailyDirect = new BranchDailyDirect();
                branchDailyDirect.setSystemBookCode(branchDailyDirect.getSystemBookCode());
                branchDailyDirect.setBranchNum(branchDaily.getBranchNum());
                branchDailyDirect.setShiftTableBizday(branchDaily.getShiftTableBizday());
                branchDailyDirect.setDailyMoney(branchDaily.getDailyMoney());
                branchDailyDirect.setDailyQty(branchDaily.getDailyQty());
                branchDailyDirect.setShiftTableDate(branchDaily.getShiftTableDate());
                branchDailyDirect.setDailyPrice(branchDaily.getDailyPrice());
                branchDailyDirect.setTargetMoney(branchDaily.getTargetMoney());
                list.add(branchDailyDirect);
            }

            azureService.batchSaveBranchDailyDirects(systemBookCode[i], list, date, date);
        }

    }

    @Scheduled(cron="0 0 2-3 * * *")     ////更新历史2
    public void saveBranchDailyDirectHour(){    //每天凌晨更新前2天的数据
        Calendar calendar = Calendar.getInstance();
        Date dateTo = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH,-2);
        Date dateFrom = calendar.getTime();
        int size = systemBookCode.length;
        for (int i = 0; i < size ; i++) {
            List<BranchDaily> branchDailySummary = posOrderRpc.findBranchDailySummary(systemBookCode[i], dateFrom, dateTo);

            int dailySize = branchDailySummary.size();
            List<BranchDailyDirect> list = new ArrayList<BranchDailyDirect>(dailySize);
            for (int j = 0; j <dailySize; j++) {
                BranchDaily branchDaily = branchDailySummary.get(j);
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
            azureService.batchSaveBranchDailyDirects(systemBookCode[i],list,dateFrom,dateTo);
        }

    }
    @Scheduled(cron="0 0 2-3 * * *")
    public void deleteBranchDailyDirect(){      //保留两天数据
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-2);
        Date date = calendar.getTime();
        int size = systemBookCode.length;
        for (int i = 0; i < size ; i++) {
            azureService.batchDeleteBranchDailyDirects(systemBookCode[i],date,date);
        }
    }

    @Scheduled(cron="0 */30 * * * *")       //    @Scheduled(cron="0 0,30 * * * *")
    public void saveItemDailyDetailMinute(){        //商品日时段销售汇总(从凌晨开始，每个小时的0分和30分执行一次)
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        int size = systemBookCode.length;
        for (int i = 0; i < size ; i++) {

            List<Integer> posItemNums = azureService.findPosItemNums(systemBookCode[i]);
            List<ItemDailyDetail> itemDailyDetailSummary = posOrderRpc.findItemDailyDetailSummary(systemBookCode[i], date, date,posItemNums);
            azureService.batchSaveItemDailyDetails(systemBookCode[i],itemDailyDetailSummary,date,date);

        }

    }

    @Scheduled(cron="0 0 2-3 * * *")
    public void saveItemDailyDetailHour(){        //商品日时段销售汇总(汇总昨天的数据)
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        Date date = calendar.getTime();
        int size = systemBookCode.length;
        for (int i = 0; i < size ; i++) {
            //在商品维度表里面查询商品编码
            List<Integer> posItemNums = azureService.findPosItemNums(systemBookCode[i]);
            List<ItemDailyDetail> itemDailyDetailSummary = posOrderRpc.findItemDailyDetailSummary(systemBookCode[i], date, date,posItemNums);
            azureService.batchSaveItemDailyDetails(systemBookCode[i],itemDailyDetailSummary,date,date);
        }

    }

    @Scheduled(cron="0 0 2-3 * * *")
    public void deleteItemDetailDaily(){        //删除2天前的数据（保存两天的数据）
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-2);
        Date date = calendar.getTime();
        int size = systemBookCode.length;
        for (int i = 0; i < size ; i++) {
            azureService.batchDeleteItemDetailDailies(systemBookCode[i],date,date);
        }

    }


    @Scheduled(cron="0 0 2-3 * * *")
    public void insertBranch(){                 //每天凌晨2店-4点 每个小时执行一次

        int size = systemBookCode.length;
        for (int i = 0; i < size ; i++) {
            List<BranchDTO> branchDTOs = branchRpc.findInCache(systemBookCode[i]);
            int branchSize = branchDTOs.size();
            List<Branch> list = new ArrayList<Branch>(branchSize);
            if (branchDTOs.isEmpty()) {
                return;
            }
            for (int j = 0; j < branchSize; j++) {
                BranchDTO branchDTO = branchDTOs.get(j);
                Branch branch = new Branch();
                branch.setSystemBookCode(branchDTO.getSystemBookCode());
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
            azureService.batchSaveBranchs(systemBookCode[i], list);
        }

    }

    @Scheduled(cron="0 0,30 0 * * *") //每天凌晨定时更新日期表
    public void saveBizday(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   //设置日期格式
        Calendar calendar = Calendar.getInstance();
        Date day = calendar.getTime();
        calendar.setTime(day);

        Integer thisYear = calendar.get(Calendar.YEAR);             //本年
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        Integer thisWeek = calendar.get(Calendar.WEEK_OF_YEAR);     //本周

        Integer thisMonth = Integer.valueOf(sdf.format(calendar.getTime()).substring(5, 7)); //为了匹配微软的周 运算结果
        if(thisWeek == 1 && thisMonth > 11) {
            calendar.add(Calendar.DAY_OF_MONTH, -7);
            thisWeek = calendar.get(Calendar.WEEK_OF_YEAR) + 1;
            calendar.add(Calendar.DAY_OF_MONTH, 7);
        }

        List<Bizday> list = new ArrayList<Bizday>();
        for(int i = 0; i < 735; i++) {
            calendar.setFirstDayOfWeek(Calendar.MONDAY);
            String date = sdf.format(calendar.getTime());                  //bizday_date
            String year = sdf.format(calendar.getTime()).substring(0, 4);  //bizday_year
            Integer quarter = (Integer.valueOf(sdf.format(calendar.getTime()).substring(5, 7))+2)/3;//bizday_quater
            String yearAndMonth = sdf.format(calendar.getTime()).substring(0, 4)+sdf.format(calendar.getTime()).substring(5, 7);//bizday_year_month
            Integer month = Integer.valueOf(sdf.format(calendar.getTime()).substring(5, 7));        //bizday_month
            Integer dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);    //bizday_dayofyear
            Integer weeknumOfYear = calendar.get(Calendar.WEEK_OF_YEAR); //bizday_week_of_year
            if(weeknumOfYear == 1 && month > 11) {      //为了匹配微软的周 运算结果
                calendar.add(Calendar.DAY_OF_MONTH, -7);
                weeknumOfYear = calendar.get(Calendar.WEEK_OF_YEAR) + 1;
                calendar.add(Calendar.DAY_OF_MONTH, 7);
            }
            String yearAndWeek = null; //bizday_year_week
            if(weeknumOfYear < 10) {
                yearAndWeek = year + "0" + weeknumOfYear;
            } else {
                yearAndWeek = year + weeknumOfYear;
            }
            Integer weekDay = null;
            if(calendar.get(Calendar.DAY_OF_WEEK) == 1) {
                weekDay = 7;
            } else {
                weekDay = calendar.get(Calendar.DAY_OF_WEEK)-1;
            }
            String yearName = "Y" + year;       //bizday_year_name
            String quarterName = "Q" + quarter; //bizday_quarter_name
            String monthName = null;            //bizday_month_name
            switch(month) {
                case 1: monthName = "一月";
                    break;
                case 2: monthName = "二月";
                    break;
                case 3: monthName = "三月";
                    break;
                case 4: monthName = "四月";
                    break;
                case 5: monthName = "五月";
                    break;
                case 6: monthName = "六月";
                    break;
                case 7: monthName = "七月";
                    break;
                case 8: monthName = "八月";
                    break;
                case 9: monthName = "九月";
                    break;
                case 10: monthName = "十月";
                    break;
                case 11: monthName = "十一月";
                    break;
                case 12: monthName = "十二月";
                    break;
            }
            String weekOfYearName = "W" + weeknumOfYear; //bizday_weekOfYear_name
            String dayOfMonthName = "D" + dayOfMonth;    //bizday_day_name
            String dayOfWeekName = null;                 //bizday_dayofweek_name
            switch(weekDay) {
                case 1: dayOfWeekName = "周一";
                    break;
                case 2: dayOfWeekName = "周二";
                    break;
                case 3: dayOfWeekName = "周三";
                    break;
                case 4: dayOfWeekName = "周四";
                    break;
                case 5: dayOfWeekName = "周五";
                    break;
                case 6: dayOfWeekName = "周六";
                    break;
                case 7: dayOfWeekName = "周日";
                    break;
            }
            String yearmonth = year+"年"+month+"月";              //bizday_yearandmonth_name
            String yearweek = year+"年"+" 第"+weeknumOfYear+"周"; //bizday_yearandweek_name
            Integer isThisWeek = null;                          //bizday_isthisweek
            if(Integer.valueOf(year).equals(thisYear) && weeknumOfYear == thisWeek) {
                isThisWeek = 1;
            } else {
                isThisWeek = 0;
            }
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            Bizday bizday = new Bizday();
            bizday.setBizdayDate(date);
            bizday.setBizdayYear(year);
            bizday.setBizdayQuarter(quarter);

            bizday.setBizdayYearMonth(yearAndMonth);
            bizday.setBizdayMonth(month);
            bizday.setBizdayDayofyear(dayOfMonth);

            bizday.setBizdayYearWeek(yearAndWeek);
            bizday.setBizdayWeekofyear(weeknumOfYear);
            bizday.setBizdayDayofweek(weekDay);

            bizday.setBizdayYearName(yearName);
            bizday.setBizdayQuarterName(quarterName);
            bizday.setBizdayMonthName(monthName);

            bizday.setBizdayWeekofyearName(weekOfYearName);
            bizday.setBizdayDayName(dayOfMonthName);
            bizday.setBizdayDayofweekName(dayOfWeekName);

            bizday.setBizdayYearandmonthName(yearmonth);
            bizday.setBizdayYearandweekName(yearweek);
            bizday.setBizdayIsthisweek(isThisWeek);
            list.add(bizday);
        }
        int size = systemBookCode.length;
        for (int i = 0; i <size ; i++) {
            azureService.batchSaveBizdays(systemBookCode[i],list);
        }


    }


    //新加的表
    @Scheduled(cron="0 0 2-3 * * *") //每天凌晨2-3点更新前两天的数据     商品日销售汇总  (不需要今天的数据)
    public void saveItemSaleDaily(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        Date dateTo = calendar.getTime();   //昨天
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        Date dateFrom = calendar.getTime(); //前天
        int size = systemBookCode.length;
        for (int i = 0; i < size ; i++) {
            List<ItemSaleDailyDTO> itemSaleDailySummary = posOrderRpc.findItemSaleDailySummary(systemBookCode[i], dateFrom, dateTo);
            int itemSize = itemSaleDailySummary.size();
            List<ItemSaleDaily> list = new ArrayList<ItemSaleDaily>(itemSize);
            for (int j = 0; j < itemSize; j++) {
                ItemSaleDailyDTO itemSaleDailyDTO = itemSaleDailySummary.get(j);
                ItemSaleDaily itemSaleDaily = new ItemSaleDaily();
                itemSaleDaily.setSystemBookCode(itemSaleDaily.getSystemBookCode());
                itemSaleDaily.setBranchNum(itemSaleDailyDTO.getBranchNum());
                itemSaleDaily.setShiftTableBizday(itemSaleDailyDTO.getShiftTableBizday());
                itemSaleDaily.setItemNum(itemSaleDailyDTO.getItemNum());
                itemSaleDaily.setShiftTableDate(itemSaleDailyDTO.getShiftTableDate());
                itemSaleDaily.setItemMoney(itemSaleDailyDTO.getItemMoney());
                itemSaleDaily.setItemAmount(itemSaleDailyDTO.getItemAmount());
                itemSaleDaily.setItemCount(itemSaleDailyDTO.getItemCount());
                itemSaleDaily.setItemSource(itemSaleDailyDTO.getItemSource());
                itemSaleDaily.setItemMemberTag(itemSaleDailyDTO.getItemMemberTag());
                list.add(itemSaleDaily);
            }
            azureService.batchSaveItemSaleDailies(systemBookCode[i], list, dateFrom, dateTo);
        }



    }


    @Scheduled(cron="0 0 2-3 * * *")        //每天凌晨2-3点删除三个月前的数据     商品日销售汇总
    public void deleteItemSaleDaily(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-4);
        Date time = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        String format = sdf.format(time);
        calendar.add(Calendar.DAY_OF_MONTH,-Integer.valueOf(format)+1);
        Date date = calendar.getTime();
        int size = systemBookCode.length;
        for (int i = 0; i < size ; i++) {
            azureService.batchDeleteItemSaleDailies(systemBookCode[i],date,date);
        }

    }


    @Scheduled(cron="0 0 2-3 * * *")    //每天凌晨2-3点更新一次前两天的数据   商品日报损汇总
    public void saveItemLossDaily(){

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        Date dateTo = calendar.getTime();   //昨天
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        Date dateFrom = calendar.getTime(); //前天
        int size = systemBookCode.length;
        for (int i = 0; i <size ; i++) {
            List<ItemLossDailyDTO> itemLossDailySummary = adjustmentOrderRpc.findItemLoss(systemBookCode[i], dateFrom, dateTo);
            int itemSize = itemLossDailySummary.size();
            List<ItemLossDaily> list = new ArrayList<ItemLossDaily>(itemSize);
            for (int j = 0; j <itemSize ; j++) {
                ItemLossDailyDTO itemLossDailyDTO = itemLossDailySummary.get(j);
                ItemLossDaily itemLossDaily = new ItemLossDaily();
                itemLossDaily.setSystemBookCode(itemLossDailyDTO.getSystemBookCode());
                itemLossDaily.setBranchNum(itemLossDailyDTO.getBranchNum());
                itemLossDaily.setShiftTableBizday(itemLossDailyDTO.getShiftTableBizday());
                itemLossDaily.setItemNum(itemLossDailyDTO.getItemNum());
                itemLossDaily.setItemLossReason(itemLossDailyDTO.getItemLossReason());
                itemLossDaily.setShiftTableDate(itemLossDailyDTO.getShiftTableDate());
                itemLossDaily.setItemMoney(itemLossDailyDTO.getItemMoney());
                itemLossDaily.setItemAmount(itemLossDailyDTO.getItemAmount());
                list.add(itemLossDaily);
            }
            azureService.batchSaveItemLossDailies(systemBookCode[i],list,dateFrom,dateTo);
        }


    }

    @Scheduled(cron="0 0 2-3 * * *")    //每天凌晨2-3点删除三个月前的数据   商品日报损汇总
    public void deleteItemLossDaily(){

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-4);
        Date time = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        String format = sdf.format(time);
        calendar.add(Calendar.DAY_OF_MONTH,-Integer.valueOf(format)+1);
        Date date = calendar.getTime();
        int size = systemBookCode.length;
        for (int i = 0; i <size ; i++) {
            azureService.batchDeleteItemLossDailies(systemBookCode[i],date,date);
        }

    }


    @Scheduled(cron="0 0 2-3 * * *") //每天凌晨2-3点更新前两天的数据         会员统计
    public void saveCardDailyHour(){

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        Date yesterday = calendar.getTime();   //昨天
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        Date beforeYesterday = calendar.getTime(); //前天
        int size = systemBookCode.length;
        for (int i = 0; i < size ; i++) {
            List<CardDailyDTO> CardDailyDTOs = new ArrayList<CardDailyDTO>();
            List<CardDailyDTO> yesterdays = reportRpc.findCardDailys(systemBookCode[i], null, yesterday, yesterday);
            List<CardDailyDTO> beforeYesterdays = reportRpc.findCardDailys(systemBookCode[i], null, beforeYesterday, beforeYesterday);
            CardDailyDTOs.addAll(yesterdays);
            CardDailyDTOs.addAll(beforeYesterdays);
            int cardSize = CardDailyDTOs.size();
            List<CardDaily> list = new ArrayList<CardDaily>(cardSize);
            for (int j = 0; j <cardSize; j++) {
                CardDailyDTO cardDailyDTO = CardDailyDTOs.get(j);
                CardDaily cardDaily = new CardDaily();
                cardDaily.setSystemBookCode(cardDailyDTO.getSystemBookCode());
                cardDaily.setBranchNum(cardDailyDTO.getBranchNum());
                cardDaily.setShiftTableBizday(cardDailyDTO.getShiftTableBizday());
                cardDaily.setShiftTableDate(cardDailyDTO.getShiftTableDate());
                cardDaily.setCardDeliverCount(cardDailyDTO.getCardDeliverCount());
                cardDaily.setCardReturnCount(cardDailyDTO.getCardReturnCount());
                cardDaily.setCardDeliverTarget(cardDailyDTO.getCardDeliverTarget());
                cardDaily.setCardDepositCash(cardDailyDTO.getCardDepositCash());
                cardDaily.setCardDepositMoney(cardDailyDTO.getCardDepositMoney());
                cardDaily.setCardDepositTarget(cardDailyDTO.getCardDepositTarget());
                cardDaily.setCardConsumeMoney(cardDailyDTO.getCardConsumeMoney());
                list.add(cardDaily);
            }
            azureService.batchSaveCardDailies(systemBookCode[i],list,beforeYesterday,yesterday);
        }


    }

    @Scheduled(cron="0 0 2-3 * * *")    //每天凌晨2-3点删除三个月前的数据   会员统计
    public void deleteCardDaily(){

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-4);
        Date time = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        String format = sdf.format(time);
        calendar.add(Calendar.DAY_OF_MONTH,-Integer.valueOf(format)+1);
        Date date = calendar.getTime();
        int size = systemBookCode.length;
        for (int i = 0; i < size ; i++) {
            azureService.batchDeleteCardDailies(systemBookCode[i],date,date);
        }


    }

    @Scheduled(cron="0 0 2-3 * * *")    //每天更新商品资料
    public void saveItem(){
        int size = systemBookCode.length;
        for (int i = 0; i < size ; i++) {
            List<PosItemDTO> all = posItemRpc.findAll(systemBookCode[i]);
            int posItemSize = all.size();
            List<PosItem> list = new ArrayList<PosItem>(posItemSize);
            for (int j = 0; j < posItemSize ; j++) {

                PosItemDTO posItemDTO = all.get(j);
                PosItem posItem = new PosItem();
                posItem.setSystemBookCode(posItemDTO.getSystemBookCode());
                posItem.setItemNum(posItemDTO.getItemNum());
                posItem.setItemName(posItemDTO.getItemName());
                posItem.setItemCategory(posItemDTO.getItemCategoryCode());//顶级弗雷
                posItem.setItemSubCategory(posItemDTO.getItemCategory());
                posItem.setItemCode(posItemDTO.getItemCode());//新加字段
                list.add(posItem);
            }
            azureService.batchSaveItem(systemBookCode[i],list);
        }


    }


    @Scheduled(cron="0 */30 * * * *")   //test//每30分钟执行一次当天的数据
    public void testSaveItemSaleDaily(){

        Calendar calendar = Calendar.getInstance();
        Date time = calendar.getTime();
        int size = systemBookCode.length;
        for (int i = 0; i < size ; i++) {
            List<ItemSaleDailyDTO> itemSaleDailySummary = posOrderRpc.findItemSaleDailySummary(systemBookCode[i], time, time);
            int itemSize = itemSaleDailySummary.size();
            List<ItemSaleDaily> list = new ArrayList<ItemSaleDaily>(itemSize);
            for (int j = 0; j < itemSize ; j++) {
                ItemSaleDailyDTO itemSaleDailyDTO = itemSaleDailySummary.get(j);
                ItemSaleDaily itemSaleDaily = new ItemSaleDaily();
                itemSaleDaily.setSystemBookCode(itemSaleDailyDTO.getSystemBookCode());
                itemSaleDaily.setBranchNum(itemSaleDailyDTO.getBranchNum());
                itemSaleDaily.setShiftTableBizday(itemSaleDailyDTO.getShiftTableBizday());
                itemSaleDaily.setItemNum(itemSaleDailyDTO.getItemNum());
                itemSaleDaily.setShiftTableDate(itemSaleDailyDTO.getShiftTableDate());
                itemSaleDaily.setItemMoney(itemSaleDailyDTO.getItemMoney());
                itemSaleDaily.setItemAmount(itemSaleDailyDTO.getItemAmount());
                itemSaleDaily.setItemCount(itemSaleDailyDTO.getItemCount());
                itemSaleDaily.setItemSource(itemSaleDailyDTO.getItemSource());
                itemSaleDaily.setItemMemberTag(itemSaleDailyDTO.getItemMemberTag());
                list.add(itemSaleDaily);
            }
            azureService.batchSaveItemSaleDailies(systemBookCode[i],list,time,time);
        }


    }
}