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
    public List<TransferOutMoney> findTransferOutMoneyByBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {

        List<Object[]> objects = transferOutOrderService.findTransferOutMoneyByBranch(systemBookCode, branchNums, dateFrom, dateTo);
        List<TransferOutMoney> list = new ArrayList<TransferOutMoney>();
        if(objects.isEmpty()){
            return list;
        }
        for (int i = 0; i <objects.size() ; i++) {
            Object[] object = objects.get(i);
            TransferOutMoney transferOutMoney = new TransferOutMoney();
            transferOutMoney.setBranchNum((Integer) object[0]);
            transferOutMoney.setOutMoney((BigDecimal) object[1]);
            list.add(transferOutMoney);
        }
        return list;
    }

    @Override
    public List<TransferOutMoney> findTransferOutMoneyByBizday(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
        List<Object[]> objects = transferOutOrderService.findTransferOutMoneyByBizday(systemBookCode, branchNums, dateFrom, dateTo);
        List<TransferOutMoney> list = new ArrayList<>();
        if(objects.isEmpty()){
            return null;
        }
        for (int i = 0; i <objects.size() ; i++) {
            Object[] object = objects.get(i);
            TransferOutMoney transferOutMoney = new TransferOutMoney();
            transferOutMoney.setBiz((String) object[0]);
            transferOutMoney.setOutMoney((BigDecimal) object[1]);
            list.add(transferOutMoney);
        }
        return list;
    }

    @Override
    public List<TransferOutMoney> findTransferOutMoneyBymonth(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
        List<Object[]> objects = transferOutOrderService.findTransferOutMoneyBymonth(systemBookCode, branchNums, dateFrom, dateTo);
        List<TransferOutMoney> list = new ArrayList<>();
        if(objects.isEmpty()){
            return null;
        }
        for (int i = 0; i <objects.size() ; i++) {
            Object[] object = objects.get(i);
            TransferOutMoney transferOutMoney = new TransferOutMoney();
            transferOutMoney.setBiz((String) object[0]);
            transferOutMoney.setOutMoney((BigDecimal) object[1]);
            list.add(transferOutMoney);
        }
        return list;
    }
}
