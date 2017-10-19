package com.nhsoft.report.rpc;

import com.nhsoft.report.dto.ShipDetailsDTO;
import com.nhsoft.report.dto.ShipOrderSummary;

import java.util.Date;
import java.util.List;


public interface ShipOrderRpc {


    /**
     * 汇总 按货运公司汇总运费金额
     * @param systemBookCode
     * @param branchNums
     * @param dateFrom
     * @param dateTo
     * @param companies
     * */
    public List<ShipOrderSummary> findCarriageMoneyByCompanies(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> companies);

    /**
     *  明细 返回 货运公司（车辆）、发货单号、运费金额、时间
     * @param systemBookCode
     * @param branchNums
     * @param dateFrom
     * @param dateTo
     * @param companies
     * */
     public List<ShipDetailsDTO> findDetails(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> companies);

}
