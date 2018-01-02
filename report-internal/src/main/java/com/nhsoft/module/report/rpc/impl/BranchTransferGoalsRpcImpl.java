package com.nhsoft.module.report.rpc.impl;

import com.nhsoft.module.report.dto.DepositGoalsDTO;
import com.nhsoft.module.report.dto.NewCardGoalsDTO;
import com.nhsoft.module.report.dto.SaleMoneyGoals;
import com.nhsoft.module.report.rpc.BranchTransferGoalsRpc;
import com.nhsoft.module.report.service.BranchTransferGoalsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class BranchTransferGoalsRpcImpl implements BranchTransferGoalsRpc {

    @Autowired
    private BranchTransferGoalsService branchTransferGoalsService;

    @Override
    public List<SaleMoneyGoals> findSaleMoneyGoalsByBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String dateType) {
        List<Object[]> objects = branchTransferGoalsService.findSaleMoneyGoalsByBranch(systemBookCode, branchNums, dateFrom, dateTo,dateType);
        int size = objects.size();
        List<SaleMoneyGoals> list = new ArrayList<SaleMoneyGoals>(size);
        if(objects.isEmpty()){
            return list;
        }
        for(int i = 0;i<size;i++){
            Object[] object = objects.get(i);
            SaleMoneyGoals saleMoneyGoals = new SaleMoneyGoals();
            saleMoneyGoals.setBranchNum((Integer) object[0]);
            saleMoneyGoals.setSaleMoney((BigDecimal) object[1]);
            list.add(saleMoneyGoals);
        }
        return list;
    }

    @Override
    public List<SaleMoneyGoals> findSaleMoneyGoalsByDate(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String dateType) {
        List<Object[]> objects = branchTransferGoalsService.findSaleMoneyGoalsByDate(systemBookCode, branchNums, dateFrom, dateTo, dateType);
        int size = objects.size();
        List<SaleMoneyGoals> list = new ArrayList<SaleMoneyGoals>(size);
        if(objects.isEmpty()){
            return list;
        }
        for(int i = 0;i<size;i++){
            Object[] object = objects.get(i);
            SaleMoneyGoals saleMoneyGoals = new SaleMoneyGoals();
            saleMoneyGoals.setDate((String) object[0]);
            saleMoneyGoals.setSaleMoney((BigDecimal) object[1]);
            list.add(saleMoneyGoals);
        }
        return list;

    }

    @Override
    public List<SaleMoneyGoals> findGoalsByBranchBizday(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
        List<Object[]> objects = branchTransferGoalsService.findGoalsByBranchBizday(systemBookCode, branchNums, dateFrom, dateTo);

        int size = objects.size();
        List<SaleMoneyGoals> list = new ArrayList<>(size);
        if(objects.isEmpty()){
            return list;
        }
        for (int i = 0; i <size ; i++) {
            Object[] object = objects.get(i);
            SaleMoneyGoals saleMoneyGoals = new SaleMoneyGoals();
            saleMoneyGoals.setBranchNum((Integer) object[0]);
            saleMoneyGoals.setDate((String) object[1]);
            saleMoneyGoals.setSaleMoney((BigDecimal) object[2]);
            saleMoneyGoals.setSystemBookCode(systemBookCode);
            list.add(saleMoneyGoals);
        }
        return list;
    }

    @Override
    public List<DepositGoalsDTO> findDepositGoals(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
        List<Object[]> objects = branchTransferGoalsService.findDepositGoals(systemBookCode, branchNums, dateFrom, dateTo);
        int size = objects.size();
        List<DepositGoalsDTO> list = new ArrayList<>(size);
        if(objects.isEmpty()){
            return list;
        }
        String biz = null;
        for (int i = 0; i <size ; i++) {
            Object[] object = objects.get(i);
            DepositGoalsDTO depositGoalsDTO = new DepositGoalsDTO();
            depositGoalsDTO.setBranchNum((Integer) object[0]);
            biz = (String) object[1];
            depositGoalsDTO.setBizday(biz.replace("-",""));
            depositGoalsDTO.setDepositGoals((BigDecimal) object[2]);
            list.add(depositGoalsDTO);
        }
        return list;
    }

    @Override
    public List<NewCardGoalsDTO> findNewCardGoals(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
        List<Object[]> objects = branchTransferGoalsService.findNewCardGoals(systemBookCode, branchNums, dateFrom, dateTo);

        int size = objects.size();
        List<NewCardGoalsDTO> list = new ArrayList<>(size);
        if(objects.isEmpty()){
            return list;
        }
        String biz = null;
        for (int i = 0; i <size ; i++) {
            Object[] object = objects.get(i);
            NewCardGoalsDTO newCardGoalsDTO = new NewCardGoalsDTO();
            newCardGoalsDTO.setBranchNum((Integer) object[0]);
            biz = (String) object[1];
            newCardGoalsDTO.setBizday(biz.replace("-",""));
            newCardGoalsDTO.setNewCard((Integer) object[2]);
            list.add(newCardGoalsDTO);
        }
        return list;
    }


}
