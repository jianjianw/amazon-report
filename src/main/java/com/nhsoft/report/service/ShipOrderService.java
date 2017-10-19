package com.nhsoft.report.service;

import com.nhsoft.report.dto.ShipDetailSummary;
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

    /**
     *  明细 返回 货运公司（车辆）、发货单号、运费金额、时间
     * @param systemBookCode
     * @param branchNums
     * @param dateFrom
     * @param dateTo
     * @param companies
     * */
    public List<ShipDetailSummary> findShipDetailByCompanies(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> companies);

}
