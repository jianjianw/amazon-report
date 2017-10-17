package com.nhsoft.report.rpc.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.nhsoft.report.dto.BranchConsumeReport;
import com.nhsoft.report.dto.BranchDepositReport;
import com.nhsoft.report.rpc.CardDepositRpc;
import com.nhsoft.report.service.CardDepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
@Component
public class CardDepositRpcImpl implements CardDepositRpc{

    @Autowired
    private CardDepositService cardDepositService;
    @Override
    public List<BranchDepositReport> findBranchSum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
        List<Object[]> objects = cardDepositService.findBranchSum(systemBookCode, branchNums, dateFrom, dateTo);
        List<BranchDepositReport> list = new ArrayList<>();
        if(objects.isEmpty()){
            return list;
        }
        for (int i = 0; i <objects.size() ; i++) {
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
