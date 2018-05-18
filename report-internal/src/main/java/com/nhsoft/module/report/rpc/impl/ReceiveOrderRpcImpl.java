package com.nhsoft.module.report.rpc.impl;

import com.nhsoft.module.report.dto.BizPurchaseDTO;
import com.nhsoft.module.report.dto.MonthPurchaseDTO;
import com.nhsoft.module.report.dto.ReceiveOrderInfoDTO;
import com.nhsoft.module.report.rpc.ReceiveOrderRpc;
import com.nhsoft.module.report.service.ReceiveOrderService;
import com.nhsoft.module.report.util.AppConstants;
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
        List<Object[]> objects = receiveOrderService.findPurchaseMonth(systemBookCode, branchNum, dateFrom, dateTo, dateType, AppConstants.BUSINESS_DATE_DAY);
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

    @Override
    public List<MonthPurchaseDTO> findPurchaseMonth(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, String dateType, String strDate) {

        List<Object[]> objects = receiveOrderService.findPurchaseMonth(systemBookCode, branchNum, dateFrom, dateTo, dateType, strDate);
        int size = objects.size();
        List<MonthPurchaseDTO> list = new ArrayList<>(size);
        for (int i = 0; i < size ; i++) {
            Object[] object = objects.get(i);
            MonthPurchaseDTO dto = new MonthPurchaseDTO();
            dto.setBizday((String) object[0]);
            dto.setItemNum((Integer) object[1]);
            dto.setSubTotal((BigDecimal) object[2]);
            dto.setOtherMoney((BigDecimal) object[3]);
            list.add(dto);
        }
        return list;
    }

    @Override
    public List<BizPurchaseDTO> findPurchaseByBiz(String systemBookCode,Date dateFrom, Date dateTo, List<Integer> itemNums) {

        List<Object[]> objects = receiveOrderService.findPurchaseByBiz(systemBookCode,dateFrom, dateTo, itemNums);
        int size = objects.size();
        List<BizPurchaseDTO> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Object[] object = objects.get(i);
            BizPurchaseDTO dto = new BizPurchaseDTO();
            dto.setBizday((String) object[0]);
            dto.setQty(object[1] == null? BigDecimal.ZERO: (BigDecimal)object[1]);
            dto.setTotalMoney(object[2] == null? BigDecimal.ZERO : (BigDecimal)object[2]);
            list.add(dto);
        }
        return list;
    }

    @Override
    public List<Object[]> findPurchaseByItemBiz(String systemBookCode, Date dateFrom, Date dateTo, List<Integer> itemNums) {
        List<Object[]> obeject = receiveOrderService.findPurchaseByItemBiz(systemBookCode, dateFrom, dateTo, itemNums);
        return obeject;
    }
}
