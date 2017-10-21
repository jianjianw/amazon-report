package com.nhsoft.report.api;

import com.nhsoft.report.api.dto.OperationRegionDTO;
import com.nhsoft.report.api.dto.OperationStoreDTO;
import com.nhsoft.report.dao.impl.TransferOutMoney;
import com.nhsoft.report.dto.*;
import com.nhsoft.report.model.Branch;
import com.nhsoft.report.model.BranchRegion;
import com.nhsoft.report.rpc.*;
import com.nhsoft.report.util.AppConstants;
import com.nhsoft.report.util.DateUtil;

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
@RequestMapping("/testApi")
public class TestApi {

    private static final Logger logger = LoggerFactory.getLogger(TestApi.class);

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


    @RequestMapping(method = RequestMethod.GET, value = "/store")
    public List<OperationStoreDTO> byBranch(@RequestHeader("systemBookCode") String systemBookCode,
        @RequestHeader("branchNums") String branchNums, @RequestHeader("date") String date) {

        List<Integer> bannchNumList = new ArrayList<>();
        if(branchNums != null){
            String replace = branchNums.replace("[", "").replace("]","").replace(" ","");
            String[] split = replace.split(",");
            for (int i = 0; i <split.length ; i++) {
                bannchNumList.add(Integer.parseInt(split[i]));
            }
        }
        Date growthDateFrom = null;
        Date growthDateTo = null;
        Date dateFrom = null;
        Date dateTo = null;
        //营业额目标（查询时间类型）       
        String dateType = null;
        Calendar calendar = Calendar.getInstance();
        try {
            if(date.length() == 7){
                dateType =  AppConstants.BUSINESS_DATE_SOME_MONTH;
                //按月份查
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                dateFrom = sdf.parse(date);
                calendar.setTime(dateFrom);
                calendar.add(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH)-1);
                dateTo = calendar.getTime();
                //上个月的时间
                calendar.setTime(dateFrom);
                calendar.add(Calendar.MONTH,-1);
                growthDateFrom = calendar.getTime();
                calendar.setTime(growthDateFrom);
                calendar.add(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH)-1);
                growthDateTo = calendar.getTime();
            }else if(date.length() == 10){
                dateType = AppConstants.BUSINESS_DATE_SOME_DATE;
                //按天查
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                dateFrom = sdf.parse(date);
                dateTo = dateFrom;
                //昨天的时间
                calendar.setTime(dateFrom);
                calendar.add(Calendar.DAY_OF_MONTH,-1);
                growthDateFrom = calendar.getTime();  //昨天
                growthDateTo = growthDateFrom;
            }else {
                //按周查
                dateType =  AppConstants.BUSINESS_DATE_SOME_WEEK;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                dateFrom = sdf.parse(date.substring(0, 11));
                dateTo = sdf.parse(date.substring(11, date.length()));
                //上周的时间
                calendar.setTime(dateFrom);
                calendar.add(Calendar.DAY_OF_MONTH,-1);
                growthDateTo = calendar.getTime();
                calendar.setTime(growthDateTo);
                calendar.add(Calendar.DAY_OF_MONTH,-6);
                growthDateFrom = calendar.getTime();
            }
        } catch (ParseException e) {
            logger.error("日期解析失败");
        }


        List<OperationStoreDTO> list = new ArrayList<>();

        //用于计算环比增长率
        List<BranchMoneyReport> growthMoneyByBranch = posOrderRpc.findMoneyByBranch(systemBookCode, bannchNumList, AppConstants.BUSINESS_TREND_PAYMENT, growthDateFrom, growthDateTo, false);
        //营业额
        List<BranchMoneyReport> moneyByBranch = posOrderRpc.findMoneyByBranch(systemBookCode, bannchNumList, AppConstants.BUSINESS_TREND_PAYMENT, dateFrom, dateTo, false);
        //会员营业额
        List<BranchMoneyReport> memberMoneyByBranch = posOrderRpc.findMoneyByBranch(systemBookCode, bannchNumList, AppConstants.BUSINESS_TREND_PAYMENT, dateFrom, dateTo, true);
        //卡存款
        List<BranchDepositReport> depositByBranch = cardDepositRpc.findBranchSum(systemBookCode, bannchNumList, dateFrom, dateTo);
        //卡消费
        List<BranchConsumeReport> consumeByBranch = cardConsumeRpc.findBranchSum(systemBookCode, bannchNumList, dateFrom, dateTo);
        //配送金额
        List<TransferOutMoney> transferOutMoneyByBranch = transferOutOrderRpc.findTransferOutMoneyByBranch(systemBookCode, bannchNumList, dateFrom, dateTo);
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
        if(day == 0){
            day = 1;
        }
        BigDecimal day_ = new BigDecimal(day);//包装日期
        for (int i = 0; i < bannchNumList.size(); i++) {
            OperationStoreDTO store = new OperationStoreDTO();
            store.setBranchNum(bannchNumList.get(i));

            //上期的营业额
            Iterator growth = growthMoneyByBranch.iterator();
            while (growth.hasNext()) {
                BranchMoneyReport next = (BranchMoneyReport) growth.next();
                if (store.getBranchNum().equals(next.getBranchNum())) {
                    store.setGrowthOf(next.getBizMoney());//上期营业额，先暂存到增长率，为了下面的计算
                    break;
                }
            }

            Iterator money = moneyByBranch.iterator();
            while (money.hasNext()) {
                BranchMoneyReport next = (BranchMoneyReport) money.next();
                if (store.getBranchNum().equals(next.getBranchNum())) {
                    store.setBranchNum(next.getBranchNum());        //分店号
                    store.setRevenue(next.getBizMoney());           //营业额
                    store.setGrossProfit(next.getProfit());         //毛利
                    store.setAveBillNums(new BigDecimal(next.getOrderCount()).divide(day_,2, ROUND_HALF_DOWN));    //日均客单量
                    store.setBill(next.getBizMoney().divide(new BigDecimal(next.getOrderCount()), 2, ROUND_HALF_DOWN));//客单价
                    //本期营业额-上期营业额
                    BigDecimal subtract = store.getRevenue().subtract(store.getGrowthOf() == null? BigDecimal.ZERO:store.getGrowthOf());
                    //环比增长率
                    if(store.getGrowthOf() == null){
                        store.setGrowthOf(BigDecimal.ZERO);
                    }else{
                        store.setGrowthOf(subtract.divide(store.getGrowthOf(),2, ROUND_HALF_DOWN));//（今年6月的销售额 - 今年5月的销售额相比）/ 今年5月的销售额相比 （本期-上期）/上期
                    }
                    break;
                }
            }

            Iterator memberMoney = memberMoneyByBranch.iterator();
            while (memberMoney.hasNext()) {
                BranchMoneyReport next = (BranchMoneyReport) memberMoney.next();
                if (store.getBranchNum().equals(next.getBranchNum())) {
                    store.setMemberBillNums(next.getOrderCount());    //会员客单量
                    store.setMemberBill(next.getBizMoney().divide(new BigDecimal(next.getOrderCount()), 2, ROUND_HALF_DOWN));//会员客单价
                    break;
                }
            }
            Iterator deposit = depositByBranch.iterator();
            while (deposit.hasNext()) {
                BranchDepositReport next = (BranchDepositReport) deposit.next();
                if (store.getBranchNum().equals(next.getBranchNum())) {
                    store.setCardStorage(next.getDeposit());//卡存款
                    break;
                }
            }
            Iterator consume = consumeByBranch.iterator();
            while (consume.hasNext()) {
                BranchConsumeReport next = (BranchConsumeReport) consume.next();
                if (store.getBranchNum().equals(next.getBranchNum())) {
                    store.setCartStorageConsume(next.getConsume());//卡消费金额
                    store.setStorageConsumeOccupy(store.getCardStorage().divide(store.getCartStorageConsume(), 2, ROUND_HALF_DOWN));//存储消费占比
                    break;
                }
            }
            Iterator transferOut = transferOutMoneyByBranch.iterator();
            while (transferOut.hasNext()) {
                TransferOutMoney next = (TransferOutMoney) transferOut.next();
                if (store.getBranchNum().equals(next.getBranchNum())) {
                    BigDecimal outMoney = next.getOutMoney();
                    if (store.getRevenue() == null) {
                        store.setDistributionDifferent(outMoney.subtract(BigDecimal.ZERO));//配销差额
                    } else {
                        store.setDistributionDifferent(outMoney.subtract(store.getRevenue()));//配销差额
                    }

                    break;
                }
            }
            Iterator loss = lossMoneyByBranch.iterator();
            while (loss.hasNext()) {
                LossMoneyReport next = (LossMoneyReport) loss.next();
                if (store.getBranchNum().equals(next.getBranchNum())) {
                    store.setDestroyDefferent(next.getMoney());//报损金额
                    break;
                }
            }
            Iterator check = checkMoneyByBranch.iterator();
            while (check.hasNext()) {
                CheckMoney next = (CheckMoney) check.next();
                if (store.getBranchNum().equals(next.getBranchNum())) {
                    store.setAdjustAmount(next.getMoney());//盘损金额
                    break;
                }
            }
            Iterator cardUserCount = cardUserCountByBranch.iterator();
            while (cardUserCount.hasNext()) {
                CardUserCount next = (CardUserCount) cardUserCount.next();
                if (store.getBranchNum().equals(next.getBranchNum())) {
                    store.setIncressedMember(next.getCount()); //会员新增数
                    break;
                }
            }
            Iterator saleMoneyGoals = saleMoneyGoalsByBranch.iterator();
            while (saleMoneyGoals.hasNext()) {
                SaleMoneyGoals next = (SaleMoneyGoals) saleMoneyGoals.next();
                if (store.getBranchNum().equals(next.getBranchNum())) {
                    if (next.getSaleMoney() == null || next.getSaleMoney().compareTo(BigDecimal.ZERO) == 0) {
                        store.setRealizeRate1(BigDecimal.ZERO);
                    } else {
                        store.setRealizeRate1((store.getRevenue() == null?BigDecimal.ZERO:store.getRevenue()).divide(next.getSaleMoney())); //营业额目标   完成率
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
                    } else {
                        store.setAreaEfficiency(store.getRevenue() == null ? BigDecimal.ZERO : store.getRevenue().divide(next.getArea(), 2, ROUND_HALF_DOWN));//坪效
                    }
                    break;
                }
            }
            Iterator adjustmentCause = adjustmentCauseMoneyByBranch.iterator();
            while (adjustmentCause.hasNext()) {
                AdjustmentCauseMoney next = (AdjustmentCauseMoney) adjustmentCause.next();
                if (store.getBranchNum().equals(next.getBranchNum())) {
                    store.setTest(next.getTryEat());    //试吃
                    store.setPeel(next.getFaly());      //去皮
                    store.setBreakage(next.getLoss());  //报损
                    store.setOther(next.getOther());    //其他
                    break;
                }
            }
            list.add(store);
        }

       /* for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getRevenue() == null) {
                list.remove(i);
            }
        }*/
        //循环遍历清除营业额为null的数据
        Iterator<OperationStoreDTO> iterator = list.iterator();
        while(iterator.hasNext()){
            OperationStoreDTO next = iterator.next();
            if(next.getRevenue() == null || next.getRevenue().compareTo(BigDecimal.ZERO) == 0){
                iterator.remove();
            }
        }
        System.out.println();
        return list;

    }


    @RequestMapping(method = RequestMethod.GET, value = "/region")
    public List<OperationRegionDTO> byRegion(@RequestHeader("systemBookCode") String systemBookCode,
        @RequestHeader("branchNums") String branchNums, @RequestHeader("date") String date, @RequestHeader("LoggedInUserTenantId") String LoggedInUserTenantId,
        @RequestHeader("LoggedInUserFullName") String LoggedInUserFullName){
    	System.out.println(LoggedInUserFullName);
        //按区域汇总
        List<OperationRegionDTO> list = new ArrayList<>();
        //按分店汇总
        List<OperationStoreDTO> operationStoreDTOS = byBranch(systemBookCode, null, date);
        //根据账套号查询区域
        List<BranchRegion> branchRegions = branchRpc.findBranchRegion(systemBookCode);
        //得到所有区域号
        List<Integer> regionNumList = new ArrayList<>();
        for (BranchRegion branchRegion : branchRegions){
            Integer branchRegionNum = branchRegion.getBranchRegionNum();
            regionNumList.add(branchRegionNum);
        }

        //先遍历区域，在遍历按分店返回的数据，如果分店号，相等，就将分店的数据封装到区域里面
        for (int i = 0; i <regionNumList.size() ; i++) {
            Integer regionNum = regionNumList.get(i);
            //多少个分店号就创建多少个分店
            OperationRegionDTO region = new OperationRegionDTO();
            BigDecimal revenue = BigDecimal.ZERO;  //营业额
            BigDecimal realizeRate1 = BigDecimal.ZERO;  //营业额完成率
            BigDecimal memberSalesRealizeRate = BigDecimal.ZERO;  //会员销售额完成率
            BigDecimal memeberRevenueOccupy = BigDecimal.ZERO;      //会员消费占比
            BigDecimal aveBillNums = BigDecimal.ZERO;                  //日均客单量
            BigDecimal allBillRealizeRate = BigDecimal.ZERO;        //总客单完成率
            Integer memberBillNums = 0;               //会员客单量
            BigDecimal bill = BigDecimal.ZERO;                      //客单价
            BigDecimal memberBill = BigDecimal.ZERO;                //会员客单价
            BigDecimal distributionDifferent = BigDecimal.ZERO;     //配销差额
            BigDecimal destroyDefferent = BigDecimal.ZERO;          //报损金额
            BigDecimal adjustAmount = BigDecimal.ZERO;              //盘损金额
            BigDecimal grossProfit = BigDecimal.ZERO;               //毛利金额
            BigDecimal grossProfitRate = BigDecimal.ZERO;           //毛利完成率
            Integer incressedMember = 0 ;              //新增会员数
            BigDecimal realizeRate2 = BigDecimal.ZERO;              //新增会员数完成率
            BigDecimal cardStorage = BigDecimal.ZERO;               //卡储值金额
            BigDecimal realizeRate3 = BigDecimal.ZERO;              //卡储值完成率
            BigDecimal cartStorageConsume = BigDecimal.ZERO;        //卡储值消费金额
            BigDecimal storageConsumeOccupy = BigDecimal.ZERO;      //储值消费占比
            BigDecimal growthOf = BigDecimal.ZERO;                  //环比增长
            String areaBranchNums = "[";
            //region.setRegionNum(regionNum);
            int count = 0;//计数器
            BigDecimal count_ = new BigDecimal(count);//包装计数器
            List<Branch> branchs = branchRpc.findBranchByBranchRegionNum(systemBookCode, regionNum);

            for (int j = 0; j <branchs.size() ; j++) {
                Branch branch = branchs.get(j);
                //给区域中的分店号字段赋值
                if(j == branchs.size()-1){
                    areaBranchNums+=branch.getId().getBranchNum()+"]";
                }else{
                    areaBranchNums+=branch.getId().getBranchNum()+",";
                }
                for (int k = 0; k <operationStoreDTOS.size() ; k++) {
                    OperationStoreDTO storeDTO = operationStoreDTOS.get(k);
                    if(branch.getId().getBranchNum().equals(storeDTO.getBranchNum())){
                        count++;//每加入一个分店数据，count就加1
                        revenue = revenue.add(storeDTO.getRevenue()); //营业额
                        realizeRate1 = realizeRate1.add(storeDTO.getRealizeRate1()==null?BigDecimal.ZERO:storeDTO.getRealizeRate1());   //营业额完成率
                        memberSalesRealizeRate = memberSalesRealizeRate.add(storeDTO.getMemberSalesRealizeRate()==null?BigDecimal.ZERO:storeDTO.getMemberSalesRealizeRate());   //会员销售额完成率
                        memeberRevenueOccupy = memeberRevenueOccupy.add(storeDTO.getMemeberRevenueOccupy()==null?BigDecimal.ZERO:storeDTO.getMemeberRevenueOccupy());       //会员消费占比
                        aveBillNums = aveBillNums.add(storeDTO.getAveBillNums()==null?BigDecimal.ZERO:storeDTO.getAveBillNums());                   //日均客单量
                        allBillRealizeRate = allBillRealizeRate.add(storeDTO.getAllBillRealizeRate()==null?BigDecimal.ZERO:storeDTO.getAllBillRealizeRate());           //总客单完成率
                        memberBillNums+=storeDTO.getMemberBillNums()==null?0:storeDTO.getMemberBillNums();                       //会员客单量
                        bill = bill.add(storeDTO.getBill()==null?BigDecimal.ZERO:storeDTO.getBill());                                //客单价
                        memberBill = memberBill.add(storeDTO.getMemberBill()==null?BigDecimal.ZERO:storeDTO.getMemberBill());              //会员客单价
                        distributionDifferent = distributionDifferent.add(storeDTO.getDistributionDifferent()==null?BigDecimal.ZERO:storeDTO.getDistributionDifferent()); //配销差额
                        destroyDefferent = destroyDefferent.add(storeDTO.getDestroyDefferent()==null?BigDecimal.ZERO:storeDTO.getDestroyDefferent());                //报损金额
                        adjustAmount = adjustAmount.add(storeDTO.getAdjustAmount()==null?BigDecimal.ZERO:storeDTO.getAdjustAmount());                            //盘损金额
                        grossProfit = grossProfit.add(storeDTO.getGrossProfit()==null?BigDecimal.ZERO:storeDTO.getGrossProfit());                               //毛利金额
                        grossProfitRate = grossProfitRate.add(storeDTO.getGrossProfitRate()==null?BigDecimal.ZERO:storeDTO.getGrossProfitRate());                   //毛利完成率
                        incressedMember+=storeDTO.getIncressedMember()==null?0:storeDTO.getIncressedMember();     //新增会员数
                        realizeRate2 = realizeRate2.add(storeDTO.getRealizeRate2()==null?BigDecimal.ZERO:storeDTO.getRealizeRate2());        //新增会员数完成率
                        cardStorage = cardStorage.add(storeDTO.getCardStorage()==null?BigDecimal.ZERO:storeDTO.getCardStorage());           //卡储值金额
                        realizeRate3 = realizeRate3.add(storeDTO.getRealizeRate3()==null?BigDecimal.ZERO:storeDTO.getRealizeRate3());        //卡储值完成率
                        cartStorageConsume = cartStorageConsume.add(storeDTO.getCartStorageConsume()==null?BigDecimal.ZERO:storeDTO.getCartStorageConsume());      //卡储值消费金额
                        storageConsumeOccupy = storageConsumeOccupy.add(storeDTO.getStorageConsumeOccupy()==null?BigDecimal.ZERO:storeDTO.getStorageConsumeOccupy());    //储值消费占比
                        growthOf = growthOf.add(storeDTO.getGrowthOf()==null?BigDecimal.ZERO:storeDTO.getGrowthOf());         //环比增长率
                    }
                }
            }
            if(count_.compareTo(BigDecimal.ZERO) == 0){
                count_ = count_.add(new BigDecimal(1));
            }
            region.setRevenue(revenue);
            //如果区域下面没分店 areaBranchNums会等于 ]
            if(areaBranchNums.length() == 1){
                areaBranchNums = areaBranchNums+"]";
            }
            //region.setArea();
            region.setAreaBranchNums(areaBranchNums);//区域包含的分店
            region.setRealizeRate1(realizeRate1.divide(count_, 2, ROUND_HALF_DOWN));
            region.setMemberSalesRealizeRate(memberSalesRealizeRate.divide(count_,2,ROUND_HALF_DOWN));
            region.setMemeberRevenueOccupy(memeberRevenueOccupy.divide(count_,2,ROUND_HALF_DOWN));
            region.setAveBillNums(aveBillNums.divide(count_,2,ROUND_HALF_DOWN));
            region.setAllBillRealizeRate(allBillRealizeRate.divide(count_,2,ROUND_HALF_DOWN));
            region.setMemberBillNums(memberBillNums);
            region.setBill(bill);
            region.setMemberBill(memberBill);
            region.setDistributionDifferent(distributionDifferent);
            region.setDestroyDefferent(destroyDefferent);
            region.setAdjustAmount(adjustAmount);
            region.setGrossProfit(grossProfit);
            region.setGrossProfitRate(grossProfitRate.divide(count_,2,ROUND_HALF_DOWN));
            region.setIncressedMember(incressedMember);
            region.setRealizeRate2(realizeRate2.divide(count_,2,ROUND_HALF_DOWN));
            region.setCardStorage(cardStorage);
            region.setRealizeRate3(realizeRate3.divide(count_,2,ROUND_HALF_DOWN));
            region.setCartStorageConsume(cartStorageConsume);
            region.setStorageConsumeOccupy(storageConsumeOccupy.divide(count_,2,ROUND_HALF_DOWN));
            region.setGrowthOf(growthOf.divide(count_,2,ROUND_HALF_DOWN));
            list.add(region);
        }
        Iterator<OperationRegionDTO> iterator = list.iterator();
        while(iterator.hasNext()){
            OperationRegionDTO next = iterator.next();
            if(next.getRevenue() == null || next.getRevenue().compareTo(BigDecimal.ZERO) == 0){
                iterator.remove();
            }
        }
        return list;
    }
}
