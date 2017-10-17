package com.nhsoft.report.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhsoft.report.api.dto.OperationStoreDTO;
import com.nhsoft.report.dao.impl.TransferOutMoney;
import com.nhsoft.report.dto.*;
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
    public List<OperationStoreDTO> test(@RequestHeader("systemBookCode") String systemBookCode,
                                        @RequestHeader("branchNums") String branchNums, @RequestHeader("date") String date) {


        List<Integer> bannchNumList = new ArrayList<>();
        String replace = branchNums.replace("[", "").replace("]","");
        String[] split = replace.split(",");
        for (int i = 0; i <split.length ; i++) {
            bannchNumList.add(Integer.parseInt(split[i]));
        }
        Date dateFrom = null;
        Date dateTo = null;
        //营业额目标（查询时间类型）
        String dateType = null;
        try {
            if(date.length() == 7){
                dateType =  AppConstants.BUSINESS_DATE_SOME_MONTH;
                //按月份查
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                dateTo = sdf.parse(date);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dateTo);
                int actualMinimum = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);

                String from = date + "-01";
                String to = date+actualMinimum;


            }else if(date.length() == 10){
                dateType = AppConstants.BUSINESS_DATE_SOME_DATE;
                //按天查
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                dateTo = sdf.parse(date);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dateTo);
                calendar.add(Calendar.DAY_OF_MONTH,1);
                dateFrom = calendar.getTime();
            }else {
                //按周查
                dateType =  AppConstants.BUSINESS_DATE_SOME_WEEK;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                dateFrom = sdf.parse(date.substring(0, 11));
                dateTo = sdf.parse(date.substring(11, date.length()));
            }
        } catch (ParseException e) {
            logger.error("日期解析失败");
        }


        List<OperationStoreDTO> list = new ArrayList<>();
        //按分店查询数据
        //营业额
        List<BranchMoneyReport> moneyByBranch = posOrderRpc.findMoneyByBranch(systemBookCode, bannchNumList, AppConstants.BUSINESS_TREND_PAYMENT, dateFrom, dateTo, false);
        //会员营业额
        List<BranchMoneyReport> memberMoneyByBranch = posOrderRpc.findMoneyByBranch(systemBookCode, bannchNumList, AppConstants.BUSINESS_TREND_PAYMENT, dateFrom, dateTo, true);
        //卡存款
        List<BranchDepositReport> depositByBranch = cardDepositRpc.findBranchSum(systemBookCode, bannchNumList, dateFrom, dateTo);
        //卡消费
        List<BranchConsumeReport> consumeByBranch = cardConsumeRpc.findBranchSum(systemBookCode, bannchNumList, dateFrom, dateTo);
        //bannchNumList
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
        for (int i = 0; i < bannchNumList.size(); i++) {
            OperationStoreDTO store = new OperationStoreDTO();
            store.setBranchNum(bannchNumList.get(i));

            Iterator money = moneyByBranch.iterator();
            while (money.hasNext()) {
                BranchMoneyReport next = (BranchMoneyReport) money.next();
                if (store.getBranchNum().equals(next.getBranchNum())) {
                    store.setBranchNum(next.getBranchNum());        //分店号
                    store.setRevenue(next.getBizMoney());           //营业额
                    store.setGrossProfit(next.getProfit());         //毛利
                    store.setAveBillNums(next.getOrderCount() / day);    //日均客单量
                    store.setBill(next.getBizMoney().divide(new BigDecimal(next.getOrderCount()), 4, ROUND_HALF_DOWN));//客单价
                    break;
                }
            }
            Iterator memberMoney = memberMoneyByBranch.iterator();
            while (memberMoney.hasNext()) {
                BranchMoneyReport next = (BranchMoneyReport) memberMoney.next();
                if (store.getBranchNum().equals(next.getBranchNum())) {
                    store.setMemberBillNums(next.getOrderCount());    //会员客单量
                    store.setMemberBill(next.getBizMoney().divide(new BigDecimal(next.getOrderCount()), 4, ROUND_HALF_DOWN));//会员客单价
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
                    store.setStorageConsumeOccupy(store.getCardStorage().divide(store.getCartStorageConsume(), 4, ROUND_HALF_DOWN));//存储消费占比
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
                    if (next.getSaleMoney().compareTo(BigDecimal.ZERO) == 0) {
                        store.setRealizeRate1(BigDecimal.ZERO);
                    } else {
                        store.setRealizeRate1(store.getRevenue().divide(next.getSaleMoney())); //营业额目标   完成率
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
                        store.setAreaEfficiency(store.getRevenue() == null ? BigDecimal.ZERO : store.getRevenue().divide(next.getArea(), 4, ROUND_HALF_DOWN));//坪效
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

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getRevenue() == null) {
                list.remove(i);
            }
        }
        return list;
    }
}
