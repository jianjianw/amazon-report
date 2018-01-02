package com.nhsoft.module.azure.timer;

import com.nhsoft.module.azure.model.*;
import com.nhsoft.module.azure.service.AzureService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.math.BigDecimal.ROUND_HALF_UP;

@Component
public class ImportDemoSchedule {


    @Autowired
    AzureService azureService;

    public String getDateStr(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(date);
    }

    String systemBookCode = "12345";

    @Scheduled(cron="0 20 1 * * *")
    public void branchDaily(){//按分店和营业日统计的都是100条数据
        List<BranchDaily> list = new ArrayList<BranchDaily>();
        List<BranchDailyDirect> directList = new ArrayList<BranchDailyDirect>();

        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String shiftTableBizday = getDateStr(date);

        Integer branchNum;      //1-100
        BigDecimal dailyMoney;          //营业额   5000-20000
        Integer dailyQty;         //客单量     100-500
        BigDecimal dailyPrice;      //客单价      50-100
        BigDecimal targetMoney;     //营业额目标  10000-15000
        BigDecimal dailyCount;     //客单购买数
        BranchDaily branchDaily;
        BranchDailyDirect branchDailyDirect;    //实时分店销售数据

        Random random = new Random();
        BigDecimal big = new BigDecimal(1);

        for (int i = 1; i <= 100; i++) {
            branchNum = i;

            double money = random.nextDouble()+1;
            BigDecimal money_ = new BigDecimal(money * 20000 - money * 5000);
            dailyMoney = money_.divide(big, 2, ROUND_HALF_UP);      //营业额

            dailyQty = random.nextInt(500);
            if(dailyQty<100){
                dailyQty+=100;
            }

            double price = random.nextDouble();
            BigDecimal price_ = new BigDecimal(price * 100 - price * 50);
            dailyPrice = price_.divide(big, 2, ROUND_HALF_UP);//客单价

            double target = random.nextDouble()+1;
            BigDecimal target_ = new BigDecimal(target * 15000 - target * 5000);
            targetMoney = target_.divide(big, 2, ROUND_HALF_UP);    //营业额目标


            int value1 = random.nextInt(5)+1;
            int value2 = random.nextInt(100);
            dailyCount = new BigDecimal(value1 + "." + value2);//客单购买数

            branchDaily = new BranchDaily();
            branchDaily.setSystemBookCode(systemBookCode);
            branchDaily.setBranchNum(branchNum);
            branchDaily.setShiftTableBizday(shiftTableBizday);
            branchDaily.setShiftTableDate(date);
            branchDaily.setDailyMoney(dailyMoney);
            branchDaily.setDailyQty(dailyQty);
            branchDaily.setDailyPrice(dailyPrice);
            branchDaily.setTargetMoney(targetMoney);
            branchDaily.setDailyCount(dailyCount);

            branchDailyDirect = new BranchDailyDirect();
            branchDailyDirect.setSystemBookCode(systemBookCode);
            branchDailyDirect.setBranchNum(branchNum);
            branchDailyDirect.setShiftTableBizday(shiftTableBizday);
            branchDailyDirect.setShiftTableDate(date);
            branchDailyDirect.setDailyMoney(dailyMoney);
            branchDailyDirect.setDailyQty(dailyQty);
            branchDailyDirect.setDailyPrice(dailyPrice);
            branchDailyDirect.setTargetMoney(targetMoney);

            list.add(branchDaily);

            directList.add(branchDailyDirect);


        }
        azureService.batchSaveBranchDailies(systemBookCode,list,date,date);
        azureService.batchSaveBranchDailyDirects(systemBookCode,directList,date,date);
    }

    @Scheduled(cron="0 20 1 * * *")
    public void cardDaily(){//按分店和营业日统计的都是100条数据

        List<CardDaily> list = new ArrayList<CardDaily>();
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();

        String systemBookCode = "12345";
        Integer branchNum;      //1-100
        String shiftTableBizday = getDateStr(date);

        Integer cardDeliverCount;       //新发卡数          20-50
        Integer cardReturnCount;        //退卡数            0-5
        Integer cardDeliverTarget;      //发卡目标          10-30
        BigDecimal cardDepositCash;     //付款金额          1500-2500

        BigDecimal cardDepositMoney;    //存款金额          500-2000
        BigDecimal cardDepositTarget;   //存款目标          1000-1500
        BigDecimal cardConsumeMoney;    //消费金额          1500-2500
        BigDecimal big = new BigDecimal(1);
        CardDaily cardDaily;
        Random random = new Random();
        for (int i = 1; i <= 100 ; i++) {
            branchNum = i;//分店号
            cardDeliverCount = random.nextInt(50) + 20;     //新发卡数
            if(cardDeliverCount<20){
                cardDeliverCount+=20;
            }
            cardReturnCount = random.nextInt(6);            //退卡数

            cardDeliverTarget = random.nextInt(30) + 10;    //发卡目标

            if(cardDeliverTarget<10){
                cardDeliverTarget+=10;
            }

            double cash = random.nextDouble() + 1;
            BigDecimal cash_ = new BigDecimal(cash * 2500 - cash * 1000);
            cardDepositCash = cash_.divide(big, 2, ROUND_HALF_UP); //付款金额

            double depositMoney = random.nextDouble() + 1;
            BigDecimal depositMoney_ = new BigDecimal(depositMoney * 2000 - depositMoney * 500);
            cardDepositMoney = depositMoney_.divide(big, 2, ROUND_HALF_UP); //存款金额

            double depositTarget = random.nextDouble() + 1;
            BigDecimal depositTarget_ = new BigDecimal(depositTarget * 1500 - depositTarget * 500);
            cardDepositTarget = depositTarget_.divide(big, 2, ROUND_HALF_UP); //存款目标

            double consumeMoney = random.nextDouble() + 1;
            BigDecimal consumeMoney_ = new BigDecimal(consumeMoney * 2500 - consumeMoney * 1000);
            cardConsumeMoney = consumeMoney_.divide(big, 2, ROUND_HALF_UP); //消费金额

            cardDaily = new CardDaily();
            cardDaily.setSystemBookCode(systemBookCode);
            cardDaily.setBranchNum(branchNum);
            cardDaily.setShiftTableBizday(shiftTableBizday);
            cardDaily.setShiftTableDate(date);
            cardDaily.setCardDeliverCount(cardDeliverCount);        //新发卡数
            cardDaily.setCardReturnCount(cardReturnCount);         //退卡数
            cardDaily.setCardDeliverTarget(cardDeliverTarget);       //发卡目标
            cardDaily.setCardDepositCash(cardDepositCash);         //付款金额
            cardDaily.setCardDepositMoney(cardDepositMoney);        //存款金额
            cardDaily.setCardDepositTarget(cardDepositTarget);       //存款目标
            cardDaily.setCardConsumeMoney(cardConsumeMoney);        //消费金额
            list.add(cardDaily);
        }


        azureService.batchSaveCardDailies(systemBookCode,list,date,date);
    }

    @Scheduled(cron="0 20 1 * * *")
    public void itemLossDaily(){        //每天大概150条记录

        List<ItemLossDaily> list = new ArrayList<ItemLossDaily>();
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();

        String shiftTableBizday = getDateStr(date);
        Integer branchNum;          //1-100
        Integer itemNum;            //123450001 - 123450100      itemCode  10001 - 10100
        String[] itemLossReason = {"水果现切","报损"} ;      //报损原因      水果现切      报损
        BigDecimal itemMoney;       //报损金额   0-1000
        BigDecimal itemAmount;      //报损数量  1-10

        //循环的size
        Random random = new Random();

        int size = random.nextInt(200);
        if(size<100){
            size += 100;
        }
        BigDecimal big = new BigDecimal(1);
        ItemLossDaily itemLossDaily;
        for (int i = 1; i <= size ; i++) {

            branchNum = random.nextInt(100) + 1;
            itemNum = 123450000 + (random.nextInt(100) + 1);    //123450001 - 123450100

            int value1 = random.nextInt(1000);
            int value2 = random.nextInt(100);
            BigDecimal money_ = new BigDecimal(value1+"."+value2);
            itemMoney = money_.divide(big, 2, ROUND_HALF_UP);   //报损金额   0-1000

            int value3 = random.nextInt(10)+1;
            int value4 = random.nextInt(100);
            BigDecimal amount = new BigDecimal(value3+"."+value4);
            itemAmount = amount.divide(big,2,ROUND_HALF_UP);

            itemLossDaily = new ItemLossDaily();
            itemLossDaily.setSystemBookCode(systemBookCode);
            itemLossDaily.setBranchNum(branchNum);
            itemLossDaily.setShiftTableBizday(shiftTableBizday);
            itemLossDaily.setShiftTableDate(date);
            itemLossDaily.setItemNum(itemNum);
            itemLossDaily.setItemLossReason(itemLossReason[i%2]);
            itemLossDaily.setItemMoney(itemMoney);
            itemLossDaily.setItemAmount(itemAmount);
            list.add(itemLossDaily);
        }

        Map<String,ItemLossDaily> map = new HashMap<String, ItemLossDaily>();
        Integer branchNum1;
        Integer itemNum1;
        String itemLossReason1;
        StringBuilder sb;
        for (int i = 0; i <size ; i++) {
            ItemLossDaily lossDaily = list.get(i);
            branchNum1 = lossDaily.getBranchNum();
            itemNum1 = lossDaily.getItemNum();
            itemLossReason1 = lossDaily.getItemLossReason();
            sb = new StringBuilder();
            String key = sb.append(branchNum1).append(itemNum1).append(itemLossReason1).toString();
            ItemLossDaily itemLossDaily1 = map.get(key);
            if(itemLossDaily1 == null){
                map.put(key,lossDaily);
            }
        }
        List<ItemLossDaily> returnList = new ArrayList<ItemLossDaily>(map.values());

        azureService.batchSaveItemLossDailies(systemBookCode,returnList,date,date);

    }

    @Scheduled(cron="0 20 1 * * *")
    public void itemSaleDaily(){

        List<ItemSaleDaily> list = new ArrayList<ItemSaleDaily>();
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String shiftTableBizday = getDateStr(date);

        Integer branchNum;
        Integer itemNum;
        String[] itemSource = {"线下门店","蜂巢微商城","美团"};      //单据来源
        String[] itemMemberTag = {"会员","非会员"};  //会员标记
        BigDecimal itemMoney;   //销售金额  //10-500
        BigDecimal itemAmount;  //销售数量  //1-50
        Integer itemCount;      //销售次数  //1-20

        BigDecimal big = new BigDecimal(1);
        ItemSaleDaily itemSaleDaily;
        //循环size
        Random random = new Random();
        int size = random.nextInt(17000);
        if(size < 10000){
            size+=10000;
        }
        for (int i = 0; i <size ; i++) {

            branchNum  = random.nextInt(100)+1;
            itemNum = 123450000 + random.nextInt(100)+1;

            //单据来源下标
            int sourceIndex = random.nextInt(3);
            //会员标记下标
            int memberIndex = random.nextInt(2);

            int value3 = random.nextInt(490)+10;
            int value4 = random.nextInt(100);
            BigDecimal money = new BigDecimal(value3 + "." + value4);
            itemMoney = money.divide(big, 2, ROUND_HALF_UP);   //   销售金额

            int value5 = random.nextInt(50) + 1;
            int value6 = random.nextInt(100);
            BigDecimal amount = new BigDecimal(value5 + "." + value6);
            itemAmount = amount.divide(big, 2, ROUND_HALF_UP);

            itemCount = random.nextInt(20) + 1;

            itemSaleDaily = new ItemSaleDaily();
            itemSaleDaily.setSystemBookCode(systemBookCode);
            itemSaleDaily.setShiftTableBizday(shiftTableBizday);
            itemSaleDaily.setShiftTableDate(date);
            itemSaleDaily.setBranchNum(branchNum);
            itemSaleDaily.setItemNum(itemNum);
            itemSaleDaily.setItemSource(itemSource[sourceIndex]);
            itemSaleDaily.setItemMemberTag(itemMemberTag[memberIndex]);
            itemSaleDaily.setItemMoney(itemMoney);
            itemSaleDaily.setItemAmount(itemAmount);
            itemSaleDaily.setItemCount(itemCount);
            list.add(itemSaleDaily);
        }
        int size1 = list.size();
        //解决主键冲突
        Map<String,ItemSaleDaily> map = new HashMap<String,ItemSaleDaily>();
        for (int i = 0; i <size1 ; i++) {
            ItemSaleDaily sale = list.get(i);
            Integer branchNum1 = sale.getBranchNum();
            Integer itemNum1 = sale.getItemNum();
            String itemMemberTag1 = sale.getItemMemberTag();
            String itemSource1 = sale.getItemSource();
            StringBuilder stringBuilder = new StringBuilder();
            String key = stringBuilder.append(branchNum1).append(itemNum1).append(itemMemberTag1).append(itemSource1).toString();
            ItemSaleDaily saleDaily = map.get(key);
            if(saleDaily == null ){
                map.put(key,sale);
            }
        }
        List<ItemSaleDaily> returnList = new ArrayList<ItemSaleDaily>(map.values());
        azureService.batchSaveItemSaleDailies(systemBookCode,returnList,date,date);
    }

    @Scheduled(cron="0 20 1 * * *")
    public void itemDailyDetail(){

        List<ItemDailyDetail> list = new ArrayList<ItemDailyDetail>();
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String shiftTableBizday = getDateStr(date);
        String[] itemSource = {"线下门店", "蜂巢微商城", "美团"};      //销售来源

        Integer branchNum;
        Integer itemNum;
        String[] itemPeriod  = new String[40];      //时段

        BigDecimal itemPeriodAmout;    //当前时段销售数量
        BigDecimal itemPeriodMoney;    //当前时段销售金额

        ItemDailyDetail itemDailyDetail;
        Random random = new Random();

        BigDecimal big  = new BigDecimal(1);
        for (int i = 0; i < 300; i++) {

            BigDecimal itemAmout = BigDecimal.ZERO;   //销售数量时段汇总
            BigDecimal itemMoney = BigDecimal.ZERO;   //销售金额时段汇总

            branchNum  = random.nextInt(100)+1;                 //id
            itemNum = 123450000 + random.nextInt(100)+1;        //id
            //单据来源下标
            int sourceIndex = random.nextInt(3);

            for (int j = 0; j <40 ; j++) {

                if(j == 0){
                    itemPeriod[j] = "00:30";
                }else if(j % 2 == 0){
                    if(j<19){
                        itemPeriod[j] = "0"+ (j/2) +":30";  //偶数
                    }else{
                        itemPeriod[j] = (j/2) +":30";
                    }
                }else if(j % 2 != 0){      //基数
                    if(j<19){
                        itemPeriod[j] = "0" + ((j+1)/2) + ":00";
                    }else{
                        itemPeriod[j] = ((j+1)/2) + ":00";
                    }
                }

                itemDailyDetail  = new ItemDailyDetail();
                itemDailyDetail.setSystemBookCode(systemBookCode);                 //id
                itemDailyDetail.setBranchNum(branchNum);                            //id
                itemDailyDetail.setItemNum(itemNum);                                //id
                itemDailyDetail.setShiftTableBizday(shiftTableBizday);              //id
                itemDailyDetail.setItemSource(itemSource[sourceIndex]);             //id
                itemDailyDetail.setItemPeriod(itemPeriod[j]);                       //id
                itemDailyDetail.setShiftTableDate(date);

                int value1 = random.nextInt(100);
                int value2 = random.nextInt(100);
                BigDecimal periodAmout = new BigDecimal(value1+"."+value2);
                itemPeriodAmout = periodAmout.divide(big, 2, ROUND_HALF_UP);

                itemDailyDetail.setItemPeriodAmout(itemPeriodAmout);

                int value3 = random.nextInt(100);
                int value4 = random.nextInt(100);
                BigDecimal periodMoney = new BigDecimal(value3+"."+value4);
                itemPeriodMoney = periodMoney.divide(big, 2, ROUND_HALF_UP);

                itemDailyDetail.setItemPeriodMoney(itemPeriodMoney);

                itemAmout = itemAmout.add(itemPeriodAmout) ;
                itemMoney = itemMoney.add(itemPeriodMoney);
                itemDailyDetail.setItemAmout(itemAmout);
                itemDailyDetail.setItemMoney(itemMoney);

                list.add(itemDailyDetail);
            }
        }

        Map<String,ItemDailyDetail> map = new HashMap<String, ItemDailyDetail>();
        int size1 = list.size();
        for (int i = 0; i <size1 ; i++) {

            ItemDailyDetail detail = list.get(i);
            Integer branchNum1 = detail.getBranchNum();
            Integer itemNum1 = detail.getItemNum();
            String period = detail.getItemPeriod();
            String itemSource1 = detail.getItemSource();
            StringBuilder stringBuilder = new StringBuilder();
            String key = stringBuilder.append(branchNum1).append(itemNum1).append(period).append(itemSource1).toString();
            ItemDailyDetail itemDailyDetail1 = map.get(key);
            if(itemDailyDetail1 == null){
                map.put(key,detail);
            }
        }
        List<ItemDailyDetail> returnList = new ArrayList<ItemDailyDetail>(map.values());
        azureService.batchSaveItemDailyDetails(systemBookCode,returnList,date,date);

    }

    @Scheduled(cron="0 20 1 * * *") //每天凌晨定时更新日期表
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
        azureService.batchSaveBizdays(systemBookCode,list);

    }


}
