package com.nhsoft.module.report.rpc.impl;

import com.nhsoft.module.report.dto.MonthPurchaseDTO;
import com.nhsoft.module.report.dto.ReceiveOrderInfoDTO;
import com.nhsoft.module.report.rpc.ReceiveOrderRpc;
import com.nhsoft.module.report.service.ReceiveOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class ReceiveOrderRpcImpl implements ReceiveOrderRpc {

    @Autowired
    private ReceiveOrderService receiveOrderService;
    @Override
    public List<ReceiveOrderInfoDTO> findItemSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<Integer> itemNums) {

        List<Object[]> objects = receiveOrderService.findItemSummary(systemBookCode,branchNums,dateFrom,dateTo,itemNums);
        int size = objects.size();
        List<ReceiveOrderInfoDTO> list = new ArrayList<>(size);
        for (int i = 0; i <size ; i++) {
            ReceiveOrderInfoDTO dto = new ReceiveOrderInfoDTO();
            Object[] object = objects.get(i);
            dto.setItemNum((Integer) object[0]);
            dto.setReceiveQty((BigDecimal) object[1]);
            dto.setReceiveMoney((BigDecimal) object[2]);
            list.add(dto);
        }
        return list;
    }

    @Override
    public List<MonthPurchaseDTO> findPurchaseMonth(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, String dateType) {
        List<Object[]> objects = receiveOrderService.findPurchaseMonth(systemBookCode, branchNum, dateFrom, dateTo, dateType);
        int size = objects.size();
        List<MonthPurchaseDTO> list = new ArrayList<>(size);
        String bizday;
        for (int i = 0; i < size ; i++) {
            Object[] object = objects.get(i);
            MonthPurchaseDTO dto = new MonthPurchaseDTO();
            bizday = (String) object[0];
            bizday= bizday.substring(bizday.length() - 2, bizday.length());
            if("10".compareTo(bizday) > 0){
                String sub = bizday.substring(bizday.length() - 1,bizday.length());
                dto.setBizday(sub);
            }else{
                dto.setBizday(bizday);
            }
            dto.setItemNum((Integer) object[1]);
            dto.setSubTotal((BigDecimal) object[2]);
            dto.setOtherMoney((BigDecimal) object[3]);
            list.add(dto);
        }
        return list;
    }
}
