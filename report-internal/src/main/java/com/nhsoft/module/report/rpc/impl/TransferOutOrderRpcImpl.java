package com.nhsoft.module.report.rpc.impl;


import com.nhsoft.module.report.dto.TransferOutMoney;
import com.nhsoft.module.report.rpc.TransferOutOrderRpc;
import com.nhsoft.module.report.service.TransferOutOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Component
public class TransferOutOrderRpcImpl implements TransferOutOrderRpc {

    @Autowired
    private TransferOutOrderService transferOutOrderService;
    @Override
    public List<TransferOutMoney> findMoneyBranchSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {

        List<Object[]> objects = transferOutOrderService.findMoneyBranchSummary(systemBookCode, branchNums, dateFrom, dateTo);
        int size = objects.size();
        List<TransferOutMoney> list = new ArrayList<TransferOutMoney>(size);
        if(objects.isEmpty()){
            return list;
        }
        for (int i = 0; i <size ; i++) {
            Object[] object = objects.get(i);
            TransferOutMoney transferOutMoney = new TransferOutMoney();
            transferOutMoney.setBranchNum((Integer) object[0]);
            transferOutMoney.setOutMoney((BigDecimal) object[1]);
            list.add(transferOutMoney);
        }
        return list;
    }

    @Override
    public List<TransferOutMoney> findMoneyBizdaySummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
        List<Object[]> objects = transferOutOrderService.findMoneyBizdaySummary(systemBookCode, branchNums, dateFrom, dateTo);
        int size = objects.size();
        List<TransferOutMoney> list = new ArrayList<>(size);
        if(objects.isEmpty()){
            return list;
        }
        for (int i = 0; i <size ; i++) {
            Object[] object = objects.get(i);
            TransferOutMoney transferOutMoney = new TransferOutMoney();
            transferOutMoney.setBiz((String) object[0]);
            transferOutMoney.setOutMoney((BigDecimal) object[1]);
            list.add(transferOutMoney);
        }
        return list;
    }

    @Override
    public List<TransferOutMoney> findMoneyBymonthSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
        List<Object[]> objects = transferOutOrderService.findMoneyBymonthSummary(systemBookCode, branchNums, dateFrom, dateTo);
        int size = objects.size();
        List<TransferOutMoney> list = new ArrayList<>(size);
        if(objects.isEmpty()){
            return list;
        }
        for (int i = 0; i <size ; i++) {
            Object[] object = objects.get(i);
            TransferOutMoney transferOutMoney = new TransferOutMoney();
            transferOutMoney.setBiz((String) object[0]);
            transferOutMoney.setOutMoney((BigDecimal) object[1]);
            list.add(transferOutMoney);
        }
        return list;
    }
}
