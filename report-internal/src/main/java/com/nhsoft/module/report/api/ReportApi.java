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

import static java.math.BigDecimal.ROUND_HALF_UP;

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


    public List<Integer> stringToList(String systemBookCode, String branchNums) {

        List<Integer> bannchNumList = new ArrayList<>();
        //如果传入分店为null,就查询所有分店
        if (branchNums == null || branchNums.length() == 0 ) {
            List<BranchDTO> all = branchRpc.findAll(systemBookCode);
            for (int i = 0; i < all.size(); i++) {
                BranchDTO branchDTO = all.get(i);
                bannchNumList.add(branchDTO.getBranchNum());
            }
            return bannchNumList;
        }
        else {
            String replace = branchNums.replace("[", "").replace("]", "").replace(" ", "");
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
        //计算环比增长，上期的时间
        Date beforeDateFrom = null;
        Date beforeDateTo = null;

        Date dateFrom = null;
        Date dateTo = null;
        //营业额目标（查询时间类型）
        String dateType = null;
        Calendar calendar = Calendar.getInstance();
        //获取当前系统时间
        Date currentTime = calendar.getTime();
        try {
            if (date.length() == 7) {
                dateType = AppConstants.BUSINESS_DATE_SOME_MONTH;
                //按月份查
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                dateFrom = sdf.parse(date);
                //判断dateFrom是否为当前月
                boolean sameMonth = DateUtil.isSameMonth(currentTime, dateFrom);
                //是当前月，将dateTo设置为当前时间
                if(sameMonth == true){
                    dateTo = currentTime;
                }else{
                    calendar.setTime(dateFrom);
                    calendar.add(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH) - 1);
                    dateTo = calendar.getTime();
                }
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
                //判断当前时间是否在dateFrom和dateTo之间
                if(currentTime.getTime() >= dateFrom.getTime() && currentTime.getTime() <= dateTo.getTime()){
                    //在，就将dateTo设置为当前时间
                    dateTo = currentTime;
                }
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
        //获得当前系统时间
        //判断
        int day = DateUtil.diffDay(dateFrom, dateTo);
        //为了及计算日均客单量
        if (day == 0) {
            day = 1;
        }
        BigDecimal bigDay = new BigDecimal(day);//包装日期

        //封装返回数据
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
                    store.setAveBillNums(new BigDecimal(store.getBillNums()).divide(bigDay, 4, ROUND_HALF_UP));    //日均客单量
                    store.setBill(store.getRevenue().divide(new BigDecimal(store.getBillNums()), 4, ROUND_HALF_UP));//客单价
                    //本期营业额-上期营业额
                    BigDecimal subtract = store.getRevenue().subtract(store.getBeforeSaleMoney());
                    //环比增长率
                    if(store.getBeforeSaleMoney().compareTo(BigDecimal.ZERO) == 0){
                        store.setGrowthOf(BigDecimal.ZERO);
                    }else{
                        store.setGrowthOf(subtract.divide(store.getBeforeSaleMoney(), 4, ROUND_HALF_UP));//（本期-上期）/上期
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
                        store.setMemeberRevenueOccupy(store.getMemberSaleMoney().divide(store.getRevenue(),2,ROUND_HALF_UP));//会员销售额占比
                    }
                    store.setMemberBillNums(next.getOrderCount() == null ? 0 : next.getOrderCount());    //会员客单量
                    store.setMemberBill(next.getBizMoney().divide(new BigDecimal(next.getOrderCount()), 4, ROUND_HALF_UP));//会员客单价
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
                        store.setStorageConsumeOccupy(store.getCardStorage().divide(store.getCartStorageConsume(), 4, ROUND_HALF_UP));//存储消费占比
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
                        store.setRealizeRate1(store.getRevenue().divide(store.getSaleMoneyGoal(), 4, ROUND_HALF_UP));//营业额完成率
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
                        store.setAreaEfficiency(store.getRevenue().divide(next.getArea(), 4, ROUND_HALF_UP));//坪效
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
                region.setRealizeRate1(revenue.divide(saleMoneyGoal, 4, ROUND_HALF_UP));              //营业额完成率
            }
            region.setTransferOutMoney(transferOutMoney);//配送金额
            region.setDistributionDifferent(transferOutMoney.subtract(revenue));                                //配销差额
            region.setMemberSaleMoney(memberSaleMoney);//会员销售额
            if(revenue.compareTo(BigDecimal.ZERO) == 0){
                region.setMemeberRevenueOccupy(BigDecimal.ZERO);
            }else {
                region.setMemeberRevenueOccupy(memberSaleMoney.divide(revenue, 4, ROUND_HALF_UP));    //会员消费占比
            }
            if(memberBillNums.equals(0)){
                region.setBill(BigDecimal.ZERO);
            }else{
                region.setBill(revenue.divide(new BigDecimal(memberBillNums), 4, ROUND_HALF_UP));     //客单价
            }
            region.setBillNums(billNums);//客单量
            if(billNums == null || billNums.equals(0)){
                region.setAveBillNums(BigDecimal.ZERO);
            }else{
                BigDecimal bigBillNums = new BigDecimal(billNums);
                region.setAveBillNums(bigBillNums.divide(bigDay, 4, ROUND_HALF_UP));                                     //日均客单量
            }

            region.setMemberBillNums(memberBillNums);                                                           //会员客单量
            if(memberBill.compareTo(BigDecimal.ZERO) == 0){
                region.setMemberBill(BigDecimal.ZERO);
            }else{
                region.setMemberBill(memberSaleMoney.divide(memberBill, 4, ROUND_HALF_UP));                                       //会员客单价
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
                region.setStorageConsumeOccupy(cardStorage.divide(cartStorageConsume, 4, ROUND_HALF_UP));//储值消费占比
            }
            region.setBeforeSaleMoney(beforeSaleMoney);//上期营业额
            if(beforeSaleMoney.compareTo(BigDecimal.ZERO) == 0){
                region.setGrowthOf(BigDecimal.ZERO);
            }else{
                region.setGrowthOf((revenue.subtract(beforeSaleMoney)).divide(beforeSaleMoney, 4, ROUND_HALF_UP));//环比增长率     （本期-上期）/上期
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
        //获得系统当前时间，当前系统时间以后的数据，都显示为null
        Calendar calendar = Calendar.getInstance();
        Date currentTime = calendar.getTime();
        String currentStr = DateUtil.getDateShortStr(currentTime);
        //为了与营业日比较
        int currentInt = Integer.parseInt(currentStr);
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
            int bizdayInt = Integer.parseInt(bizday);
            TrendDailyDTO trendDaily = new TrendDailyDTO();
            trendDaily.setDay(bizday.substring(bizday.length() - 2, bizday.length()) + "日");
            if(bizdayInt>currentInt){       //营业月大于当前系统时间，将数据设置为null（不设置默认为0）
                trendDaily.setRevenue(null);
                trendDaily.setBillNums(null);
                trendDaily.setMemberRevenue(null);
                trendDaily.setMemberBillNums(null);
                trendDaily.setDistributionMoney(null);
            }else{
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
        //获得系统当前时间，当前系统时间以后的数据，都显示为null
        Calendar calendar = Calendar.getInstance();
        Date currentTime = calendar.getTime();
        String currentStr = DateUtil.getYearAndMonthString(currentTime);
        int currentInt = Integer.parseInt(currentStr);
        Date dateFrom = null;
        try {
            dateFrom = sdf.parse(date);
        } catch (ParseException e) {
            logger.error("日期解析失败");
        }
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
            int bizmonthInt = Integer.parseInt(bizmonth);
            TrendMonthlyDTO trendMonthly = new TrendMonthlyDTO();
            trendMonthly.setMonth(bizmonth.substring(bizmonth.length() - 2, bizmonth.length()) + "月");
            if(bizmonthInt>currentInt){ //营业月大于当前系统时间，将数据设置为null（不设置默认为0）
                trendMonthly.setRevenue(null);
                trendMonthly.setBillNums(null);
                trendMonthly.setGross(null);
                trendMonthly.setMemberRevenue(null);
                trendMonthly.setMemberBillNums(null);
                trendMonthly.setGross(null);
                trendMonthly.setDistributionMoney(null);
            }else{
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
            }
            list.add(trendMonthly);
        }
        return list;
    }


    //1、门店完成率排名
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
                       saleFinishMoneyTopDTO.setFinishMoneyRate(saleFinishMoneyTopDTO.getSaleMoney().divide(saleFinishMoneyTopDTO.getGoalMoney(),2,ROUND_HALF_UP));
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

    //2、区域完成率排名
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
        List<SaleMoneyGoals> saleMoneyGoalsByBranch = branchTransferGoalsRpc.findSaleMoneyGoalsByBranch(systemBookCode, bannchNumList, dateFrom, dateFrom, AppConstants.BUSINESS_DATE_SOME_DATE);
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
                            saleFinishMoneyTopDTO.setFinishMoneyRate(saleFinishMoneyTopDTO.getSaleMoney().divide(saleFinishMoneyTopDTO.getGoalMoney(),2,ROUND_HALF_UP));
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

    //年度销售分析
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
        Calendar calendar = Calendar.getInstance();
        //获得当前系统时间
        Date currentTime = calendar.getTime();
        String currentStr = DateUtil.getYearAndMonthString(currentTime);
        int currentInt = Integer.parseInt(currentStr);

        Date dateFrom = null;
        try {
            dateFrom = sdf.parse(date);
        } catch (ParseException e) {
            logger.error("日期解析失败");
        }
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
            int bizmonthInt = Integer.parseInt(bizmonth);

            SaleMoneyMonthDTO saleMoneyMonthDTO = new SaleMoneyMonthDTO();
            saleMoneyMonthDTO.setMonth(bizmonth.substring(bizmonth.length() - 2, bizmonth.length()) + "月");
            if(bizmonthInt>currentInt){//如果营业月时间大于当前系统时间，就将数据设置为null,默认是0
                saleMoneyMonthDTO.setSaleMoney(null);
                saleMoneyMonthDTO.setSaleMoneyGoal(null);
                saleMoneyMonthDTO.setFinishMoneyRate(null);
                saleMoneyMonthDTO.setAddRate(null);
                saleMoneyMonthDTO.setBeforeSaleMoney(null);
            }else{
                //营业额
                for (int j = 0; j <revenueByBizmonth.size() ; j++) {
                    BranchBizRevenueSummary branchBizRevenueSummary = revenueByBizmonth.get(j);
                    if(bizmonth.equals(branchBizRevenueSummary.getBiz())){
                        saleMoneyMonthDTO.setSaleMoney(branchBizRevenueSummary.getBizMoney() == null ? BigDecimal.ZERO : branchBizRevenueSummary.getBizMoney());//营业额
                        break;
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
                            BigDecimal divide = (saleMoneyMonthDTO.getSaleMoney()).divide(saleMoneyMonthDTO.getSaleMoneyGoal(), 4, ROUND_HALF_UP);
                            BigDecimal product = new BigDecimal(100);
                            saleMoneyMonthDTO.setFinishMoneyRate(divide.multiply(product));
                        }
                        break;
                    }
                }
                //改变年份，因为要和去年的比较
                String reMonth = bizmonth.replaceAll(date, Integer.parseInt(date)-1+"");
                //同比增长率
                for (int j = 0; j <beforeRevenueByBizmonth.size() ; j++) {
                    BranchBizRevenueSummary branchBizRevenueSummary = beforeRevenueByBizmonth.get(j);
                    if(reMonth.equals(branchBizRevenueSummary.getBiz())){

                        BigDecimal saleMoney = saleMoneyMonthDTO.getSaleMoney();//本期销售额
                        BigDecimal bizMoney = branchBizRevenueSummary.getBizMoney();//同期销售额
                        //设置同期营业额
                        saleMoneyMonthDTO.setBeforeSaleMoney(bizMoney == null ? BigDecimal.ZERO : bizMoney);
                        //计算同比增长率   （本期-同期）/同期
                        if(bizMoney == null || bizMoney.compareTo(BigDecimal.ZERO) == 0){
                            saleMoneyMonthDTO.setAddRate(BigDecimal.ZERO);
                        }else{
                            //同比增长率
                            BigDecimal divide = (saleMoney.subtract(bizMoney)).divide(bizMoney, 4, ROUND_HALF_UP);
                            BigDecimal product = new BigDecimal(100);
                            saleMoneyMonthDTO.setAddRate(divide.multiply(product));
                        }
                        break;
                    }
                }
            }
            list.add(saleMoneyMonthDTO);
        }
        return list;
    }

    //门店每日完成率排名(门店业绩完成)
    @RequestMapping(method=RequestMethod.GET ,value="/finishRateTop")
    public List<BranchFinishRateTopDTO> findFinishRateTopByBranch(@RequestHeader("systemBookCode") String systemBookCode,
                                                                  @RequestHeader("branchNums") String branchNums,
                                                                  @RequestHeader("date") String date, @RequestHeader("goal") String goal){
        String[] arrayDay = {"日","一","二","三","四","五","六"};
        Date dateFrom = DateUtil.getShortDate(date);
        Integer dayCountOfMonth = DateUtil.getDayCountOfMonth(dateFrom);//获取当月天数
        BigDecimal bigDay = new BigDecimal(dayCountOfMonth);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateFrom);
        Integer day = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        calendar.add(Calendar.DAY_OF_MONTH, -day);
        Date time = calendar.getTime();//得到上周星期天
        calendar.setTime(time);
        calendar.add(Calendar.DAY_OF_MONTH,4);//得到这周星期四
        Date thursday = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH,5);//得到这周星期五
        Date friday = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH,6);//得到这周星期六
        Date saturday = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH,7);//得到这周星期天
        Date sunday = calendar.getTime();
        //为了计算排名，不管传递几个分店都要查询所有
        List<Integer> bannchNumList = stringToList(systemBookCode, null);

        //星期四
        List<SaleMoneyGoals> thursdayMoneyGoal = branchTransferGoalsRpc.findSaleMoneyGoalsByBranch(systemBookCode, bannchNumList, thursday, thursday, AppConstants.BUSINESS_DATE_SOME_DATE);
        //星期五
        List<SaleMoneyGoals> fridayMoneyGoal = branchTransferGoalsRpc.findSaleMoneyGoalsByBranch(systemBookCode, bannchNumList, friday, friday, AppConstants.BUSINESS_DATE_SOME_DATE);
        //星期六
        List<SaleMoneyGoals> saturdayMoneyGoal = branchTransferGoalsRpc.findSaleMoneyGoalsByBranch(systemBookCode, bannchNumList, saturday, saturday, AppConstants.BUSINESS_DATE_SOME_DATE);
        //星期天
        List<SaleMoneyGoals> sundayMoneyGoal = branchTransferGoalsRpc.findSaleMoneyGoalsByBranch(systemBookCode, bannchNumList, sunday, sunday, AppConstants.BUSINESS_DATE_SOME_DATE);
        //按分店汇总营业额（当天）
        List<BranchRevenueReport> moneyByBranch = posOrderRpc.findMoneyBranchSummary(systemBookCode, bannchNumList, AppConstants.BUSINESS_TREND_PAYMENT, dateFrom, dateFrom, false);
        //按分店汇总日营业额目标（当天）
        List<SaleMoneyGoals> saleMoneyGoalsByBranch = branchTransferGoalsRpc.findSaleMoneyGoalsByBranch(systemBookCode, bannchNumList, dateFrom, dateFrom, AppConstants.BUSINESS_DATE_SOME_DATE);
        //按分店汇总月营业额目标
        List<SaleMoneyGoals> monthGoal = branchTransferGoalsRpc.findSaleMoneyGoalsByBranch(systemBookCode, bannchNumList, dateFrom, dateFrom, AppConstants.BUSINESS_DATE_SOME_MONTH);


        List<BranchFinishRateTopDTO> list = new ArrayList<>();
        for (int i = 0; i <bannchNumList.size() ; i++) {
            BranchFinishRateTopDTO  branchFinishRateTopDTO = new BranchFinishRateTopDTO();
            Integer branchNum = bannchNumList.get(i);
            BranchDTO branchDTO = branchRpc.readWithNolock(systemBookCode, branchNum);
            branchFinishRateTopDTO.setBranchNum(branchNum);
            branchFinishRateTopDTO.setBranchName(branchDTO.getBranchName());

            branchFinishRateTopDTO.setDate(date+"( 星期" + arrayDay[day] + " )");
            //星期一到星期四
            for (int j = 0; j <thursdayMoneyGoal.size() ; j++) {
                SaleMoneyGoals saleMoneyGoals = thursdayMoneyGoal.get(j);
                if(branchNum.equals(saleMoneyGoals.getBranchNum())){
                    branchFinishRateTopDTO.setMonSaleMoneyGoal(saleMoneyGoals.getSaleMoney() == null ?BigDecimal.ZERO : saleMoneyGoals.getSaleMoney());
                }
            }
            //星期五
            for (int j = 0; j <fridayMoneyGoal.size() ; j++) {
                SaleMoneyGoals saleMoneyGoals = fridayMoneyGoal.get(j);
                if(branchNum.equals(saleMoneyGoals.getBranchNum())){
                    branchFinishRateTopDTO.setFriSaleMoneyGoal(saleMoneyGoals.getSaleMoney() == null ?BigDecimal.ZERO : saleMoneyGoals.getSaleMoney());
                }
            }
            //星期六
            for (int j = 0; j <saturdayMoneyGoal.size() ; j++) {
                SaleMoneyGoals saleMoneyGoals = saturdayMoneyGoal.get(j);
                if(branchNum.equals(saleMoneyGoals.getBranchNum())){
                    branchFinishRateTopDTO.setSatSaleMoneyGoal(saleMoneyGoals.getSaleMoney() == null ? BigDecimal.ZERO : saleMoneyGoals.getSaleMoney());
                }
            }
            //星期天
            for (int j = 0; j <sundayMoneyGoal.size() ; j++) {
                SaleMoneyGoals saleMoneyGoals = sundayMoneyGoal.get(j);
                if(branchNum.equals(saleMoneyGoals.getBranchNum())){
                    branchFinishRateTopDTO.setSunSaleMoneyGoal(saleMoneyGoals.getSaleMoney() == null ? BigDecimal.ZERO : saleMoneyGoals.getSaleMoney());
                }
            }

            //按分店汇总营业额(当天营业额)
            for (int j = 0; j <moneyByBranch.size() ; j++) {
                BranchRevenueReport branchRevenueReport = moneyByBranch.get(j);
                if(branchNum.equals(branchRevenueReport.getBranchNum())){
                    branchFinishRateTopDTO.setSaleMoney(branchRevenueReport.getBizMoney() == null ? BigDecimal.ZERO : branchRevenueReport.getBizMoney() );//营业额
                    branchFinishRateTopDTO.setOrderCount(branchRevenueReport.getOrderCount() == null ? 0 : branchRevenueReport.getOrderCount());  //客单量
                    branchFinishRateTopDTO.setProfit(branchRevenueReport.getProfit() == null ? BigDecimal.ZERO : branchRevenueReport.getProfit());  //毛利
                }
            }

            //按分店汇总日营业额目标(当天的目标)
            for (int j = 0; j <saleMoneyGoalsByBranch.size() ; j++) {
                SaleMoneyGoals saleMoneyGoals = saleMoneyGoalsByBranch.get(j);
                if(branchNum.equals(saleMoneyGoals.getBranchNum())){
                    //营业额目标
                    branchFinishRateTopDTO.setSaleMoneyGoal(saleMoneyGoals.getSaleMoney() == null ? BigDecimal.ZERO : saleMoneyGoals.getSaleMoney());
                    //完成率
                    if(branchFinishRateTopDTO.getSaleMoney().compareTo(BigDecimal.ZERO) == 0 ||
                            branchFinishRateTopDTO.getSaleMoneyGoal().compareTo(BigDecimal.ZERO)  == 0){
                        branchFinishRateTopDTO.setSaleMoneyFinishRate(BigDecimal.ZERO);
                    }else{
                        branchFinishRateTopDTO.setSaleMoneyFinishRate(branchFinishRateTopDTO.getSaleMoney()
                                .divide(branchFinishRateTopDTO.getSaleMoneyGoal(),4,ROUND_HALF_UP));
                    }
                }
            }

            //按分店汇总月营业额目标
            for (int j = 0; j <monthGoal.size() ; j++) {
                SaleMoneyGoals saleMoneyGoals = monthGoal.get(j);
                if(branchNum.equals(saleMoneyGoals.getBranchNum())){
                    branchFinishRateTopDTO.setMonthSaleMoneyGoal(saleMoneyGoals.getSaleMoney() == null ? BigDecimal.ZERO : saleMoneyGoals.getSaleMoney());
                    //日均营业额目标
                    if(branchFinishRateTopDTO.getMonthSaleMoneyGoal().compareTo(BigDecimal.ZERO) == 0 ){
                        branchFinishRateTopDTO.setAvgSaleMoneyGoal(BigDecimal.ZERO);
                    }else{
                        branchFinishRateTopDTO.setAvgSaleMoneyGoal(branchFinishRateTopDTO.getMonthSaleMoneyGoal().divide(bigDay,4,ROUND_HALF_UP));
                    }
                }
            }

            list.add(branchFinishRateTopDTO);
        }
        //排序
        Collections.sort(list,Comparator.comparing(BranchFinishRateTopDTO::getSaleMoneyFinishRate));
        //得到分店号,判断要不要过滤其他分店
        if(branchNums != null && branchNums.length()>3){//查询所有分店传递额参数是"[]" lenth大于3时才会有分店传入
            String replace = branchNums.replace("[", "").replace("]", "").replace(" ", "");
            String[] branchArray = replace.split(",");
            if(branchArray.length>0){//如果前台有分店就要过滤其他分店
                Iterator<BranchFinishRateTopDTO> iterator = list.iterator();
                while(iterator.hasNext()){
                    BranchFinishRateTopDTO next = iterator.next();
                    for (int i = 0; i <branchArray.length ; i++) {
                        Integer integer = Integer.parseInt(branchArray[i]);
                        if(!integer.equals(next.getBranchNum())){
                            iterator.remove();
                        }
                    }
                }
            }
        }

        //判断有没有过滤条件
        if(goal == null || goal.equals("null") || goal.length() == 0){    //查询所有
            for (int i = 0; i <list.size() ; i++) {
                //设置排名
                BranchFinishRateTopDTO branchFinishRateTopDTO = list.get(i);
                branchFinishRateTopDTO.setTop(i+1);
            }
            return list;
        } else{
            boolean flag = false;
            BigDecimal start = null;
            BigDecimal end = null;
            if (goal.contains(">")) {   // >10000
                flag = true;
                String subGoal = goal.substring(1, goal.length());
                start = new BigDecimal(subGoal);

            } else {    //小于10000的其他区间
                String[] split = goal.split("-");
                start = new BigDecimal(split[0]);
                end = new BigDecimal(split[1]);
            }
            //移除不在指定区间内的数据
            Iterator<BranchFinishRateTopDTO> iterator = list.iterator();
            while (iterator.hasNext()) {
                BranchFinishRateTopDTO next = iterator.next();
                BigDecimal saleMoney = next.getSaleMoney();
                if(flag){//移除小于10000的数据(留下大于10000的数据)
                    if(saleMoney.compareTo(start) == -1  ){
                        iterator.remove();
                    }
                }else{
                    if (saleMoney.compareTo(start) == -1 || saleMoney.compareTo(end) == 1) {//其它区间,例如（4500-5000），
                        iterator.remove();
                    }
                }
            }
            //如果数据被全部移出，不能返回空，因为前台要拿日期数据
            BranchFinishRateTopDTO  branchFinishRateTopDTO = new BranchFinishRateTopDTO();
            branchFinishRateTopDTO.setDate(date+"( 星期" + arrayDay[day] + " )");
            list.add(branchFinishRateTopDTO);
            return list;
        }


    }

    //销售额同比增长排名
    @RequestMapping(method=RequestMethod.GET,value="/moneyAddRateDTO")
    public List<YearMoneyAddRateDTO> findBeforeAddRateTop(@RequestHeader("systemBookCode") String systemBookCode,
                                                          @RequestHeader("branchNums") String branchNums, @RequestHeader("date") String date){
        List<Integer> bannchNumList = stringToList(systemBookCode, branchNums);
        Date dateFrom = DateUtil.getShortDate(date);

        //根据当前日期获得，今天是今年的第几周,周几
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateFrom);
        int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);    //今年的第几周
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);    //本周的周几

        //根据第几周获取，同期的日期（去年第几周的日期）
        calendar.add(Calendar.YEAR,-1);//上一年
        calendar.set(Calendar.WEEK_OF_YEAR, weekOfYear); // 设置为2016年的第10周
        calendar.set(Calendar.DAY_OF_WEEK,dayOfWeek); // 1表示周日，2表示周一，7表示周六
        Date beforeDateFrom = calendar.getTime();


        //按分店查询同期营业额
        List<BranchRevenueReport> beforeMoneyByBranch = posOrderRpc.findMoneyBranchSummary(systemBookCode, bannchNumList, AppConstants.BUSINESS_TREND_PAYMENT, beforeDateFrom, beforeDateFrom, false);
        //按分店查询营业额
        List<BranchRevenueReport> moneyByBranch = posOrderRpc.findMoneyBranchSummary(systemBookCode, bannchNumList, AppConstants.BUSINESS_TREND_PAYMENT, dateFrom, dateFrom, false);

        List<YearMoneyAddRateDTO> list = new ArrayList<>();

        for (int i = 0; i <bannchNumList.size() ; i++) {
            YearMoneyAddRateDTO yearMoneyAddRateDTO = new YearMoneyAddRateDTO();
            Integer branchNum = bannchNumList.get(i);
            BranchDTO branchDTO = branchRpc.readWithNolock(systemBookCode, branchNum);
            yearMoneyAddRateDTO.setBranchNum(branchNum);
            yearMoneyAddRateDTO.setBranchName(branchDTO.getBranchName());
            //同期分店营业额
            for (int j = 0; j <beforeMoneyByBranch.size() ; j++) {
                BranchRevenueReport branchRevenueReport = beforeMoneyByBranch.get(j);
                if(branchNum.equals(branchRevenueReport.getBranchNum())){
                    yearMoneyAddRateDTO.setBeforeSaleMoney(branchRevenueReport.getBizMoney() == null ? BigDecimal.ZERO : branchRevenueReport.getBizMoney());   //营业额
                    yearMoneyAddRateDTO.setBeforeBillNum(branchRevenueReport.getOrderCount() == null ? 0 : branchRevenueReport.getOrderCount());    //客单量
                    if(yearMoneyAddRateDTO.getBeforeSaleMoney().compareTo(BigDecimal.ZERO) == 0 || yearMoneyAddRateDTO.getBeforeBillNum().equals(0)){
                        yearMoneyAddRateDTO.setBeforebillMoney(BigDecimal.ZERO);   //客单价
                    }else{
                        yearMoneyAddRateDTO.setBeforebillMoney(yearMoneyAddRateDTO.getBeforeSaleMoney().divide(new BigDecimal(yearMoneyAddRateDTO.getBeforeBillNum()),4,ROUND_HALF_UP));   //客单价
                    }
                    yearMoneyAddRateDTO.setBeforeProfit(branchRevenueReport.getProfit());//毛利
                }
            }
            //本期分店营业额
            for (int j = 0; j <moneyByBranch.size() ; j++) {
                BranchRevenueReport branchRevenueReport = moneyByBranch.get(j);
                if(branchNum.equals(branchRevenueReport.getBranchNum())){
                    yearMoneyAddRateDTO.setSaleMoney(branchRevenueReport.getBizMoney() == null ? BigDecimal.ZERO : branchRevenueReport.getBizMoney());//营业额
                    yearMoneyAddRateDTO.setBillNum(branchRevenueReport.getOrderCount() == null ? 0 : branchRevenueReport.getOrderCount());//客单量
                    if(yearMoneyAddRateDTO.getSaleMoney().compareTo(BigDecimal.ZERO) == 0 || yearMoneyAddRateDTO.getBillNum().equals(0)){
                        yearMoneyAddRateDTO.setBillMoney(BigDecimal.ZERO);
                    }else{
                        yearMoneyAddRateDTO.setBillMoney(yearMoneyAddRateDTO.getSaleMoney().divide(new BigDecimal(yearMoneyAddRateDTO.getBillNum()),4,ROUND_HALF_UP));//客单价
                    }
                    yearMoneyAddRateDTO.setProfit(branchRevenueReport.getProfit());//毛利

                    //营业额同比增长率   （本期-同期）/ 同期
                    BigDecimal saleMoney = yearMoneyAddRateDTO.getSaleMoney();//本期
                    BigDecimal beforeSaleMoney = yearMoneyAddRateDTO.getBeforeSaleMoney();//同期
                    if(beforeSaleMoney.compareTo(BigDecimal.ZERO) == 0){
                        yearMoneyAddRateDTO.setSaleMoneyAddRate(BigDecimal.ZERO);
                    }else{
                        yearMoneyAddRateDTO.setSaleMoneyAddRate((saleMoney.subtract(beforeSaleMoney)).divide(beforeSaleMoney,4,ROUND_HALF_UP));
                    }


                    //客单量同比增长率
                    BigDecimal billNum = new BigDecimal(yearMoneyAddRateDTO.getBillNum());//本期
                    BigDecimal beforeBillNum = new BigDecimal(yearMoneyAddRateDTO.getBeforeBillNum());//同期
                    if(beforeBillNum.equals(0)){
                        yearMoneyAddRateDTO.setBillNumAddRate(BigDecimal.ZERO);
                    }else{
                        yearMoneyAddRateDTO.setBillNumAddRate((billNum.subtract(beforeBillNum)).divide(beforeBillNum,4,ROUND_HALF_UP));
                    }


                    //客单价同比增长率
                    BigDecimal billMoney = yearMoneyAddRateDTO.getBillMoney();//本期
                    BigDecimal beforebillMoney = yearMoneyAddRateDTO.getBeforebillMoney();//同期
                    if(beforebillMoney.compareTo(BigDecimal.ZERO) == 0){
                        yearMoneyAddRateDTO.setBillMoneyAddRate(BigDecimal.ZERO);
                    }else{
                        yearMoneyAddRateDTO.setBillMoneyAddRate((billMoney.subtract(beforebillMoney).divide(beforebillMoney,4,ROUND_HALF_UP)));
                    }

                    //毛利同比增长率
                    BigDecimal profit = yearMoneyAddRateDTO.getProfit();//本期
                    BigDecimal beforeProfit = yearMoneyAddRateDTO.getBeforeProfit();//同期
                    if(beforeProfit.compareTo(BigDecimal.ZERO) == 0){
                        yearMoneyAddRateDTO.setBeforeProfit(BigDecimal.ZERO);
                    }
                    yearMoneyAddRateDTO.setBeforeProfit(profit.subtract(beforeProfit).divide(beforeProfit,4,ROUND_HALF_UP));

                }
            }
            list.add(yearMoneyAddRateDTO);
        }
        Collections.sort(list,Comparator.comparing(YearMoneyAddRateDTO::getSaleMoneyAddRate));
        for (int i = 0; i <list.size() ; i++) {
            YearMoneyAddRateDTO yearMoneyAddRateDTO = list.get(i);
            yearMoneyAddRateDTO.setTop(i+1);
        }
        return list;
    }
}
