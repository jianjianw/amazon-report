package com.nhsoft.report.rpc.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.nhsoft.module.report.dto.AdjustmentCauseMoney;
import com.nhsoft.module.report.dto.CheckMoney;
import com.nhsoft.module.report.dto.LossMoneyReport;
import com.nhsoft.module.report.rpc.AdjustmentOrderRpc;
import com.nhsoft.report.service.AdjustmentOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Component
public class AdjustmentOrderRpcImpl implements AdjustmentOrderRpc {

    @Autowired
    private AdjustmentOrderService adjustmentOrderService;

    @Override
    public List<LossMoneyReport> findLossMoneyByBranch(String systemBookCode,List<Integer> branchNums,Date dateFrom, Date dateTo) {
        List<Object[]> objects = adjustmentOrderService.findLossMoneyByBranch(systemBookCode,branchNums,dateFrom, dateTo);
        List<LossMoneyReport> list = new ArrayList<LossMoneyReport>();
        if(objects.isEmpty()){
            return list;
        }
        for (int i = 0; i <objects.size() ; i++) {
            Object[] object = objects.get(i);
            LossMoneyReport lossMoneyReport = new LossMoneyReport();
            lossMoneyReport.setBranchNum((Integer) object[0]);
            lossMoneyReport.setMoney((BigDecimal) object[1]);
            list.add(lossMoneyReport);
        }
        return list;
    }

    @Override
    public List<CheckMoney> findCheckMoneyByBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {

        List<Object[]> objects = adjustmentOrderService.findCheckMoneyByBranch(systemBookCode, branchNums, dateFrom, dateTo);
        List<CheckMoney> list = new ArrayList<CheckMoney>();
        if(objects.isEmpty()){
            return list;
        }
        for (int i = 0; i <objects.size() ; i++) {
            Object[] object = objects.get(i);
            CheckMoney checkMoney = new CheckMoney();
            checkMoney.setBranchNum((Integer) object[0]);
            checkMoney.setMoney((BigDecimal) object[1]);
            list.add(checkMoney);
        }
        return list;
    }



    @Override
    public List<AdjustmentCauseMoney> findAdjustmentCauseMoneyByBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {

        List<Object[]> objects = adjustmentOrderService.findAdjustmentCauseMoneyByBranch(systemBookCode, branchNums, dateFrom, dateTo);
        List<AdjustmentCauseMoney> list = new ArrayList<AdjustmentCauseMoney>();
        if(objects.isEmpty()){
            return list;
        }
        for (int i = 0; i <objects.size() ; i++) {
            Object[] object = objects.get(i);
            AdjustmentCauseMoney adjustmentCauseMoney = new AdjustmentCauseMoney();
            adjustmentCauseMoney.setBranchNum((Integer) object[0]);
            adjustmentCauseMoney.setTryEat((BigDecimal) object[1]);
            adjustmentCauseMoney.setLoss((BigDecimal) object[2]);
            adjustmentCauseMoney.setLoss((BigDecimal) object[3]);
            adjustmentCauseMoney.setOther((BigDecimal) object[4]);
            list.add(adjustmentCauseMoney);
        }
        return list;
    }
}
