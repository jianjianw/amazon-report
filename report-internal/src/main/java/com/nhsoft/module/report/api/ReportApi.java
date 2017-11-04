package com.nhsoft.module.report.api;

import com.nhsoft.module.report.api.dto.*;
import com.nhsoft.module.report.dto.*;
import com.nhsoft.module.report.query.LogQuery;
import com.nhsoft.module.report.rpc.*;
import com.nhsoft.module.report.dto.TransferOutMoney;
import com.nhsoft.module.report.util.AppConstants;
import com.nhsoft.module.report.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.math.BigDecimal.ROUND_HALF_DOWN;

@RestController
@RequestMapping("/reportApi")
public class ReportApi {

    private static final Logger logger = LoggerFactory.getLogger(ReportApi.class);

    @Autowired
    private AdjustmentOrderRpc adjustmentOrderRpc;
    @Autowired
    private BranchRpc branchRpc;
    @Autowired
    private BranchTransferGoalsRpc branchTransferGoalsRpc;
    @Autowired
    private CardConsumeRpc cardConsumeRpc;
    @Autowired
    private CardDepositRpc cardDepositRpc;
    @Autowired
    private CardUserRpc cardUserRpc;
    @Autowired
    private PosOrderRpc posOrderRpc;
    @Autowired
    private TransferOutOrderRpc transferOutOrderRpc;
    @Autowired
    private AlipayLogRpc alipayLogRpc;

    public List<Integer> stringToList(String systemBookCode, String str) {

        List<Integer> bannchNumList = new ArrayList<>();
        //如果传入分店为null,就查询所有分店
        if (str == null || str.length() == 0) {
            List<BranchDTO> all = branchRpc.findAll(systemBookCode);
            for (int i = 0; i < all.size(); i++) {
                BranchDTO branchDTO = all.get(i);
                bannchNumList.add(branchDTO.getBranchNum());
            }
            return bannchNumList;
        }
        else {
            String replace = str.replace("[", "").replace("]", "").replace(" ", "");
            String[] split = replace.split(",");
            for (int i = 0; i < split.length; i++) {
                bannchNumList.add(Integer.parseInt(split[i]));
            }
            return bannchNumList;
        }

    }

    //alipaylogs
    @RequestMapping(method = RequestMethod.GET, value = "/findAlipayLogs")
    public @ResponseBody
    List<AlipayLogDTO> findAlipayLogs(@RequestParam("systemBookCode") String systemBookCode) {

        LogQuery logQuery = new LogQuery();
        logQuery.setSystemBookCode(systemBookCode);
        logQuery.setPaging(false);
        logQuery.setDateFrom(DateUtil.addMonth(Calendar.getInstance().getTime(), -3));
        logQuery.setDateTo(Calendar.getInstance().getTime());

        return alipayLogRpc.findByLogQuery(systemBookCode, 99, logQuery, 0, 0);
    }

    //按分店汇总
    @RequestMapping(method = RequestMethod.GET, value = "/store")
    public List<OperationStoreDTO> byBranch(@RequestHeader("systemBookCode") String systemBookCode,
                                            @RequestHeader("branchNums") String branchNums, @RequestHeader("date") String date) {

        //点击区域跳转到分店，区域下面没有分店，直接一个空的list
        if(branchNums != null && branchNums.length() == 2){
            List<OperationStoreDTO>  list = new ArrayList<>();
            return list;
        }
        List<Integer> bannchNumList = stringToList(systemBookCode, branchNums);

        Date beforeDateFrom = null;
        Date beforeDateTo = null;
        Date dateFrom = null;
        Date dateTo = null;
        //营业额目标（查询时间类型）
        String dateType = null;
        Calendar calendar = Calendar.getInstance();
        try {
            if (date.length() == 7) {
                dateType = AppConstants.BUSINESS_DATE_SOME_MONTH;
                //按月份查
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                dateFrom = sdf.parse(date);
                calendar.setTime(dateFrom);
                calendar.add(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH) - 1);
                dateTo = calendar.getTime();
                //上个月的时间
                calendar.setTime(dateFrom);
                calendar.add(Calendar.MONTH, -1);
                beforeDateFrom = calendar.getTime();
                calendar.setTime(beforeDateFrom);
                calendar.add(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH) - 1);
                beforeDateTo = calendar.getTime();
            } else if (date.length() == 10) {
                dateType = AppConstants.BUSINESS_DATE_SOME_DATE;
                //按天查
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                dateFrom = sdf.parse(date);
                dateTo = dateFrom;
                //昨天的时间
                calendar.setTime(dateFrom);
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                beforeDateFrom = calendar.getTime();  //昨天
                beforeDateTo = beforeDateFrom;
            } else {
                //按周查
                dateType = AppConstants.BUSINESS_DATE_SOME_WEEK;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                dateFrom = sdf.parse(date.substring(0, 11));
                dateTo = sdf.parse(date.substring(11, date.length()));
                //上周的时间
                calendar.setTime(dateFrom);
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                beforeDateTo = calendar.getTime();
                calendar.setTime(beforeDateTo);
                calendar.add(Calendar.DAY_OF_MONTH, -6);
                beforeDateFrom = calendar.getTime();
            }
        } catch (ParseException e) {
            logger.error("日期解析失败");
        }


        List<OperationStoreDTO> list = new ArrayList<>();

        //用于计算环比增长率
        List<BranchRevenueReport> beforeMoneyByBranch = posOrderRpc.findMoneyBranchSummary(systemBookCode, bannchNumList, AppConstants.BUSINESS_TREND_PAYMENT, beforeDateFrom, beforeDateTo, false);
        //营业额
        List<BranchRevenueReport> moneyByBranch = posOrderRpc.findMoneyBranchSummary(systemBookCode, bannchNumList, AppConstants.BUSINESS_TREND_PAYMENT, dateFrom, dateTo, false);
        //会员营业额
        List<BranchRevenueReport> memberMoneyByBranch = posOrderRpc.findMoneyBranchSummary(systemBookCode, bannchNumList, AppConstants.BUSINESS_TREND_PAYMENT, dateFrom, dateTo, true);
        //卡存款
        List<BranchDepositReport> depositByBranch = cardDepositRpc.findBranchSum(systemBookCode, bannchNumList, dateFrom, dateTo);
        //卡消费
        List<BranchConsumeReport> consumeByBranch = cardConsumeRpc.findBranchSum(systemBookCode, bannchNumList, dateFrom, dateTo);
        //配送金额
        List<TransferOutMoney> transferOutMoneyByBranch = transferOutOrderRpc.findMoneyBranchSummary(systemBookCode, bannchNumList, dateFrom, dateTo);
        //报损金额
        List<LossMoneyReport> lossMoneyByBranch = adjustmentOrderRpc.findLossMoneyByBranch(systemBookCode, bannchNumList, dateFrom, dateTo);
        //盘损金额
        List<CheckMoney> checkMoneyByBranch = adjustmentOrderRpc.findCheckMoneyByBranch(systemBookCode, bannchNumList, dateFrom, dateTo);
        //会员新增数
        List<CardUserCount> cardUserCountByBranch = cardUserRpc.findCardUserCountByBranch(systemBookCode, bannchNumList, dateFrom, dateTo);
        //营业额目标
        List<SaleMoneyGoals> saleMoneyGoalsByBranch = branchTransferGoalsRpc.findSaleMoneyGoalsByBranch(systemBookCode, bannchNumList, dateFrom, dateTo, dateType);
        //分店面积
        List<BranchArea> branchArea = branchRpc.findBranchArea(systemBookCode, bannchNumList);
        //损耗：试吃，去皮，报损，其他
        List<AdjustmentCauseMoney> adjustmentCauseMoneyByBranch = adjustmentOrderRpc.findAdjustmentCauseMoneyByBranch(systemBookCode, bannchNumList, dateFrom, dateTo);


        int day = DateUtil.diffDay(dateFrom, dateTo);
        //为了及计算日均客单量
        if (day == 0) {
            day = 1;
        }
        BigDecimal bigDay = new BigDecimal(day);//包装日期
        for (int i = 0; i < bannchNumList.size(); i++) {
            OperationStoreDTO store = new OperationStoreDTO();
            store.setBranchNum(bannchNumList.get(i));
            BranchDTO branchDTO = branchRpc.readWithNolock(systemBookCode, bannchNumList.get(i));
            store.setBranchName(branchDTO.getBranchName());
            store.setBigDay(bigDay);
            store.setBranchName(branchDTO.getBranchName());
            //上期的营业额
            Iterator before = beforeMoneyByBranch.iterator();
            while (before.hasNext()) {
                BranchRevenueReport next = (BranchRevenueReport) before.next();
                if (store.getBranchNum().equals(next.getBranchNum())) {
                    store.setBeforeSaleMoney(next.getBizMoney() == null ? BigDecimal.ZERO : next.getBizMoney());//上期营业额
                    break;
                }
            }

            Iterator money = moneyByBranch.iterator();
            while (money.hasNext()) {
                BranchRevenueReport next = (BranchRevenueReport) money.next();
                if (store.getBranchNum().equals(next.getBranchNum())) {
                    store.setBranchNum(next.getBranchNum());        //分店号
                    store.setRevenue(next.getBizMoney() == null ? BigDecimal.ZERO : next.getBizMoney());           //营业额
                    store.setGrossProfit(next.getProfit() == null ? BigDecimal.ZERO : next.getProfit());         //毛利
                    store.setBillNums(next.getOrderCount() == null ? 0 : next.getOrderCount());        //客单量
                    store.setAveBillNums(new BigDecimal(store.getBillNums()).divide(bigDay, 4, ROUND_HALF_DOWN));    //日均客单量
                    store.setBill(store.getRevenue().divide(new BigDecimal(store.getBillNums()), 4, ROUND_HALF_DOWN));//客单价
                    //本期营业额-上期营业额
                    BigDecimal subtract = store.getRevenue().subtract(store.getBeforeSaleMoney());
                    //环比增长率
                    if(store.getBeforeSaleMoney().compareTo(BigDecimal.ZERO) == 0){
                        store.setGrowthOf(BigDecimal.ZERO);
                    }else{
                        store.setGrowthOf(subtract.divide(store.getBeforeSaleMoney(), 4, ROUND_HALF_DOWN));//（本期-上期）/上期
                    }
                    break;
                }
            }


            Iterator cardUserCount = cardUserCountByBranch.iterator();
            while (cardUserCount.hasNext()) {
                CardUserCount next = (CardUserCount) cardUserCount.next();
                if (store.getBranchNum().equals(next.getBranchNum())) {
                    store.setIncressedMember(next.getCount() == null ? 0 : next.getCount()); //会员新增数
                    break;
                }
            }


            Iterator deposit = depositByBranch.iterator();
            while (deposit.hasNext()) {
                BranchDepositReport next = (BranchDepositReport) deposit.next();
                if (store.getBranchNum().equals(next.getBranchNum())) {
                    store.setCardStorage(next.getDeposit() == null ? BigDecimal.ZERO : next.getDeposit());//卡存款
                    break;
                }
            }

            Iterator transferOutMoney = transferOutMoneyByBranch.iterator();//配送金额
            while (transferOutMoney.hasNext()) {
                TransferOutMoney next = (TransferOutMoney) transferOutMoney.next();
                if (store.getBranchNum().equals(next.getBranchNum())) {
                    BigDecimal outMoney = next.getOutMoney();
                    store.setTransferOutMoney(outMoney);//配送金额
                    //计算配销差额
                    if(store.getRevenue() == null || store.getRevenue().compareTo(BigDecimal.ZERO) == 0){
                        store.setDistributionDifferent(outMoney == null ? BigDecimal.ZERO : outMoney);//配销差额
                    }else{
                        store.setDistributionDifferent((outMoney == null ? BigDecimal.ZERO : outMoney).subtract(store.getRevenue()));//配销差额
                    }
                    break;
                }
            }

            //如果营业额,会员新增数，卡存款，配送金额 为null或0  就跳出本次循环
            if (store.getRevenue().compareTo(BigDecimal.ZERO) == 0
                    && store.getIncressedMember().equals(0)
                    && store.getCardStorage().compareTo(BigDecimal.ZERO) == 0
                    && store.getDistributionDifferent().compareTo(BigDecimal.ZERO) == 0) {
                continue;
            }

            Iterator memberMoney = memberMoneyByBranch.iterator();
            while (memberMoney.hasNext()) {
                BranchRevenueReport next = (BranchRevenueReport) memberMoney.next();
                if (store.getBranchNum().equals(next.getBranchNum())) {

                    store.setMemberSaleMoney(next.getBizMoney());//会员销售额
                    if(store.getMemberSaleMoney() == null || store.getMemberSaleMoney().compareTo(BigDecimal.ZERO) == 0 ){
                        store.setMemeberRevenueOccupy(BigDecimal.ZERO);
                    }else if(store.getRevenue() == null || store.getRevenue().compareTo(BigDecimal.ZERO) == 0) {
                        store.setMemeberRevenueOccupy(BigDecimal.ZERO);
                    }else{
                        store.setMemeberRevenueOccupy(store.getMemberSaleMoney().divide(store.getRevenue(),2,ROUND_HALF_DOWN));//会员销售额占比
                    }
                    store.setMemberBillNums(next.getOrderCount() == null ? 0 : next.getOrderCount());    //会员客单量
                    store.setMemberBill(next.getBizMoney().divide(new BigDecimal(next.getOrderCount()), 4, ROUND_HALF_DOWN));//会员客单价
                    break;
                }
            }

            //卡消费   消费存储占比
            Iterator consume = consumeByBranch.iterator();
            while (consume.hasNext()) {
                BranchConsumeReport next = (BranchConsumeReport) consume.next();
                if (store.getBranchNum().equals(next.getBranchNum())) {
                    store.setCartStorageConsume(next.getConsume());//卡消费金额
                    if(store.getCartStorageConsume() == null || store.getCartStorageConsume().compareTo(BigDecimal.ZERO) == 0){
                        store.setStorageConsumeOccupy(BigDecimal.ZERO);
                    }else if(store.getCardStorage() == null || store.getCardStorage().compareTo(BigDecimal.ZERO) == 0){
                        store.setStorageConsumeOccupy(BigDecimal.ZERO);
                    }else{
                        store.setStorageConsumeOccupy(store.getCardStorage().divide(store.getCartStorageConsume(), 4, ROUND_HALF_DOWN));//存储消费占比
                    }
                    break;
                }
            }

            Iterator loss = lossMoneyByBranch.iterator();
            while (loss.hasNext()) {
                LossMoneyReport next = (LossMoneyReport) loss.next();
                if (store.getBranchNum().equals(next.getBranchNum())) {
                    store.setDestroyDefferent(next.getMoney() == null ? BigDecimal.ZERO : next.getMoney());//报损金额
                    break;
                }
            }
            Iterator check = checkMoneyByBranch.iterator();
            while (check.hasNext()) {
                CheckMoney next = (CheckMoney) check.next();
                if (store.getBranchNum().equals(next.getBranchNum())) {
                    store.setAdjustAmount(next.getMoney()== null ? BigDecimal.ZERO : next.getMoney());//盘损金额
                    break;
                }
            }
            //营业额目标   完成率
            Iterator saleMoneyGoals = saleMoneyGoalsByBranch.iterator();
            while (saleMoneyGoals.hasNext()) {
                SaleMoneyGoals next = (SaleMoneyGoals) saleMoneyGoals.next();
                if (store.getBranchNum().equals(next.getBranchNum())) {
                    store.setSaleMoneyGoal(next.getSaleMoney());//营业额目标
                    if (store.getSaleMoneyGoal() == null || store.getSaleMoneyGoal().compareTo(BigDecimal.ZERO) == 0) {
                        store.setRealizeRate1(BigDecimal.ZERO);
                    }else if(store.getRevenue() == null || store.getRevenue().compareTo(BigDecimal.ZERO) == 0){
                        store.setRealizeRate1(BigDecimal.ZERO);
                    }else {
                        store.setRealizeRate1(store.getRevenue().divide(store.getSaleMoneyGoal(), 4, ROUND_HALF_DOWN));//营业额完成率
                    }
                    break;
                }
            }
            Iterator area = branchArea.iterator();
            while (area.hasNext()) {
                BranchArea next = (BranchArea) area.next();
                if (store.getBranchNum().equals(next.getBranchNum())) {

                    if (next.getArea() == null || next.getArea().compareTo(BigDecimal.ZERO) == 0) {
                        store.setAreaEfficiency(BigDecimal.ZERO);
                    } else if(store.getRevenue() == null || store.getRevenue().compareTo(BigDecimal.ZERO) == 0){
                        store.setAreaEfficiency(BigDecimal.ZERO);
                    }else {
                        store.setAreaEfficiency(store.getRevenue().divide(next.getArea(), 4, ROUND_HALF_DOWN));//坪效
                    }
                    break;
                }
            }
            Iterator adjustmentCause = adjustmentCauseMoneyByBranch.iterator();
            while (adjustmentCause.hasNext()) {
                AdjustmentCauseMoney next = (AdjustmentCauseMoney) adjustmentCause.next();
                if (store.getBranchNum().equals(next.getBranchNum())) {
                    store.setTest(next.getTryEat() == null ? BigDecimal.ZERO : next.getTryEat() );    //试吃
                    store.setPeel(next.getFaly() == null ? BigDecimal.ZERO : next.getFaly() );      //去皮
                    store.setBreakage(next.getLoss() == null ? BigDecimal.ZERO : next.getLoss());  //报损
                    store.setOther(next.getOther() == null ? BigDecimal.ZERO : next.getOther());    //其他
                    break;
                }
            }
            //暂时将完成率设置为0
            store.setAllBillRealizeRate(BigDecimal.ZERO);           //总客单完成率
            store.setGrossProfitRate(BigDecimal.ZERO);              //毛利完成率
            store.setRealizeRate2(BigDecimal.ZERO);                 //新增会员数完成率
            store.setRealizeRate3(BigDecimal.ZERO);                 //卡储值完成率
            list.add(store);
        }
        System.out.println();
        return list;

    }

    //按区域汇总
    @RequestMapping(method = RequestMethod.GET, value = "/region")
    public List<OperationRegionDTO> byRegion(@RequestHeader("systemBookCode") String systemBookCode,
                                             @RequestHeader("branchNums") String branchNums, @RequestHeader("date") String date) {
        //按区域汇总
        List<OperationRegionDTO> list = new ArrayList<>();
        //按分店汇总
        List<OperationStoreDTO> operationStoreDTOS = byBranch(systemBookCode, branchNums, date);
        //根据账套号查询区域
        List<BranchRegionDTO> branchRegions = branchRpc.findBranchRegion(systemBookCode);

        //先遍历区域，在遍历按分店返回的数据，如果分店号，相等，就将分店的数据封装到区域里面
        for (int i = 0; i < branchRegions.size(); i++) {
            Integer regionNum = branchRegions.get(i).getBranchRegionNum();
            //多少个分店号就创建多少个分店
            OperationRegionDTO region = new OperationRegionDTO();

            BigDecimal revenue = BigDecimal.ZERO;  //营业额
            BigDecimal saleMoneyGoal = BigDecimal.ZERO;//营业额目标
            Integer billNums = 0;                   //客单量
            BigDecimal bill = BigDecimal.ZERO;                      //客单价
            Integer memberBillNums = 0;               //会员客单量
            BigDecimal memberSaleMoney = BigDecimal.ZERO;       //会员销售额
            BigDecimal memberBill = BigDecimal.ZERO;                //会员客单价
            BigDecimal transferOutMoney = BigDecimal.ZERO;          //配送金额
            BigDecimal destroyDefferent = BigDecimal.ZERO;          //报损金额
            BigDecimal adjustAmount = BigDecimal.ZERO;              //盘损金额
            BigDecimal grossProfit = BigDecimal.ZERO;               //毛利金额
            Integer incressedMember = 0;              //新增会员数
            BigDecimal cardStorage = BigDecimal.ZERO;               //卡储值金额
            BigDecimal cartStorageConsume = BigDecimal.ZERO;        //卡储值消费金额
            BigDecimal beforeSaleMoney  =BigDecimal.ZERO;           //上期营业额
            BigDecimal bigDay = BigDecimal.ONE;
            String areaBranchNums = "[";
            //得到区域下面的所有分店
            List<BranchDTO> branchs = branchRpc.findBranchByBranchRegionNum(systemBookCode, regionNum);

            for (int j = 0; j < branchs.size(); j++) {
                BranchDTO branch = branchs.get(j);
                //给区域中的分店号字段赋值
                if (j == branchs.size() - 1) {
                    areaBranchNums += branch.getBranchNum() + "]";
                } else {
                    areaBranchNums += branch.getBranchNum() + ",";
                }
                for (int k = 0; k < operationStoreDTOS.size(); k++) {
                    OperationStoreDTO storeDTO = operationStoreDTOS.get(k);
                    if (branch.getBranchNum().equals(storeDTO.getBranchNum())) {
                        revenue = revenue.add(storeDTO.getRevenue() == null ? BigDecimal.ZERO : storeDTO.getRevenue());                                         //营业额
                        saleMoneyGoal = saleMoneyGoal.add(storeDTO.getSaleMoneyGoal() == null ? BigDecimal.ZERO : storeDTO.getSaleMoneyGoal());                 //营业额目标
                        billNums += storeDTO.getBillNums() == null ? 0 : storeDTO.getBillNums();                                                                //客单量
                        bill = bill.add(storeDTO.getBill() == null ? BigDecimal.ZERO : storeDTO.getBill());                                                     //客单价
                        memberSaleMoney = memberSaleMoney.add(storeDTO.getMemberSaleMoney() == null ? BigDecimal.ZERO : storeDTO.getMemberSaleMoney());         //会员销售额
                        memberBillNums += (storeDTO.getMemberBillNums() == null ? 0 : storeDTO.getMemberBillNums());                                              //会员客单量
                        memberBill = memberBill.add(storeDTO.getMemberBill() == null ? BigDecimal.ZERO : storeDTO.getMemberBill());                             //会员客单价
                        transferOutMoney = transferOutMoney.add(storeDTO.getTransferOutMoney() == null ? BigDecimal.ZERO : storeDTO.getTransferOutMoney());     //配送金额
                        destroyDefferent = destroyDefferent.add(storeDTO.getDestroyDefferent() == null ? BigDecimal.ZERO : storeDTO.getDestroyDefferent());     //报损金额
                        adjustAmount = adjustAmount.add(storeDTO.getAdjustAmount() == null ? BigDecimal.ZERO : storeDTO.getAdjustAmount());                     //盘损金额
                        grossProfit = grossProfit.add(storeDTO.getGrossProfit() == null ? BigDecimal.ZERO : storeDTO.getGrossProfit());                         //毛利金额
                        incressedMember += (storeDTO.getIncressedMember() == null ? 0 : storeDTO.getIncressedMember());                                           //新增会员数
                        cardStorage = cardStorage.add(storeDTO.getCardStorage() == null ? BigDecimal.ZERO : storeDTO.getCardStorage());                         //卡储值金额
                        cartStorageConsume = cartStorageConsume.add(storeDTO.getCartStorageConsume() == null ? BigDecimal.ZERO : storeDTO.getCartStorageConsume());      //卡储值消费金额
                        beforeSaleMoney = beforeSaleMoney.add(storeDTO.getBeforeSaleMoney() == null ? BigDecimal.ZERO : storeDTO.getBeforeSaleMoney());                  //上期营业额
                        bigDay = storeDTO.getBigDay();//日期之差，计算日均客单量
                        break;
                    }
                }
            }

            region.setArea(branchRegions.get(i).getBranchRegionName());
            region.setRevenue(revenue); //营业额
            //如果区域下面没分店 areaBranchNums会等于 ]
            if (areaBranchNums.length() == 1) {
                areaBranchNums = areaBranchNums + "]";
            }
            region.setAreaBranchNums(areaBranchNums);                                                       //区域包含的分店
            region.setRevenue(revenue);//营业额
            region.setSaleMoneyGoal(saleMoneyGoal);//营业额目标
            if(saleMoneyGoal.compareTo(BigDecimal.ZERO) == 0){
                region.setRealizeRate1(BigDecimal.ZERO);
            }else{
                region.setRealizeRate1(revenue.divide(saleMoneyGoal, 4, ROUND_HALF_DOWN));              //营业额完成率
            }
            region.setTransferOutMoney(transferOutMoney);//配送金额
            region.setDistributionDifferent(transferOutMoney.subtract(revenue));                                //配销差额
            region.setMemberSaleMoney(memberSaleMoney);//会员销售额
            if(revenue.compareTo(BigDecimal.ZERO) == 0){
                region.setMemeberRevenueOccupy(BigDecimal.ZERO);
            }else {
                region.setMemeberRevenueOccupy(memberSaleMoney.divide(revenue, 4, ROUND_HALF_DOWN));    //会员消费占比
            }
            if(memberBillNums.equals(0)){
                region.setBill(BigDecimal.ZERO);
            }else{
                region.setBill(revenue.divide(new BigDecimal(memberBillNums), 4, ROUND_HALF_DOWN));     //客单价
            }
            region.setBillNums(billNums);//客单量
            if(billNums == null || billNums.equals(0)){
                region.setAveBillNums(BigDecimal.ZERO);
            }else{
                BigDecimal bigBillNums = new BigDecimal(billNums);
                region.setAveBillNums(bigBillNums.divide(bigDay, 4, ROUND_HALF_DOWN));                                     //日均客单量
            }

            region.setMemberBillNums(memberBillNums);                                                           //会员客单量
            if(memberBill.compareTo(BigDecimal.ZERO) == 0){
                region.setMemberBill(BigDecimal.ZERO);
            }else{
                region.setMemberBill(memberSaleMoney.divide(memberBill, 4, ROUND_HALF_DOWN));                                       //会员客单价
            }
            region.setDestroyDefferent(destroyDefferent);                                                      //报损金额
            region.setAdjustAmount(adjustAmount);                                                              //盘损金额
            region.setGrossProfit(grossProfit);                                                                //毛利金额
            region.setIncressedMember(incressedMember);                                                        //新增会员数
            region.setCardStorage(cardStorage);                                                                //卡储值金额
            region.setCartStorageConsume(cartStorageConsume);                                                  //卡储值消费金额
            if(cartStorageConsume.compareTo(BigDecimal.ZERO) == 0){
                region.setStorageConsumeOccupy(BigDecimal.ZERO);
            }else{
                region.setStorageConsumeOccupy(cardStorage.divide(cartStorageConsume, 4, ROUND_HALF_DOWN));//储值消费占比
            }
            region.setBeforeSaleMoney(beforeSaleMoney);//上期营业额
            if(beforeSaleMoney.compareTo(BigDecimal.ZERO) == 0){
                region.setGrowthOf(BigDecimal.ZERO);
            }else{
                region.setGrowthOf((revenue.subtract(beforeSaleMoney)).divide(beforeSaleMoney, 4, ROUND_HALF_DOWN));//环比增长率     （本期-上期）/上期
            }
            //暂时将完成率设置为0
            region.setAllBillRealizeRate(BigDecimal.ZERO);      //总客单完成率
            region.setGrossProfitRate(BigDecimal.ZERO);        //毛利完成率
            region.setRealizeRate2(BigDecimal.ZERO);  //新增会员数完成率
            region.setRealizeRate3(BigDecimal.ZERO);//卡储值完成率
            list.add(region);
        }
        return list;
    }


    //按营业日汇总---日趋势 (时间传递月份)
    @RequestMapping(method = RequestMethod.GET, value = "/bizday")
    public List<TrendDailyDTO> byBizday(@RequestHeader("systemBookCode") String systemBookCode,
                                        @RequestHeader("branchNums") String branchNums, @RequestHeader("date") String date) {

        List<Integer> bannchNumList = new ArrayList<>();
        int index = branchNums.indexOf("|");
        String str = branchNums.substring(0, index);
        if (str.length() == 0) {
            List<BranchDTO> all = branchRpc.findAll(systemBookCode);
            for (int i = 0; i < all.size(); i++) {
                BranchDTO branchDTO = all.get(i);
                bannchNumList.add(branchDTO.getBranchNum());
            }
        } else {
            bannchNumList.add(Integer.parseInt(str));
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Calendar calendar = Calendar.getInstance();
        Date dateFrom = null;
        Date dateTo = null;
        try {
            dateFrom = sdf.parse(date);

        } catch (ParseException e) {
            logger.error("日期解析失败");
        }
        calendar.setTime(dateFrom);
        int max = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.add(Calendar.DAY_OF_MONTH, max - 1);
        dateTo = calendar.getTime();
        //营业额 客单量
        List<BranchBizRevenueSummary> revenueByBizday = posOrderRpc.findMoneyBizdaySummary(systemBookCode, bannchNumList, AppConstants.BUSINESS_TREND_PAYMENT, dateFrom, dateTo, false);
        //会员营业额 客单量
        List<BranchBizRevenueSummary> memberRevenueByBizday = posOrderRpc.findMoneyBizdaySummary(systemBookCode, bannchNumList, AppConstants.BUSINESS_TREND_PAYMENT, dateFrom, dateTo, true);
        //配送额
        List<TransferOutMoney> transferOutMoneyByBizday = transferOutOrderRpc.findMoneyBizdaySummary(systemBookCode, bannchNumList, dateFrom, dateTo);
        List<TrendDailyDTO> list = new ArrayList<>();
        for (int i = 0; i < max; i++) {
            calendar.setTime(dateFrom);
            calendar.add(Calendar.DAY_OF_MONTH, i);

            Date time = calendar.getTime();
            String bizday = DateUtil.getDateShortStr(time);
            TrendDailyDTO trendDaily = new TrendDailyDTO();
            trendDaily.setDay(bizday.substring(bizday.length() - 2, bizday.length()) + "日");
            //营业额 客单量
            for (int j = 0; j < revenueByBizday.size(); j++) {
                BranchBizRevenueSummary branchBizRevenueSummary = revenueByBizday.get(j);
                if (bizday.equals(branchBizRevenueSummary.getBiz())) {
                    trendDaily.setRevenue(branchBizRevenueSummary.getBizMoney() == null ? BigDecimal.ZERO : branchBizRevenueSummary.getBizMoney());
                    trendDaily.setBillNums(branchBizRevenueSummary.getOrderCount() == null ? 0 : branchBizRevenueSummary.getOrderCount());
                    break;
                }
            }
            //会员营业额 客单量
            for (int j = 0; j < memberRevenueByBizday.size(); j++) {
                BranchBizRevenueSummary branchBizRevenueSummary = memberRevenueByBizday.get(j);
                if (bizday.equals(branchBizRevenueSummary.getBiz())) {
                    trendDaily.setMemberRevenue(branchBizRevenueSummary.getBizMoney() == null ? BigDecimal.ZERO : branchBizRevenueSummary.getBizMoney());
                    trendDaily.setMemberBillNums(branchBizRevenueSummary.getOrderCount() == null ? 0 : branchBizRevenueSummary.getOrderCount());
                    break;
                }
            }
            //配送额
            for (int j = 0; j < transferOutMoneyByBizday.size(); j++) {
                TransferOutMoney transferOutMoney = transferOutMoneyByBizday.get(j);
                if (bizday.equals(transferOutMoney.getBiz())) {
                    trendDaily.setDistributionMoney(transferOutMoney.getOutMoney() == null ? BigDecimal.ZERO : transferOutMoney.getOutMoney());
                    break;
                }
            }
            list.add(trendDaily);
        }
        return list;
    }


    //按营业月汇总---月趋势（时间传递年份）
    @RequestMapping(method = RequestMethod.GET, value = "/bizmonth")
    public List<TrendMonthlyDTO> byBizmonth(@RequestHeader("systemBookCode") String systemBookCode,
                                            @RequestHeader("branchNums") String branchNums, @RequestHeader("date") String date) {
        List<Integer> bannchNumList = new ArrayList<>();
        int index = branchNums.indexOf("|");
        String str = branchNums.substring(0, index);
        if (str.length() == 0) {
            List<BranchDTO> all = branchRpc.findAll(systemBookCode);
            for (int i = 0; i < all.size(); i++) {
                BranchDTO branchDTO = all.get(i);
                bannchNumList.add(branchDTO.getBranchNum());
            }
        } else {
            bannchNumList.add(Integer.parseInt(str));
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date dateFrom = null;
        try {
            dateFrom = sdf.parse(date);
        } catch (ParseException e) {
            logger.error("日期解析失败");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateFrom);
        int month = 12;
        calendar.add(Calendar.MONTH, month-1);
        int maximum = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.add(Calendar.DAY_OF_MONTH,maximum-1);
        Date dateTo = calendar.getTime();

        //营业额 客单量
        List<BranchBizRevenueSummary> revenueByBizmonth = posOrderRpc.findMoneyBizmonthSummary(systemBookCode, bannchNumList, AppConstants.BUSINESS_TREND_PAYMENT, dateFrom, dateTo, false);
        //会员营业额 客单量
        List<BranchBizRevenueSummary> memberRevenueByBizmonth = posOrderRpc.findMoneyBizmonthSummary(systemBookCode, bannchNumList, AppConstants.BUSINESS_TREND_PAYMENT, dateFrom, dateTo, true);
        //配送额
        List<TransferOutMoney> transferOutMoneyBymonth = transferOutOrderRpc.findMoneyBymonthSummary(systemBookCode, bannchNumList, dateFrom, dateTo);
        List<TrendMonthlyDTO> list = new ArrayList<>();
        for (int i = 0; i <month ; i++) {
            calendar.setTime(dateFrom);
            calendar.add(Calendar.MONTH, i);
            Date time = calendar.getTime();
            String bizmonth = DateUtil.getYearAndMonthString(time);
            TrendMonthlyDTO trendMonthly = new TrendMonthlyDTO();
            trendMonthly.setMonth(bizmonth.substring(bizmonth.length() - 2, bizmonth.length()) + "月");
            //营业额 客单量
            for (int j = 0; j < revenueByBizmonth.size(); j++) {
                BranchBizRevenueSummary branchBizRevenueSummary = revenueByBizmonth.get(j);
                if (bizmonth.equals(branchBizRevenueSummary.getBiz())) {
                    trendMonthly.setRevenue(branchBizRevenueSummary.getBizMoney() == null ? BigDecimal.ZERO : branchBizRevenueSummary.getBizMoney());
                    trendMonthly.setBillNums(branchBizRevenueSummary.getOrderCount() == null ? 0 : branchBizRevenueSummary.getOrderCount());
                    trendMonthly.setGross(branchBizRevenueSummary.getProfit() == null ? BigDecimal.ZERO : branchBizRevenueSummary.getProfit());
                    break;
                }
            }
            //会员营业额 客单量
            for (int j = 0; j < memberRevenueByBizmonth.size(); j++) {
                BranchBizRevenueSummary branchBizRevenueSummary = memberRevenueByBizmonth.get(j);
                if (bizmonth.equals(branchBizRevenueSummary.getBiz())) {
                    trendMonthly.setMemberRevenue(branchBizRevenueSummary.getBizMoney() == null ? BigDecimal.ZERO : branchBizRevenueSummary.getBizMoney());
                    trendMonthly.setMemberBillNums(branchBizRevenueSummary.getOrderCount() == null ? 0 : branchBizRevenueSummary.getOrderCount());
                    trendMonthly.setGross(branchBizRevenueSummary.getProfit() == null ? BigDecimal.ZERO : branchBizRevenueSummary.getProfit());
                    break;
                }
            }
            //配送额
            for (int j = 0; j < transferOutMoneyBymonth.size(); j++) {
                TransferOutMoney transferOutMoney = transferOutMoneyBymonth.get(j);
                if (bizmonth.equals(transferOutMoney.getBiz())) {
                    trendMonthly.setDistributionMoney(transferOutMoney.getOutMoney() == null ? BigDecimal.ZERO : transferOutMoney.getOutMoney());
                    break;
                }
            }
            list.add(trendMonthly);
        }
        return list;
    }


    //1、增加门店完成率排名
    @RequestMapping(method = RequestMethod.GET, value = "/branchTop")
    public List<SaleFinishMoneyTopDTO> findMoneyFinishRateBranchTop(@RequestHeader("systemBookCode") String systemBookCode,
                                                                    @RequestHeader("branchNums") String branchNums, @RequestHeader("date") String date) {
        List<Integer> bannchNumList = stringToList(systemBookCode, branchNums);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateFrom = null;
        try {
            dateFrom = sdf.parse(date);
        } catch (ParseException e) {
            logger.error("日期解析失败");
        }

        //营业额
        List<BranchRevenueReport> moneyByBranch = posOrderRpc.findMoneyBranchSummary(systemBookCode, bannchNumList, AppConstants.BUSINESS_TREND_PAYMENT, dateFrom, dateFrom, false);
        //营业额目标
        List<SaleMoneyGoals> saleMoneyGoalsByBranch = branchTransferGoalsRpc.findSaleMoneyGoalsByBranch(systemBookCode, bannchNumList, dateFrom, dateFrom, AppConstants.BUSINESS_DATE_SOME_DATE);

        List<SaleFinishMoneyTopDTO> list = new ArrayList<>();
        for (int i = 0; i < bannchNumList.size(); i++) {
            SaleFinishMoneyTopDTO saleFinishMoneyTopDTO = new SaleFinishMoneyTopDTO();
            BranchDTO branchDTO = branchRpc.readWithNolock(systemBookCode, bannchNumList.get(i));
            saleFinishMoneyTopDTO.setName(branchDTO.getBranchName());
            saleFinishMoneyTopDTO.setNum(bannchNumList.get(i));
            //营业额
            for (int j = 0; j < moneyByBranch.size(); j++) {
                BranchRevenueReport branchRevenueReport = moneyByBranch.get(j);
                if (saleFinishMoneyTopDTO.getNum().equals(branchRevenueReport.getBranchNum())) {
                    saleFinishMoneyTopDTO.setSaleMoney(branchRevenueReport.getBizMoney() == null ? BigDecimal.ZERO : branchRevenueReport.getBizMoney() );
                    break;
                }
            }
            //营业额目标  和  完成率
            for (int j = 0; j < saleMoneyGoalsByBranch.size(); j++) {
                SaleMoneyGoals saleMoneyGoals = saleMoneyGoalsByBranch.get(j);

               if(saleFinishMoneyTopDTO.getNum().equals(saleMoneyGoals.getBranchNum())){
                   //营业额目标
                   saleFinishMoneyTopDTO.setGoalMoney(saleMoneyGoals.getSaleMoney() == null ? BigDecimal.ZERO : saleMoneyGoals.getSaleMoney());
                   //完成率
                   if(saleFinishMoneyTopDTO.getSaleMoney() == null || saleFinishMoneyTopDTO.getSaleMoney().compareTo(BigDecimal.ZERO) == 0){
                       saleFinishMoneyTopDTO.setFinishMoneyRate(BigDecimal.ZERO);
                   }else if(saleFinishMoneyTopDTO.getGoalMoney() == null || saleFinishMoneyTopDTO.getGoalMoney().compareTo(BigDecimal.ZERO) == 0){
                       saleFinishMoneyTopDTO.setFinishMoneyRate(BigDecimal.ZERO);
                   }else{
                       saleFinishMoneyTopDTO.setFinishMoneyRate(saleFinishMoneyTopDTO.getSaleMoney().divide(saleFinishMoneyTopDTO.getGoalMoney(),2,ROUND_HALF_DOWN));
                   }
               }
            }
            list.add(saleFinishMoneyTopDTO);
        }
        if(list.isEmpty()){
            return list;
        }
        //排序
        Collections.sort(list);
        for (int i = 0; i <list.size(); i++) {
            SaleFinishMoneyTopDTO saleFinishMoneyTopDTO = list.get(i);
            saleFinishMoneyTopDTO.setTopNum(i+1);
        }
        return list;
    }

    //2、增加区域完成率排名
    @RequestMapping(method = RequestMethod.GET, value = "/regionTop")
    public List<SaleFinishMoneyTopDTO> findMoneyFinishRateRegionTop(@RequestHeader("systemBookCode") String systemBookCode,
                                                                    @RequestHeader("branchNums") String branchNums, @RequestHeader("date") String date) {

        List<Integer> bannchNumList = stringToList(systemBookCode, branchNums);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateFrom = null;
        try {
            dateFrom = sdf.parse(date);
        } catch (ParseException e) {
            logger.error("日期解析失败");
        }

        //营业额
        List<BranchRevenueReport> moneyByBranch = posOrderRpc.findMoneyBranchSummary(systemBookCode, bannchNumList, AppConstants.BUSINESS_TREND_PAYMENT, dateFrom, dateFrom, false);
        //营业额目标
        List<SaleMoneyGoals> saleMoneyGoalsByBranch = branchTransferGoalsRpc.findSaleMoneyGoalsByBranch(systemBookCode, bannchNumList, dateFrom, dateFrom, AppConstants.BUSINESS_DATE_SOME_MONTH);
        //得到所有区域
        List<BranchRegionDTO> branchRegions = branchRpc.findBranchRegion(systemBookCode);
        List<SaleFinishMoneyTopDTO> list = new ArrayList<>();
        for (int i = 0; i < branchRegions.size(); i++) {
            BranchRegionDTO branchRegionDTO = branchRegions.get(i);
            SaleFinishMoneyTopDTO saleFinishMoneyTopDTO = new SaleFinishMoneyTopDTO();
            saleFinishMoneyTopDTO.setName(branchRegionDTO.getBranchRegionName());
            saleFinishMoneyTopDTO.setNum(branchRegionDTO.getBranchRegionNum());
            //得到区域下的所有分店
            List<BranchDTO> branchs = branchRpc.findBranchByBranchRegionNum(systemBookCode, branchRegionDTO.getBranchRegionNum());
            for (int j = 0; j < branchs.size(); j++) {
                BranchDTO branchDTO = branchs.get(j);
                //营业额
                for (int k = 0; k < moneyByBranch.size(); k++) {
                    BranchRevenueReport branchRevenueReport = moneyByBranch.get(k);
                    if (branchDTO.getBranchNum().equals(branchRevenueReport.getBranchNum())) {
                        saleFinishMoneyTopDTO.setSaleMoney(branchRevenueReport.getBizMoney() == null ? BigDecimal.ZERO : branchRevenueReport.getBizMoney());
                    }
                }
                //目标，完成率
                for (int k = 0; k < saleMoneyGoalsByBranch.size(); k++) {
                    SaleMoneyGoals saleMoneyGoals = saleMoneyGoalsByBranch.get(k);
                    if (branchDTO.getBranchNum().equals(saleMoneyGoals.getBranchNum())) {
                        //营业额目标
                        saleFinishMoneyTopDTO.setGoalMoney(saleMoneyGoals.getSaleMoney() == null ? BigDecimal.ZERO : saleMoneyGoals.getSaleMoney());
                        //完成率
                        if(saleFinishMoneyTopDTO.getSaleMoney() == null || saleFinishMoneyTopDTO.getSaleMoney().compareTo(BigDecimal.ZERO) == 0){
                            saleFinishMoneyTopDTO.setFinishMoneyRate(BigDecimal.ZERO);
                        }else if(saleFinishMoneyTopDTO.getGoalMoney() == null || saleFinishMoneyTopDTO.getGoalMoney().compareTo(BigDecimal.ZERO) == 0){
                            saleFinishMoneyTopDTO.setFinishMoneyRate(BigDecimal.ZERO);
                        }else{
                            saleFinishMoneyTopDTO.setFinishMoneyRate(saleFinishMoneyTopDTO.getSaleMoney().divide(saleFinishMoneyTopDTO.getGoalMoney(),2,ROUND_HALF_DOWN));
                        }
                    }
                }
            }
            list.add(saleFinishMoneyTopDTO);
        }
        if(list.isEmpty()){
            return list;
        }
        //排序
        Collections.sort(list);
        for (int i = 0; i <list.size(); i++) {
            SaleFinishMoneyTopDTO saleFinishMoneyTopDTO = list.get(i);
            saleFinishMoneyTopDTO.setTopNum(i+1);
        }
        return list;
    }

    //销售分析
    @RequestMapping(method = RequestMethod.GET, value = "/saleAnalysis")
    public List<SaleMoneyMonthDTO> findSaleAnalysisByMonth(@RequestHeader("systemBookCode") String systemBookCode,
                                                           @RequestHeader("branchNums") String branchNums, @RequestHeader("date") String date){

        List<Integer> bannchNumList = new ArrayList<>();
        int index = branchNums.indexOf("|");
        String str = branchNums.substring(0, index);
        if (str.length() == 0) {
            List<BranchDTO> all = branchRpc.findAll(systemBookCode);
            for (int i = 0; i < all.size(); i++) {
                BranchDTO branchDTO = all.get(i);
                bannchNumList.add(branchDTO.getBranchNum());
            }
        } else {
            bannchNumList.add(Integer.parseInt(str));
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date dateFrom = null;
        try {
            dateFrom = sdf.parse(date);
        } catch (ParseException e) {
            logger.error("日期解析失败");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateFrom);
        int month = 12;
        calendar.add(Calendar.MONTH,month-1);
        int maximum = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.add(Calendar.DAY_OF_MONTH,maximum-1);
        Date dateTo = calendar.getTime();

        Date beforeDateform  = null;        //去年的开始时间
        Date beforeDateTo = null;           //去年的结束时间
        calendar.setTime(dateFrom);
        calendar.add(Calendar.YEAR,-1);
        beforeDateform = calendar.getTime();
        calendar.add(Calendar.MONTH,month-1);
        calendar.add(Calendar.DAY_OF_MONTH,maximum-1);
        beforeDateTo = calendar.getTime();

        //按月份查询营业额
        List<BranchBizRevenueSummary> revenueByBizmonth = posOrderRpc.findMoneyBizmonthSummary(systemBookCode, bannchNumList, AppConstants.BUSINESS_TREND_PAYMENT, dateFrom, dateTo, false);
        //按月份查询营业额目标
        List<SaleMoneyGoals> saleMoneyGoalsByDate = branchTransferGoalsRpc.findSaleMoneyGoalsByDate(systemBookCode, bannchNumList, dateFrom, dateTo, AppConstants.BUSINESS_DATE_SOME_MONTH);
        //查询去年的营业额
        List<BranchBizRevenueSummary> beforeRevenueByBizmonth = posOrderRpc.findMoneyBizmonthSummary(systemBookCode, bannchNumList, AppConstants.BUSINESS_TREND_PAYMENT, beforeDateform, beforeDateTo, false);


        List<SaleMoneyMonthDTO> list = new ArrayList<>();
        for (int i = 0; i <month ; i++) {
            calendar.setTime(dateFrom);
            calendar.add(Calendar.MONTH, i);
            Date time = calendar.getTime();
            String bizmonth = DateUtil.getYearAndMonthString(time);
            SaleMoneyMonthDTO saleMoneyMonthDTO = new SaleMoneyMonthDTO();
            saleMoneyMonthDTO.setMonth(bizmonth.substring(bizmonth.length() - 2, bizmonth.length()) + "月");
            //营业额
            for (int j = 0; j <revenueByBizmonth.size() ; j++) {
                BranchBizRevenueSummary branchBizRevenueSummary = revenueByBizmonth.get(j);
                if(bizmonth.equals(branchBizRevenueSummary.getBiz())){
                    saleMoneyMonthDTO.setSaleMoney(branchBizRevenueSummary.getBizMoney() == null ? BigDecimal.ZERO : branchBizRevenueSummary.getBizMoney());//营业额
                }
            }
            //营业额目标
            for (int j = 0; j <saleMoneyGoalsByDate.size() ; j++) {
                SaleMoneyGoals saleMoneyGoals = saleMoneyGoalsByDate.get(j);
                if(bizmonth.equals(saleMoneyGoals.getDate())){
                    saleMoneyMonthDTO.setSaleMoneyGoal(saleMoneyGoals.getSaleMoney() == null ? BigDecimal.ZERO : saleMoneyGoals.getSaleMoney());//营业额目标
                    //营业额完成率
                    if(saleMoneyMonthDTO.getSaleMoney() == null || saleMoneyMonthDTO.getSaleMoney().compareTo(BigDecimal.ZERO) == 0){
                        saleMoneyMonthDTO.setFinishMoneyRate(BigDecimal.ZERO);
                    }else if(saleMoneyMonthDTO.getSaleMoneyGoal() == null || saleMoneyMonthDTO.getSaleMoneyGoal().compareTo(BigDecimal.ZERO) == 0){
                        saleMoneyMonthDTO.setFinishMoneyRate(BigDecimal.ZERO);
                    }else{
                        BigDecimal divide = saleMoneyMonthDTO.getSaleMoney().divide(saleMoneyMonthDTO.getSaleMoneyGoal(), 4, ROUND_HALF_DOWN);
                        BigDecimal product = new BigDecimal(100);
                        saleMoneyMonthDTO.setFinishMoneyRate(divide.multiply(product));
                    }
                }
            }
            //同比增长率
            for (int j = 0; j <beforeRevenueByBizmonth.size() ; j++) {
                BranchBizRevenueSummary branchBizRevenueSummary = beforeRevenueByBizmonth.get(j);
                if(bizmonth.equals(branchBizRevenueSummary.getBiz())){
                    BigDecimal saleMoney = saleMoneyMonthDTO.getSaleMoney();//本期销售额
                    BigDecimal bizMoney = branchBizRevenueSummary.getBizMoney();//同期销售额
                    //设置同期营业额
                    saleMoneyMonthDTO.setBeforeSaleMoney(bizMoney == null ? BigDecimal.ZERO : bizMoney);
                    //计算同比增长率   （本期-同期）/同期
                    if(bizMoney == null || bizMoney.compareTo(BigDecimal.ZERO) == 0){
                        saleMoneyMonthDTO.setAddRate(BigDecimal.ZERO);
                    }else{
                        //同比增长率
                        BigDecimal divide = (saleMoney.subtract(bizMoney)).divide(bizMoney, 4, ROUND_HALF_DOWN);
                        BigDecimal product = new BigDecimal(100);
                        saleMoneyMonthDTO.setAddRate(divide.multiply(product));
                    }
                }
            }
            list.add(saleMoneyMonthDTO);
        }
        return list;
    }

}
