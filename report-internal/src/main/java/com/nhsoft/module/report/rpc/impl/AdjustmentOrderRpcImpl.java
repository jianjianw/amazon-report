package com.nhsoft.module.report.rpc.impl;

import com.nhsoft.module.report.dto.AdjustmentCauseMoney;
import com.nhsoft.module.report.dto.CheckMoney;
import com.nhsoft.module.report.dto.ItemLossDailyDTO;
import com.nhsoft.module.report.dto.LossMoneyReport;
import com.nhsoft.module.report.rpc.AdjustmentOrderRpc;

import com.nhsoft.module.report.service.AdjustmentOrderService;
import com.nhsoft.module.report.util.DateUtil;
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
        int size = objects.size();
        List<LossMoneyReport> list = new ArrayList<LossMoneyReport>(size);
        if(objects.isEmpty()){
            return list;
        }
        for (int i = 0; i <size ; i++) {
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
        int size = objects.size();
        List<CheckMoney> list = new ArrayList<CheckMoney>(size);
        if(objects.isEmpty()){
            return list;
        }
        for (int i = 0; i <size ; i++) {
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
        int size = objects.size();
        List<AdjustmentCauseMoney> list = new ArrayList<AdjustmentCauseMoney>(size);
        if(objects.isEmpty()){
            return list;
        }
        for (int i = 0; i <size ; i++) {
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

    @Override
    public List<ItemLossDailyDTO> findItemLossDailySummary(String systemBookCode, Date dateFrom, Date dateTo) {

        List<Object[]> objects = adjustmentOrderService.findItemLossDailySummary(systemBookCode, dateFrom, dateTo);
        int size = objects.size();
        List<ItemLossDailyDTO> list = new ArrayList<>(size);
        if(objects.isEmpty()){
            return list;
        }
        for (int i = 0; i <size ; i++) {
            Object[] object = objects.get(i);
            ItemLossDailyDTO itemLossDailyDTO = new ItemLossDailyDTO();
            itemLossDailyDTO.setSystemBookCode(systemBookCode);
            itemLossDailyDTO.setBranchNum((Integer) object[0]);
            itemLossDailyDTO.setShiftTableBizday((String) object[1]);
            itemLossDailyDTO.setItemLossReason((String) object[2]);
            itemLossDailyDTO.setItemNum((Integer) object[3]);
            itemLossDailyDTO.setItemMoney((BigDecimal) object[4]);
            itemLossDailyDTO.setItemAmount((BigDecimal) object[5]);
            itemLossDailyDTO.setShiftTableDate(DateUtil.getDateStr(itemLossDailyDTO.getShiftTableBizday()));
            list.add(itemLossDailyDTO);
        }
        return list;
    }


}
