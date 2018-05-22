package com.nhsoft.module.report.rpc.impl;


import com.nhsoft.module.report.dto.*;
import com.nhsoft.module.report.query.TransferQueryDTO;
import com.nhsoft.module.report.queryBuilder.TransferProfitQuery;
import com.nhsoft.module.report.rpc.PosItemFlagRpc;
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

    @Override
    public List<TransterOutDTO> findItemSummary(String systemBookCode, List<Integer> outBranchNums, List<Integer> branchNums, Date dateFrom, Date dateTo, List<Integer> itemNums) {

        List<Object[]> objects = transferOutOrderService.findItemSummary(systemBookCode, outBranchNums, branchNums, dateFrom, dateTo, itemNums);
        int size = objects.size();
        List<TransterOutDTO> list = new ArrayList<>(size);
        for (int i = 0; i <size ; i++) {
            Object[] object = objects.get(i);
            TransterOutDTO dto = new TransterOutDTO();
            dto.setItemNum((Integer) object[0]);
            dto.setQty((BigDecimal) object[1]);
            dto.setMoney((BigDecimal) object[2]);
            dto.setUseQty((BigDecimal) object[3]);
            list.add(dto);
        }
        return list;
    }

    @Override
    public List<TransferOutMoneyDateDTO> findDateSummary(String systemBookCode, Integer centerBranchNum, List<Integer> branchNums, Date dateFrom, Date dateTo, String strDate) {

        List<Object[]> objects = transferOutOrderService.findDateSummary(systemBookCode, centerBranchNum, branchNums, dateFrom, dateTo, strDate);
        int size = objects.size();
        List<TransferOutMoneyDateDTO> list = new ArrayList<>(size);
        for (int i = 0; i < size ; i++) {
            Object[] object = objects.get(i);
            TransferOutMoneyDateDTO dto = new TransferOutMoneyDateDTO();
            dto.setBizday((String) object[0]);
            dto.setTotalMoney((BigDecimal) object[1]);
            dto.setFeeMoney((BigDecimal) object[2]);
            list.add(dto);
        }
        return list;
    }

    @Override
    public List<TransferOutMoneyAndAmountDTO> findMoneyAndAmountByBiz(String systemBookCode, Date dateFrom, Date dateTo,List<Integer> itemNums) {

        List<Object[]> objects = transferOutOrderService.findMoneyAndAmountByBiz(systemBookCode, dateFrom, dateTo,itemNums);
        int size = objects.size();
        List<TransferOutMoneyAndAmountDTO> list = new ArrayList<>(size);
        for (int i = 0; i < size ; i++) {
            Object[] object = objects.get(i);
            TransferOutMoneyAndAmountDTO dto = new TransferOutMoneyAndAmountDTO();
            dto.setBiz((String) object[0]);
            dto.setOutQty(object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1] );
            dto.setOutMoney(object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2]);
            list.add(dto);
        }
        return list;
    }

    @Override
    public List<TransterOutDTO> findMoneyAndAmountByItemNum(String systemBookCode, Integer branchNum,List<Integer> storehouseNums, Date dateFrom, Date dateTo, List<Integer> itemNums, String sortField) {
        List<Object[]> objects = transferOutOrderService.findMoneyAndAmountByItemNum(systemBookCode, branchNum,storehouseNums, dateFrom, dateTo, itemNums, sortField);
        int size = objects.size();
        List<TransterOutDTO> list = new ArrayList<>(size);
        for (int i = 0; i < size ; i++) {
            Object[] object = objects.get(i);
            TransterOutDTO dto = new TransterOutDTO();
            dto.setItemNum((Integer) object[0]);
            dto.setQty((BigDecimal) object[1]);
            dto.setMoney((BigDecimal) object[2]);
            list.add(dto);
        }
        return list;
    }

    @Override
    public List<TransterOutDTO> findMoneyAndAmountByItemNum(TransferQueryDTO queryDTO) {
        List<Object[]> objects = transferOutOrderService.findMoneyAndAmountByItemNum(queryDTO);
        int size = objects.size();
        List<TransterOutDTO> list = new ArrayList<>(size);
        for (int i = 0; i < size ; i++) {
            Object[] object = objects.get(i);
            TransterOutDTO dto = new TransterOutDTO();
            dto.setItemNum((Integer) object[0]);
            dto.setQty((BigDecimal) object[1]);
            dto.setMoney((BigDecimal) object[2]);
            list.add(dto);
        }
        return list;
    }
}
