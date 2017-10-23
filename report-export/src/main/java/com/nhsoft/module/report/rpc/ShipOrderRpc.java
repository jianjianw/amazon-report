package com.nhsoft.module.report.rpc;

import com.nhsoft.module.report.dto.ShipDetailDTO;
import com.nhsoft.module.report.dto.ShipOrderSummary;

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
     public List<ShipDetailDTO> findDetails(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> companies);

}
