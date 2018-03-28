package com.nhsoft.module.report.service;

import com.nhsoft.module.report.dto.ShipDetailDTO;
import com.nhsoft.module.report.dto.ShipOrderSummary;

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


    /**
     * 按天汇总运费金额
     * @param systemBookCode
     * @param branchNum 发货分店号
     * @param branchNums 收货分店列表
     * @param dateFrom 审核时间起
     * @param dateTo 审核时间至
     * @return
     */
    public List<Object[]> findDateSummary(String systemBookCode, Integer branchNum,
                                          List<Integer> branchNums, Date dateFrom, Date dateTo,String strDate);
}
