package com.nhsoft.report.rpc.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.nhsoft.report.dto.SaleMoneyGoals;
import com.nhsoft.report.rpc.BranchTransferGoalsRpc;
import com.nhsoft.report.service.BranchTransferGoalsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
@Component
public class BranchTransferGoalsRpcImpl implements BranchTransferGoalsRpc {

    @Autowired
    private BranchTransferGoalsService branchTransferGoalsService;

    @Override
    public List<SaleMoneyGoals> findSaleMoneyGoalsByBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo,String dateType) {
        List<Object[]> objects = branchTransferGoalsService.findSaleMoneyGoalsByBranch(systemBookCode, branchNums, dateFrom, dateTo,dateType);
        List<SaleMoneyGoals> list = new ArrayList<SaleMoneyGoals>();
        if(objects.isEmpty()){
            return list;
        }
        for(int i = 0;i<objects.size();i++){
            Object[] object = objects.get(i);
            SaleMoneyGoals saleMoneyGoals = new SaleMoneyGoals();
            saleMoneyGoals.setBranchNum((Integer) object[0]);
            saleMoneyGoals.setSaleMoney((BigDecimal) object[1]);
            list.add(saleMoneyGoals);
        }
        return list;
    }
}