package com.nhsoft.report.rpc.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.nhsoft.report.dto.BranchMoneyReport;
import com.nhsoft.report.rpc.PosOrderRpc;
import com.nhsoft.report.service.PosOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
@Component
public class PosOrderRpcImpl implements PosOrderRpc {

    @Autowired
    private PosOrderService posOrderService;

    @Override
    public List<BranchMoneyReport> findMoneyByBranch(String systemBookCode, List<Integer> branchNums, String queryBy, Date dateFrom, Date dateTo, Boolean isMember) {
        List<Object[]> objects = posOrderService.findMoneyByBranch(systemBookCode, branchNums, queryBy, dateFrom, dateTo,isMember);
        List<BranchMoneyReport> list = new ArrayList<BranchMoneyReport>();
        if(objects.isEmpty()){
            return list;
        }
        for (int i = 0; i <objects.size() ; i++) {
            Object[] object = objects.get(i);
            BranchMoneyReport branchMoneyReport = new BranchMoneyReport();
            branchMoneyReport.setBranchNum((Integer) object[0]);
            branchMoneyReport.setBizMoney((BigDecimal) object[1]);
            branchMoneyReport.setOrderCount((Integer) object[2]);
            branchMoneyReport.setProfit((BigDecimal) object[3]);
            list.add(branchMoneyReport);

        }
        return list;
    }
}
