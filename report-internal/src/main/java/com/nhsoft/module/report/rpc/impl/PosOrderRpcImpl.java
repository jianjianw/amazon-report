package com.nhsoft.module.report.rpc.impl;


import com.nhsoft.amazon.server.dto.OrderQueryDTO;
import com.nhsoft.amazon.server.dto.OrderReportDTO;
import com.nhsoft.amazon.server.remote.service.PosOrderRemoteService;
import com.nhsoft.module.report.dto.BranchBizRevenueSummary;
import com.nhsoft.module.report.dto.BranchRevenueReport;
import com.nhsoft.module.report.model.SystemBook;
import com.nhsoft.module.report.rpc.PosOrderRpc;
import com.nhsoft.module.report.service.PosOrderService;
import com.nhsoft.module.report.service.SystemBookService;
import com.nhsoft.module.report.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
@Component
public class PosOrderRpcImpl implements PosOrderRpc {

    @Autowired
    private PosOrderService posOrderService;
    @Autowired
    public SystemBookService systemBookService;
    @Autowired
    private PosOrderRemoteService posOrderRemoteService;


    public List<Object[]> goBigCenterBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, int type){
        SystemBook systemBook = systemBookService.readInCache(systemBookCode);
        Date now = Calendar.getInstance().getTime();
        now = DateUtil.getMinOfDate(now);
        if (dateTo.compareTo(now) >= 0) {
            dateTo = now;
        }

        Date deleteDate = systemBook.getDeleteDate();
        dateFrom = DateUtil.getMinOfDate(dateFrom);
        if (deleteDate != null && dateFrom.compareTo(deleteDate) < 0) {
            dateFrom = deleteDate;
        }
        dateTo = DateUtil.getMaxOfDate(dateTo);
        BigDecimal value1;
        BigDecimal value2;
        Date dpcLimitTime = DateUtil.addDay(now, -2);

        if (dpcLimitTime.compareTo(dateFrom) > 0 && systemBook.getBookReadDpc() != null && systemBook.getBookReadDpc()) {

            if (dpcLimitTime.compareTo(dateTo) > 0) {
                OrderQueryDTO orderQueryDTO = new OrderQueryDTO();
                orderQueryDTO.setSystemBookCode(systemBookCode);
                orderQueryDTO.setBranchNum(branchNums);
                orderQueryDTO.setDateFrom(dateFrom);
                orderQueryDTO.setDateTo(dateTo);
                List<OrderReportDTO> list = posOrderRemoteService.findBranchSummary(orderQueryDTO);
                List<Object[]> returnList = new ArrayList<Object[]>();
                for (int i = 0; i < list.size(); i++) {
                    Object[] objects = new Object[4];

                    objects[0] = list.get(i).getBranchNum();
                    /*objects[1] = list.get(i).getBizday();//营业额
                    objects[2] = list.get(i).getOrderCount();//客单量
                    objects[3] = list.get(i).getProfit();//毛利*/
                    if(type == 0){
                        objects[2] = list.get(i).getPaymentMoney().add(list.get(i).getCouponTotalMoney())
                                .subtract(list.get(i).getMgrDiscount());

                    } else if(type == 1){
                        objects[2] = BigDecimal.valueOf(list.get(i).getOrderCount());
                    } else if(type == 2){
                        value1 = list.get(i).getPaymentMoney().add(list.get(i).getCouponTotalMoney())
                                .subtract(list.get(i).getMgrDiscount());
                        value2 = BigDecimal.valueOf(list.get(i).getOrderCount());
                        if(value2.compareTo(BigDecimal.ZERO) > 0){
                            objects[2] = value1.divide(value2, 2, BigDecimal.ROUND_HALF_UP);
                        } else {
                            objects[2] = BigDecimal.ZERO;
                        }

                    } else if(type == 5){
                        objects[2] = list.get(i).getProfit();
                    } else if(type == 6){
                        value1 = list.get(i).getPaymentMoney().add(list.get(i).getCouponTotalMoney())
                                .subtract(list.get(i).getMgrDiscount());
                        value2 = list.get(i).getProfit();
                        if(value1.compareTo(BigDecimal.ZERO) > 0){
                            objects[2] = BigDecimal.ZERO;
                        } else {
                            objects[2] = value2.divide(value1, 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100));;
                        }
                        objects[3] = value1;
                        objects[4] = value2;

                    }
                    returnList.add(objects);
                }
                return returnList;
            } else {

                /*OrderQueryDTO orderQueryDTO = new OrderQueryDTO();
                orderQueryDTO.setSystemBookCode(systemBookCode);
                orderQueryDTO.setBranchNum(branchNums);
                orderQueryDTO.setDateFrom(dateFrom);
                orderQueryDTO.setDateTo(DateUtil.addDay(dpcLimitTime, -1));
                List<OrderReportDTO> list = posOrderRemoteService.findBranchDaySummary(orderQueryDTO);
                List<Object[]> returnList = new ArrayList<Object[]>();
                for (int i = 0; i < list.size(); i++) {
                    Object[] objects = new Object[5];
                    objects[0] = list.get(i).getBranchNum();
                    objects[1] = list.get(i).getBizday();
                    objects[2] = list.get(i).getPaymentMoney().add(list.get(i).getCouponTotalMoney())
                            .subtract(list.get(i).getMgrDiscount());
                    objects[3] = list.get(i).getOrderCount();
                    objects[4] = list.get(i).getProfit();
                    returnList.add(objects);
                }
                List<Object[]> localObjects = reportDao.findDayWholes(systemBookCode, branchNums, dpcLimitTime, dateTo, false);

                boolean find = false;
                for (int i = 0; i < localObjects.size(); i++) {
                    Object[] localObject = localObjects.get(i);

                    find = false;
                    for (int j = 0; j < returnList.size(); j++) {
                        Object[] objects = returnList.get(j);
                        if (objects[0].equals(localObject[0]) && objects[1].equals(localObject[1])) {
                            objects[2] = ((BigDecimal) objects[2]).add((BigDecimal) localObject[2]);
                            objects[3] = (Integer) objects[3] + (Integer) localObject[3];
                            objects[4] = ((BigDecimal) objects[4]).add((BigDecimal) localObject[4]);
                            find = true;
                            break;
                        }
                    }

                    if (!find) {
                        returnList.add(localObject);
                    }
                }
                if(type > 0){
                    Object[] object = null;
                    for(int i = 0;i < returnList.size();i++){
                        object = returnList.get(i);
                        if(type == 1){
                            value1 = object[3] == null?BigDecimal.ZERO:BigDecimal.valueOf((Integer)object[3]);

                            object[2] = value1;


                        } else if(type == 2){
                            value1 = object[3] == null?BigDecimal.ZERO:BigDecimal.valueOf((Integer)object[3]);
                            value2 = object[2] == null?BigDecimal.ZERO:(BigDecimal)object[2];

                            if(value1.compareTo(BigDecimal.ZERO) > 0){
                                object[2] = value2.divide(value1, 2, BigDecimal.ROUND_HALF_UP);
                            }
                        } else if(type == 5){
                            object[2] = object[4];
                        } else if(type == 6){
                            value1 = object[2] == null?BigDecimal.ZERO:(BigDecimal)object[2];
                            value2 = object[4] == null?BigDecimal.ZERO:(BigDecimal)object[4];

                            if(value1.compareTo(BigDecimal.ZERO) > 0){
                                object[2] = value2.divide(value1, 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100));
                            }
                            object[3] = value1;
                            object[4] = value2;
                        }
                    }
                }
                return returnList;*/
            }
        }
        return null;
    }

    public List goBigCenterDay(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, int type){

        return null;
    }

    public List goBigCenterMonth(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, int type){

        return null;
    }





    @Override
    @Cacheable(value="serviceCache")
    public List<BranchRevenueReport> findMoneyBranchSummary(String systemBookCode, List<Integer> branchNums, String queryBy, Date dateFrom, Date dateTo, Boolean isMember) {
        List<Object[]> objects = posOrderService.findMoneyBranchSummary(systemBookCode, branchNums, queryBy, dateFrom, dateTo,isMember);
        List<BranchRevenueReport> list = new ArrayList<BranchRevenueReport>();
        if(objects.isEmpty()){
            return list;
        }
        for (int i = 0; i <objects.size() ; i++) {
            Object[] object = objects.get(i);
            BranchRevenueReport branchRevenueReport = new BranchRevenueReport();
            branchRevenueReport.setBranchNum((Integer) object[0]);
            branchRevenueReport.setBizMoney((BigDecimal) object[1]);
            branchRevenueReport.setOrderCount((Integer) object[2]);
            branchRevenueReport.setProfit((BigDecimal) object[3]);
            list.add(branchRevenueReport);

        }
        return list;
    }

    @Override
    public List<BranchBizRevenueSummary> findMoneyBizdaySummary(String systemBookCode, List<Integer> branchNums, String queryBy, Date dateFrom, Date dateTo, Boolean isMember) {

        List<Object[]> objects = posOrderService.findMoneyBizdaySummary(systemBookCode, branchNums, queryBy, dateFrom, dateTo, isMember);
        List<BranchBizRevenueSummary> list = new ArrayList<>();
        if(objects.isEmpty()){
            return list;
        }
        for (int i = 0; i <objects.size() ; i++) {
            Object[] object = objects.get(i);
            BranchBizRevenueSummary branchBizRevenueSummary = new BranchBizRevenueSummary();
            branchBizRevenueSummary.setBiz((String) object[0]);
            branchBizRevenueSummary.setBizMoney((BigDecimal) object[1]);
            branchBizRevenueSummary.setOrderCount((Integer) object[2]);
            branchBizRevenueSummary.setProfit((BigDecimal) object[3]);
            list.add(branchBizRevenueSummary);
        }
        return list;
    }

    @Override
    public List<BranchBizRevenueSummary> findMoneyBizmonthSummary(String systemBookCode, List<Integer> branchNums, String queryBy, Date dateFrom, Date dateTo, Boolean isMember) {
        List<Object[]> objects = posOrderService.findMoneyBizmonthSummary(systemBookCode, branchNums, queryBy, dateFrom, dateTo, isMember);
        List<BranchBizRevenueSummary> list = new ArrayList<>();
        if(objects.isEmpty()){
            return list;
        }
        for (int i = 0; i <objects.size() ; i++) {
            Object[] object = objects.get(i);
            BranchBizRevenueSummary branchBizRevenueSummary = new BranchBizRevenueSummary();
            branchBizRevenueSummary.setBiz((String) object[0]);
            branchBizRevenueSummary.setBizMoney((BigDecimal) object[1]);
            branchBizRevenueSummary.setOrderCount((Integer) object[2]);
            branchBizRevenueSummary.setProfit((BigDecimal) object[3]);
            list.add(branchBizRevenueSummary);
        }
        return list;
    }
}
