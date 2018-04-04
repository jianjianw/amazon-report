package com.nhsoft.module.report.rpc.impl;

import com.nhsoft.module.report.dto.WholesaleAmountAndMoneyDTO;
import com.nhsoft.module.report.rpc.WholesaleOrderRpc;
import com.nhsoft.module.report.service.WholesaleOrderService;
import com.sun.org.apache.bcel.internal.generic.BIPUSH;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Component
public class WholesaleOrderRpcImpl implements WholesaleOrderRpc {

    @Autowired
    private WholesaleOrderService wholesaleOrderService;

    @Override
    public List<WholesaleAmountAndMoneyDTO> findAmountAndMoneyByBiz(String systemBookCode, Date dateFrom, Date dateTo, List<Integer> itemNums) {
        List<Object[]> objects = wholesaleOrderService.findAmountAndMoneyByBiz(systemBookCode, dateFrom, dateTo, itemNums);
        int size = objects.size();
        List<WholesaleAmountAndMoneyDTO> list = new ArrayList<>(size);
        for (int i = 0; i <size ; i++) {
            Object[] object = objects.get(i);
            WholesaleAmountAndMoneyDTO dto = new WholesaleAmountAndMoneyDTO();
            dto.setBiz((String) object[0]);
            dto.setQty(object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1]);
            dto.setMoney(object[2] == null ? BigDecimal.ZERO : (BigDecimal)object[2]);
            list.add(dto);
        }

        return list;
    }
}
