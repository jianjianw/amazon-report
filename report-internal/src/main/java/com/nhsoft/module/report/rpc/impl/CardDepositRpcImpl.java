package com.nhsoft.module.report.rpc.impl;


import com.nhsoft.module.report.dto.BranchDepositReport;
import com.nhsoft.module.report.rpc.CardDepositRpc;
import com.nhsoft.module.report.service.CardDepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Component
public class CardDepositRpcImpl implements CardDepositRpc{

    @Autowired
    private CardDepositService cardDepositService;
    @Override
    public List<BranchDepositReport> findBranchSum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
        List<Object[]> objects = cardDepositService.findBranchSum(systemBookCode, branchNums, dateFrom, dateTo);
        int size = objects.size();
        List<BranchDepositReport> list = new ArrayList<>(size);
        if(objects.isEmpty()){
            return list;
        }
        for (int i = 0; i <size; i++) {
            Object[] object = objects.get(i);
            BranchDepositReport branchDepositReport = new BranchDepositReport();
            branchDepositReport.setBranchNum((Integer) object[0]);
            branchDepositReport.setDepositCash((BigDecimal)object[1]);
            branchDepositReport.setDeposit((BigDecimal) object[2]);
            list.add(branchDepositReport);
        }
        return list;
    }




}
