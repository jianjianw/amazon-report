package com.nhsoft.report.rpc.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.nhsoft.report.dao.impl.TransferOutMoney;
import com.nhsoft.report.model.TransferOutOrder;
import com.nhsoft.report.rpc.TransferOutOrderRpc;
import com.nhsoft.report.service.TransferOutOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
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
}
