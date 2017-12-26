package com.nhsoft.module.azure.rest;

import com.google.gson.Gson;
import com.nhsoft.module.azure.model.*;
import com.nhsoft.module.azure.service.AzureService;
import com.nhsoft.module.report.dto.*;
import com.nhsoft.module.report.rpc.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value="/azure")
public class InitApi {

    @Autowired
    private PosOrderRpc posOrderRpc;
    @Autowired
    private AzureService azureService;
    @Autowired
    private BranchRpc branchRpc;
    @Autowired
    private AdjustmentOrderRpc adjustmentOrderRpc;
    @Autowired
    private ReportRpc reportRpc;
    @Autowired
    private PosItemRpc posItemRpc;


    @RequestMapping(method = RequestMethod.GET,value = "/echo")
    public String echo(){

        Date time = Calendar.getInstance().getTime();
        return time.toString();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/init/branchDaily/{systemBookCode}/{dateFrom}/{dateTo}")
    public String initBranchDaily(@PathVariable("systemBookCode") String systemBookCode, @PathVariable("dateFrom") String dateFrom, @PathVariable("dateTo") String dateTo) {
        Date form = null;
        Date to = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            form = sdf.parse(dateFrom);
            to = sdf.parse(dateTo);
        } catch (ParseException e) {
            throw new RuntimeException("日期解析失败");
        }
        List<BranchDaily> branchDailySummary = posOrderRpc.findBranchDailySummary(systemBookCode, form, to);
        azureService.batchSaveBranchDailies(systemBookCode, branchDailySummary,form,to);
        return "SUCCESS";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/init/branch/{systemBookCode}")
    public String insertBranch(@PathVariable("systemBookCode") String systemBookCode) {//@PathVariable("systemBookCode")
        List<BranchDTO> brachDTO = branchRpc.findInCache(systemBookCode);
        List<Branch> list = new ArrayList<Branch>();
        for (int i = 0; i < brachDTO.size(); i++) {
            BranchDTO branchDTO = brachDTO.get(i);
            Branch branch = new Branch();
            branch.setSystemBookCode(systemBookCode);
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
        azureService.batchSaveBranchs(systemBookCode, list);
        return "SUCCESS";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/init/itemDetail/{systemBookCode}/{dateFrom}/{dateTo}")
    public String initItemDeatil(@PathVariable("systemBookCode") String systemBookCode, @PathVariable("dateFrom") String dateFrom, @PathVariable("dateTo") String dateTo) {

        Date form = null;
        Date to = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            form = sdf.parse(dateFrom);
            to = sdf.parse(dateTo);
        } catch (ParseException e) {
            throw new RuntimeException("日期解析失败");
        }
        List<Integer> posItemNums = azureService.findPosItemNums(systemBookCode);
        List<ItemDailyDetail> itemDailyDetailSummary = posOrderRpc.findItemDailyDetailSummary(systemBookCode, form, to,posItemNums);
        azureService.batchSaveItemDailyDetails(systemBookCode, itemDailyDetailSummary,form,to);
        return "SUCCESS";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/delete/branchDaily/{systemBookCode}/{dateFrom}/{dateTo}")
    public String deleteBranchDaily(@PathVariable("systemBookCode") String systemBookCode, @PathVariable("dateFrom") String dateFrom, @PathVariable("dateTo") String dateTo) {
        Date from = null;
        Date to = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            from = sdf.parse(dateFrom);
            to = sdf.parse(dateTo);
        } catch (ParseException e) {
            throw new RuntimeException("日期解析失败");
        }
        azureService.batchDeleteBranchDailies(systemBookCode,from,to);
        return "SUCCESS";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/delete/itemDetail/{systemBookCode}/{dateFrom}/{dateTo}")
    public String deleteItemDetailDaily(@PathVariable("systemBookCode") String systemBookCode, @PathVariable("dateFrom") String dateFrom, @PathVariable("dateTo") String dateTo) {
        Date from = null;
        Date to = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            from = sdf.parse(dateFrom);
            to = sdf.parse(dateTo);
        } catch (ParseException e) {
            throw new RuntimeException("日期解析失败");
        }
        azureService.batchDeleteItemDetailDailies(systemBookCode,from,to);
        return "SUCCESS";
    }


    @RequestMapping(method = RequestMethod.GET, value = "/init/branchDailyDirect/{systemBookCode}/{dateFrom}/{dateTo}")
    public String initBranchDailyDirect(@PathVariable("systemBookCode") String systemBookCode, @PathVariable("dateFrom") String dateFrom, @PathVariable("dateTo") String dateTo) {
        Date from = null;
        Date to = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            from = sdf.parse(dateFrom);
            to = sdf.parse(dateTo);
        } catch (ParseException e) {
            throw new RuntimeException("日期解析失败");
        }
        List<BranchDaily> branchDailySummary = posOrderRpc.findBranchDailySummary(systemBookCode, from, to);
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
        azureService.batchSaveBranchDailyDirects(systemBookCode,list,from,to);
        return "SUCCESS";
    }


    @RequestMapping(method = RequestMethod.GET, value = "/delete/branchDailyDirect/{systemBookCode}/{dateFrom}/{dateTo}")
    public String deleteBranchDailyDirect(@PathVariable("systemBookCode") String systemBookCode, @PathVariable("dateFrom") String dateFrom, @PathVariable("dateTo") String dateTo) {
        Date from = null;
        Date to = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            from = sdf.parse(dateFrom);
            to = sdf.parse(dateTo);
        } catch (ParseException e) {
            throw new RuntimeException("日期解析失败");
        }
        azureService.batchDeleteBranchDailyDirects(systemBookCode,from,to);
        return "SUCCESS";
    }

    //一下都是新加的表

    @RequestMapping(method = RequestMethod.GET,value="/init/bizday/{systemBookCode}")
    public String saveBizday(@PathVariable("systemBookCode") String systemBookCode){

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
        return "Success";
    }


    //商品日销售
    @RequestMapping(method = RequestMethod.GET,value = "/init/saleDaily/{systemBookCode}/{dateFrom}/{dateTo}")
    public String initSaleDaily(@PathVariable("systemBookCode") String systemBookCode ,@PathVariable("dateFrom") String dateFrom, @PathVariable("dateTo") String dateTo) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date from = sdf.parse(dateFrom);
        Date to = sdf.parse(dateTo);
        List<ItemSaleDailyDTO> itemSaleDailySummary = posOrderRpc.findItemSaleDailySummary(systemBookCode, from, to);
        List<ItemSaleDaily> list = new ArrayList<ItemSaleDaily>();
        for(int i = 0; i<itemSaleDailySummary.size(); i++){
            ItemSaleDailyDTO itemSaleDailyDTO = itemSaleDailySummary.get(i);
            ItemSaleDaily itemSaleDaily = new ItemSaleDaily();
            itemSaleDaily.setSystemBookCode(systemBookCode);
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
        azureService.batchSaveItemSaleDailies(systemBookCode,list,from,to);
        return "SUCCESS";
    }
    @RequestMapping(method=RequestMethod.GET,value="/delete/saleDaily/{systemBookCode}/{dateFrom}/{dateTo}")
    public String deleteSaleDaily(@PathVariable("systemBookCode") String systemBookCode ,@PathVariable("dateFrom") String dateFrom, @PathVariable("dateTo") String dateTo)throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date from = sdf.parse(dateFrom);
        Date to = sdf.parse(dateTo);
        azureService.batchDeleteItemSaleDailies(systemBookCode,from,to);
        return "SUCCESS";
    }

    //商品日报损
    @RequestMapping(method = RequestMethod.GET,value = "/init/lossDaily/{systemBookCode}/{dateFrom}/{dateTo}")
    public String initLossDaily(@PathVariable("systemBookCode") String systemBookCode ,@PathVariable("dateFrom") String dateFrom, @PathVariable("dateTo") String dateTo) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date from = sdf.parse(dateFrom);
        Date to = sdf.parse(dateTo);
        List<ItemLossDailyDTO> itemLossDailySummary = adjustmentOrderRpc.findItemLossDailySummary(systemBookCode, from, to);
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
        azureService.batchSaveItemLossDailies(systemBookCode,list,from,to);
        return "SUCCESS";
    }
    @RequestMapping(method=RequestMethod.GET,value="/delete/lossDaily/{systemBookCode}/{dateFrom}/{dateTo}")
    public String deleteLossDaily(@PathVariable("systemBookCode") String systemBookCode ,@PathVariable("dateFrom") String dateFrom, @PathVariable("dateTo") String dateTo)throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date from = sdf.parse(dateFrom);
        Date to = sdf.parse(dateTo);
        azureService.batchDeleteItemLossDailies(systemBookCode,from,to);
        return "SUCCESS";
    }

    //会员统计
    @RequestMapping(method = RequestMethod.GET,value = "/init/cardDaily/{systemBookCode}/{dateFrom}/{dateTo}")
    public String  initCardDaily(@PathVariable("systemBookCode") String systemBookCode ,@PathVariable("dateFrom") String dateFrom, @PathVariable("dateTo") String dateTo) throws Exception{

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date from = sdf.parse(dateFrom);
        Date to = sdf.parse(dateTo);
        List<CardDailyDTO> CardDailyDTOs = reportRpc.findCardDailyByBranchBizday(systemBookCode, null, from, to);
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
        azureService.batchSaveCardDailies(systemBookCode,list,from,to);
        return "SUCCESS";
    }

    @RequestMapping(method=RequestMethod.GET,value="/delete/cardDaily/{systemBookCode}/{dateFrom}/{dateTo}")
    public String deleteCardDaily(@PathVariable("systemBookCode") String systemBookCode ,@PathVariable("dateFrom") String dateFrom, @PathVariable("dateTo") String dateTo)throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date from = sdf.parse(dateFrom);
        Date to = sdf.parse(dateTo);
        azureService.batchDeleteCardDailies(systemBookCode,from,to);
        return "SUCCESS";
    }


    @RequestMapping(method = RequestMethod.GET,value = "/init/batch/cardDaily/{systemBookCode}/{dateFrom}/{dateTo}")
    public String  initBatchCardDaily(@PathVariable("systemBookCode") String systemBookCode ,@PathVariable("dateFrom") String dateFrom, @PathVariable("dateTo") String dateTo) throws Exception{


        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        List<CardDailyDTO> list = new ArrayList<CardDailyDTO>();
        Date from = sdf.parse(dateFrom);
        Date to = sdf.parse(dateTo);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(to);
        List<CardDailyDTO> CardDailyDTOs = null;
        for (int i = 1; i <130 ; i++) {
            calendar.add(Calendar.DAY_OF_MONTH,-1);
            Date time = calendar.getTime();
            CardDailyDTOs = reportRpc.findCardDailyByBranchBizday(systemBookCode, null, time, time);
            list.addAll(CardDailyDTOs);
        }
        List<CardDaily> returnList = new ArrayList<CardDaily>();
        for (int i = 0; i <list.size() ; i++) {
            CardDailyDTO cardDailyDTO = list.get(i);
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
            returnList.add(cardDaily);
        }
        azureService.batchSaveCardDailies(systemBookCode,returnList,from,to);
        return "SUCCESS";
    }
    @RequestMapping(method = RequestMethod.GET,value = "/init/item/{systemBookCode}")
    public String initItem(@PathVariable("systemBookCode") String systemBookCode){
        List<PosItemDTO> all = posItemRpc.findAll(systemBookCode);
        List<PosItem> list = new ArrayList<PosItem>();
        for (int i = 0; i < all.size() ; i++) {
            PosItemDTO posItemDTO = all.get(i);
            PosItem posItem = new PosItem();
            posItem.setSystemBookCode(posItemDTO.getSystemBookCode());
            posItem.setItemNum(posItemDTO.getItemNum());
            posItem.setItemName(posItemDTO.getItemName());
            posItem.setItemCategory(posItemDTO.getItemCategoryCode());//顶级父类
            posItem.setItemSubCategory(posItemDTO.getItemCategory());
            posItem.setItemCode(posItemDTO.getItemCode());
            list.add(posItem);
        }
        azureService.batchSaveItem(systemBookCode,list);
        return "SUCCESS";
    }


    @RequestMapping(method = RequestMethod.GET,value = "/init/batch/cardDaily-nine/{systemBookCode}")
    public String  initBatchCardDailyNine(@PathVariable("systemBookCode") String systemBookCode) throws Exception{
        String date = "20170901";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date dateFrom = sdf.parse(date);
        List<CardDailyDTO> list = new ArrayList<CardDailyDTO>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateFrom);

        List<CardDailyDTO> CardDailyDTOs = null;
        CardDailyDTOs = reportRpc.findCardDailyByBranchBizday(systemBookCode, null, dateFrom, dateFrom);
        list.addAll(CardDailyDTOs);
        for (int i = 0; i <29 ; i++) {
            calendar.add(Calendar.DAY_OF_MONTH,1);
            Date time = calendar.getTime();
            CardDailyDTOs = reportRpc.findCardDailyByBranchBizday(systemBookCode, null, time, time);
            list.addAll(CardDailyDTOs);
        }
        List<CardDaily> returnList = new ArrayList<CardDaily>();
        for (int i = 0; i <list.size() ; i++) {
            CardDailyDTO cardDailyDTO = list.get(i);
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
            returnList.add(cardDaily);
        }
        String date_ = "20170930";
        Date dateTo = sdf.parse(date_);
        azureService.batchSaveCardDailies(systemBookCode,returnList,dateFrom,dateTo);
        return "SUCCESS";
    }

    @RequestMapping(method = RequestMethod.GET,value = "/init/batch/cardDaily-ten/{systemBookCode}")
    public String  initBatchCardDailyTen(@PathVariable("systemBookCode") String systemBookCode) throws Exception{

        String date = "20171001";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        List<CardDailyDTO> list = new ArrayList<CardDailyDTO>();
        Date dateFrom = sdf.parse(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateFrom);
        List<CardDailyDTO> CardDailyDTOs = null;
        CardDailyDTOs = reportRpc.findCardDailyByBranchBizday(systemBookCode, null, dateFrom, dateFrom);
        list.addAll(CardDailyDTOs);
        for (int i = 0; i <30 ; i++) {
            calendar.add(Calendar.DAY_OF_MONTH,1);
            Date time = calendar.getTime();
            CardDailyDTOs = reportRpc.findCardDailyByBranchBizday(systemBookCode, null, time, time);
            list.addAll(CardDailyDTOs);
        }
        List<CardDaily> returnList = new ArrayList<CardDaily>();
        for (int i = 0; i <list.size() ; i++) {
            CardDailyDTO cardDailyDTO = list.get(i);
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
            returnList.add(cardDaily);
        }
        String date_ = "20171031";
        Date dateTo = sdf.parse(date_);
        azureService.batchSaveCardDailies(systemBookCode,returnList,dateFrom,dateTo);
        return "SUCCESS";
    }

    @RequestMapping(method = RequestMethod.GET,value = "/init/batch/cardDaily-eleven/{systemBookCode}")
    public String  initBatchCardDailyEleven(@PathVariable("systemBookCode") String systemBookCode ) throws Exception{


        String date = "20171101";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        List<CardDailyDTO> list = new ArrayList<CardDailyDTO>();
        Date dateFrom = sdf.parse(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateFrom);
        List<CardDailyDTO> CardDailyDTOs = null;
        CardDailyDTOs = reportRpc.findCardDailyByBranchBizday(systemBookCode, null, dateFrom, dateFrom);
        list.addAll(CardDailyDTOs);
        for (int i = 0; i <29 ; i++) {
            calendar.add(Calendar.DAY_OF_MONTH,1);
            Date time = calendar.getTime();
            CardDailyDTOs = reportRpc.findCardDailyByBranchBizday(systemBookCode, null, time, time);
            list.addAll(CardDailyDTOs);
        }
        List<CardDaily> returnList = new ArrayList<CardDaily>();
        for (int i = 0; i <list.size() ; i++) {
            CardDailyDTO cardDailyDTO = list.get(i);
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
            returnList.add(cardDaily);
        }
        String date_ = "20171130";
        Date dateTo = sdf.parse(date_);
        azureService.batchSaveCardDailies(systemBookCode,returnList,dateFrom,dateTo);
        return "SUCCESS";
    }

    @RequestMapping(method = RequestMethod.GET,value = "/init/batch/cardDaily-twelve/{systemBookCode}")
    public String  initBatchCardDailyTwelve(@PathVariable("systemBookCode") String systemBookCode) throws Exception{


        String date = "20171201";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        List<CardDailyDTO> list = new ArrayList<CardDailyDTO>();
        Date dateFrom = sdf.parse(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateFrom);
        List<CardDailyDTO> CardDailyDTOs = null;
        CardDailyDTOs = reportRpc.findCardDailyByBranchBizday(systemBookCode, null, dateFrom, dateFrom);
        list.addAll(CardDailyDTOs);
        for (int i = 0; i <30 ; i++) {
            calendar.add(Calendar.DAY_OF_MONTH,1);
            Date time = calendar.getTime();
            CardDailyDTOs = reportRpc.findCardDailyByBranchBizday(systemBookCode, null, time, time);
            list.addAll(CardDailyDTOs);
        }
        List<CardDaily> returnList = new ArrayList<CardDaily>();
        for (int i = 0; i <list.size() ; i++) {
            CardDailyDTO cardDailyDTO = list.get(i);
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
            returnList.add(cardDaily);
        }
        String date_ = "20171231";
        Date dateTo = sdf.parse(date_);
        azureService.batchSaveCardDailies(systemBookCode,returnList,dateFrom,dateTo);
        return "SUCCESS";

    }
    @RequestMapping(method = RequestMethod.GET,value = "/delete/{systemBookCode}")
    public String deleteMonth(@PathVariable("systemBookCode") String systemBookCode){
        //删除三个月前的数据
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-3);
        Date time = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        String format = sdf.format(time);
        calendar.add(Calendar.DAY_OF_MONTH,-Integer.valueOf(format)+1);
        Date date = calendar.getTime();
        azureService.batchDeleteItemSaleDailies(systemBookCode,date,date);
        return "SUCCESS";
    }

    @RequestMapping(method = RequestMethod.GET,value = "/nimei")
    public String nimei(){
        return "nimei";
    }


}
