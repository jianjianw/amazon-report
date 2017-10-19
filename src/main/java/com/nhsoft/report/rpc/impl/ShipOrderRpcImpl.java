package com.nhsoft.report.rpc.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.nhsoft.report.dto.ShipDetailSummary;
import com.nhsoft.report.dto.ShipMoneySummary;
import com.nhsoft.report.rpc.ShipOrderRpc;
import com.nhsoft.report.service.ShipOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
@Service
@Component
public class ShipOrderRpcImpl implements ShipOrderRpc {
    @Autowired
    private ShipOrderService shipOrderService;
    @Override
    public List<ShipMoneySummary> findShipMoneyByCompanies(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> companies) {
        return shipOrderService.findShipMoneyByCompanies(systemBookCode,branchNums,dateFrom,dateTo,companies);
    }

    @Override
    public List<ShipDetailSummary> findShipDetailByCompanies(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> companies) {
        return shipOrderService.findShipDetailByCompanies(systemBookCode,branchNums,dateFrom,dateTo,companies);
    }
}
