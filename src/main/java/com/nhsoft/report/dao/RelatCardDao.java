package com.nhsoft.report.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface RelatCardDao {
	
	 /**
     * 查询续卡现金收入
     * @param systemBookCode
     * @param branchNums
     * @param dateFrom
     * @param dateTo
     * @return
     */
    public BigDecimal getCashMoney(String systemBookCode,
                                   List<Integer> branchNums, Date dateFrom, Date dateTo);
    
    /**
     * 按门店汇总续卡现金收入
     * @param systemBookCode
     * @param branchNums
     * @param dateFrom
     * @param dateTo
     * @return
     */
    public List<Object[]> findCashGroupByBranch(String systemBookCode,
                                                List<Integer> branchNums, Date dateFrom, Date dateTo);
	
}
