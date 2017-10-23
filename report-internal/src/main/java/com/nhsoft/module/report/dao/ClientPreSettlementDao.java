package com.nhsoft.module.report.dao;


import java.util.Date;
import java.util.List;

public interface ClientPreSettlementDao {


    /**
     * 按门店统计单据数
     * @param systemBookCode
     * @param branchNum
     * @param dateTo
     * @param dateFrom
     * @return
     */
    public int countByBranch(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo);


    /**
     * 根据客户汇总预付金额
     * @param systemBookCode
     * @param branchNum
     * @param supplierNums
     * @return
     */
    public List<Object[]> findDueMoney(String systemBookCode, Integer branchNum, List<String> clientFids, Date dateFrom, Date dateTo);


}
