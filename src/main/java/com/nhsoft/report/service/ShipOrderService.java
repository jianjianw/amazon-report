package com.nhsoft.report.service;

import com.nhsoft.report.dto.ShipMoneySummary;

import java.util.Date;
import java.util.List;

public interface ShipOrderService {

    /**
     * @param systemBookCode
     * @param branchNums
     * @param dateFrom
     * @param dateTo
     * @param companies
     * */
    public List<ShipMoneySummary> findShipMoneyByCompanies(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> companies);


}
