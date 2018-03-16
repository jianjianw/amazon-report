package com.nhsoft.module.report.rpc.impl;

import com.nhsoft.module.report.dto.MonthReturnDTO;
import com.nhsoft.module.report.rpc.ReturnOrderRpc;
import com.nhsoft.module.report.service.ReturnOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class ReturnOrderRpcImpl implements ReturnOrderRpc{



    @Autowired
    private ReturnOrderService returnOrderService;

    @Override
    public List<MonthReturnDTO> findReturnMonth(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, String strDate) {

        List<Object[]> objects = returnOrderService.findReturnMonth(systemBookCode, branchNum, dateFrom, dateTo, strDate);
        int size = objects.size();
        List<MonthReturnDTO> list = new ArrayList<>(size);
        for (int i = 0; i <size ; i++) {
            Object[] object = objects.get(i);
            MonthReturnDTO dto = new MonthReturnDTO();
            dto.setBizday((String) object[0]);
            dto.setTotalMoney((BigDecimal) object[1]);
            list.add(dto);
        }
        return list;
    }
}
