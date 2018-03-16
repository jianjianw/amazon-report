package com.nhsoft.module.report.rpc.impl;

import com.nhsoft.module.report.dto.ShipDetailDTO;
import com.nhsoft.module.report.dto.ShipOrderMoneyDateDTO;
import com.nhsoft.module.report.dto.ShipOrderSummary;
import com.nhsoft.module.report.rpc.ShipOrderRpc;
import com.nhsoft.module.report.service.ShipOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Component
public class ShipOrderRpcImpl implements ShipOrderRpc {
    @Autowired
    private ShipOrderService shipOrderService;
    @Override
    public List<ShipOrderSummary> findCarriageMoneyByCompanies(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> companies) {
        return shipOrderService.findCarriageMoneyByCompanies(systemBookCode,branchNums,dateFrom,dateTo,companies);
    }

    @Override
    public List<ShipDetailDTO> findDetails(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> companies) {
        
        return shipOrderService.findDetails(systemBookCode,branchNums,dateFrom,dateTo,companies);
    }

    @Override
    public List<ShipOrderMoneyDateDTO> findDateSummary(String systemBookCode, Integer branchNum, List<Integer> branchNums, Date dateFrom, Date dateTo, String strDate) {

        List<Object[]> objects = shipOrderService.findDateSummary(systemBookCode, branchNum, branchNums, dateFrom, dateTo, strDate);
        int size = objects.size();
        List<ShipOrderMoneyDateDTO> list = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            Object[] object = objects.get(i);
            ShipOrderMoneyDateDTO dto = new ShipOrderMoneyDateDTO();
            dto.setBizday((String) object[0]);
            dto.setFeeMoney((BigDecimal) object[1]);
            dto.setTotalMoney((BigDecimal) object[2]);
            list.add(dto);
        }
        return list;
    }
}
