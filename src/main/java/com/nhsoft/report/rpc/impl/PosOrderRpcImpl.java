package com.nhsoft.report.rpc.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.nhsoft.module.report.dto.BranchBizRevenueSummary;
import com.nhsoft.module.report.dto.BranchRevenueReport;
import com.nhsoft.module.report.rpc.PosOrderRpc;
import com.nhsoft.report.service.PosOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Component
public class PosOrderRpcImpl implements PosOrderRpc {

    @Autowired
    private PosOrderService posOrderService;

    @Override
    public List<BranchRevenueReport> findRevenueByBranch(String systemBookCode, List<Integer> branchNums, String queryBy, Date dateFrom, Date dateTo, Boolean isMember) {
        List<Object[]> objects = posOrderService.findRevenueByBranch(systemBookCode, branchNums, queryBy, dateFrom, dateTo,isMember);
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
    public List<BranchBizRevenueSummary> findRevenueByBizday(String systemBookCode, List<Integer> branchNums, String queryBy, Date dateFrom, Date dateTo, Boolean isMember) {

        List<Object[]> objects = posOrderService.findRevenueByBizday(systemBookCode, branchNums, queryBy, dateFrom, dateTo, isMember);
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
