package com.nhsoft.module.report.rpc.impl;

import com.nhsoft.module.report.dto.ShipDetailDTO;
import com.nhsoft.module.report.dto.ShipOrderSummary;
import com.nhsoft.module.report.rpc.ShipOrderRpc;
import com.nhsoft.module.report.service.ShipOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
}
