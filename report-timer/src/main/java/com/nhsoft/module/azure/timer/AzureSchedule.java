package com.nhsoft.module.azure.timer;

import com.nhsoft.module.azure.model.*;
import com.nhsoft.module.azure.service.AzureService;
import com.nhsoft.module.report.dto.*;
import com.nhsoft.module.report.rpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.SimpleDateFormat;
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
    @Autowired
    private AdjustmentOrderRpc adjustmentOrderRpc;
    @Autowired
    private CardUserRpc cardUserRpc;
    @Autowired
    private CardDepositRpc cardDepositRpc;
    @Autowired
    private BranchTransferGoalsRpc branchTransferGoalsRpc;
    @Autowired
    private CardConsumeRpc cardConsumeRpc;

    @Autowired
    private ReportRpc reportRpc;

    String systemBook = "4410";

    @Scheduled(cron="0 */30 * * * *")
    public void saveBranchDailyMinute(){     //分店销售汇总(每30分钟执行一次)  Scheduled(cron="0 */30 * * * *")
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        List<BranchDaily> branchDailySummary = posOrderRpc.findBranchDailySummary(systemBook, date, date);
        azureService.batchSaveBranchDailies(systemBook,branchDailySummary,date,date);
    }

    @Scheduled(cron="0 0 2-3 * * *")////更新历史1
    public void saveBranchDailyHour(){     //分店销售汇总(每天凌晨2点执行,更新前七天的数据)
        Calendar calendar = Calendar.getInstance();
        Date dateTo = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH,-7);
        Date dateFrom = calendar.getTime();
        List<BranchDaily> branchDailySummary = posOrderRpc.findBranchDailySummary(systemBook, dateFrom, dateTo);
        azureService.batchSaveBranchDailies(systemBook,branchDailySummary,dateFrom,dateTo);
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
            branchDailyDirect.setSystemBookCode(systemBook);
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
        calendar.add(Calendar.DAY_OF_MONTH,-2);
        Date dateFrom = calendar.getTime();
        List<BranchDaily> branchDailySummary = posOrderRpc.findBranchDailySummary(systemBook, dateFrom, dateTo);
        List<BranchDailyDirect> list = new ArrayList<BranchDailyDirect>();
        for (int i = 0; i <branchDailySummary.size() ; i++) {
            BranchDaily branchDaily = branchDailySummary.get(i);
            BranchDailyDirect branchDailyDirect = new BranchDailyDirect();
            branchDailyDirect.setSystemBookCode(systemBook);
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
        calendar.add(Calendar.DAY_OF_MONTH,-2);
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

    @Scheduled(cron="0 0 2-3 * * *")
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
        calendar.add(Calendar.DAY_OF_MONTH,-2);
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
            branch.setSystemBookCode(systemBook);
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

    @Scheduled(cron="0 0 0 * * *") //每天凌晨定时更新日期表
    public void saveBizday(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   //设置日期格式
        Calendar calendar = Calendar.getInstance();
        Date day = calendar.getTime();
        calendar.setTime(day);

        Integer thisYear = calendar.get(Calendar.YEAR);             //本年
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        Integer thisWeek = calendar.get(Calendar.WEEK_OF_YEAR);     //本周
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
                case 1: dayOfWeekName = "星期一";
                    break;
                case 2: dayOfWeekName = "星期二";
                    break;
                case 3: dayOfWeekName = "星期三";
                    break;
                case 4: dayOfWeekName = "星期四";
                    break;
                case 5: dayOfWeekName = "星期五";
                    break;
                case 6: dayOfWeekName = "星期六";
                    break;
                case 7: dayOfWeekName = "星期日";
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

            bizday.setBizdayYearweek(yearAndWeek);
            bizday.setBizdayWeekofYear(weeknumOfYear);
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
        azureService.batchSaveBizdays(systemBook,list);

    }

    @Scheduled(cron="0 */30 * * * *")       //每30分钟，更新一次当天数据    商品日销售汇总
    public void saveItemSaleDailyMin(){
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        List<ItemSaleDailyDTO> itemSaleDailySummary = posOrderRpc.findItemSaleDailySummary(systemBook, date, date);
        if(itemSaleDailySummary.isEmpty()){
            return;
        }
        List<ItemSaleDaily> list = new ArrayList<ItemSaleDaily>();
        for (int i = 0; i <itemSaleDailySummary.size() ; i++) {
            ItemSaleDailyDTO itemSaleDailyDTO = itemSaleDailySummary.get(i);
            ItemSaleDaily itemSaleDaily = new ItemSaleDaily();
            itemSaleDaily.setSystemBookCode(systemBook);
            itemSaleDaily.setBranchNum(itemSaleDailyDTO.getItemNum());
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
        azureService.batchSaveItemSaleDailies(systemBook,list,date,date);
    }

    @Scheduled(cron="0 0 2-3 * * *")       //每天凌晨2-3点更新前两天的数据     商品日销售汇总
    public void saveItemSaleDailyHour(){
        Calendar calendar = Calendar.getInstance();
        Date dateTo = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH,-2);
        Date dateFrom = calendar.getTime();
        List<ItemSaleDailyDTO> itemSaleDailySummary = posOrderRpc.findItemSaleDailySummary(systemBook, dateFrom, dateTo);
        if(itemSaleDailySummary.isEmpty()){
            return;
        }
        List<ItemSaleDaily> list = new ArrayList<ItemSaleDaily>();
        for (int i = 0; i <itemSaleDailySummary.size() ; i++) {
            ItemSaleDailyDTO itemSaleDailyDTO = itemSaleDailySummary.get(i);
            ItemSaleDaily itemSaleDaily = new ItemSaleDaily();
            itemSaleDaily.setSystemBookCode(systemBook);
            itemSaleDaily.setBranchNum(itemSaleDailyDTO.getItemNum());
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
        azureService.batchSaveItemSaleDailies(systemBook,list,dateFrom,dateTo);
    }

    @Scheduled(cron="0 0 2-3 * * *")        //删除2天以前的数据     商品日销售汇总
    public void deleteItemSaleDaily(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-2);
        Date date = calendar.getTime();
        azureService.batchDeleteItemSaleDailies(systemBook,date,date);
    }


    @Scheduled(cron="0 */30 * * * *")       //每30分钟，更新一次当天数据    商品日报损汇总
    public void saveItemLossDailyMin(){

        Date date = Calendar.getInstance().getTime();
        List<ItemLossDailyDTO> itemLossDailySummary = adjustmentOrderRpc.findItemLossDailySummary(systemBook, date, date);
        if(itemLossDailySummary.isEmpty()){
            return;
        }
        List<ItemLossDaily> list = new ArrayList<ItemLossDaily>();
        for (int i = 0; i <itemLossDailySummary.size() ; i++) {
            ItemLossDailyDTO itemLossDailyDTO = itemLossDailySummary.get(i);
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
        azureService.batchSaveItemLossDailies(systemBook,list,date,date);
    }


    @Scheduled(cron="0 0 2-3 * * *")       //每天凌晨2-3点更新一次前两天的数据   商品日报损汇总
    public void saveItemLossDailyHour(){

        Calendar calendar = Calendar.getInstance();
        Date dateTo = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH,-2);
        Date dateFrom = calendar.getTime();
        List<ItemLossDailyDTO> itemLossDailySummary = adjustmentOrderRpc.findItemLossDailySummary(systemBook, dateFrom, dateFrom);
        if(itemLossDailySummary.isEmpty()){
            return;
        }
        List<ItemLossDaily> list = new ArrayList<ItemLossDaily>();
        for (int i = 0; i <itemLossDailySummary.size() ; i++) {
            ItemLossDailyDTO itemLossDailyDTO = itemLossDailySummary.get(i);
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
        azureService.batchSaveItemLossDailies(systemBook,list,dateFrom,dateFrom);
    }

    @Scheduled(cron="0 0 2-3 * * *")    //每天凌晨2-3点删除两天前的数据   商品日报损汇总
    public void deleteItemLossDaily(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-2);
        Date date = calendar.getTime();
        azureService.batchDeleteItemLossDailies(systemBook,date,date);
    }


    @Scheduled(cron = "0 */30 * * * *")     //每30分钟跟新一次当天的数据      会员统计
    public void saveCardDailyMin(){
        Date date = Calendar.getInstance().getTime();
        List<CardDailyDTO> CardDailyDTOs = reportRpc.findCardDailyByBranchBizday(systemBook, null, date, date);
        List<CardDaily> list = new ArrayList<CardDaily>();
        for (int i = 0; i <CardDailyDTOs.size() ; i++) {
            CardDailyDTO cardDailyDTO = CardDailyDTOs.get(i);
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
        azureService.batchSaveCardDailies(systemBook,list,date,date);

    }


    @Scheduled(cron="0 0 2-3 * * *")     //每天凌晨2-3点更新前两天的数据         会员统计
    public void saveCardDailyHour(){
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        Date yesterday = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        Date beforeYesterday = calendar.getTime();

        List<CardDailyDTO> CardDailyDTOs = new ArrayList<CardDailyDTO>();
        List<CardDailyDTO> todays = reportRpc.findCardDailyByBranchBizday(systemBook, null, today, today);
        List<CardDailyDTO> yesterdays = reportRpc.findCardDailyByBranchBizday(systemBook, null, yesterday, yesterday);
        List<CardDailyDTO> beforeYesterdays = reportRpc.findCardDailyByBranchBizday(systemBook, null, beforeYesterday, beforeYesterday);
        CardDailyDTOs.addAll(todays);
        CardDailyDTOs.addAll(yesterdays);
        CardDailyDTOs.addAll(beforeYesterdays);

        List<CardDaily> list = new ArrayList<CardDaily>();
        for (int i = 0; i <CardDailyDTOs.size() ; i++) {
            CardDailyDTO cardDailyDTO = CardDailyDTOs.get(i);
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
        azureService.batchSaveCardDailies(systemBook,list,beforeYesterday,today);
    }





    @Scheduled(cron="0 0 2-3 * * *")    //每天凌晨2-3点删除两天前的数据   会员统计
    public void deleteCardDaily(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-2);
        Date date = calendar.getTime();
        azureService.batchDeleteCardDailies(systemBook,date,date);
    }









}
