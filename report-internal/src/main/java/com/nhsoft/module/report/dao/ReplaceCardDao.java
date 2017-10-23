package com.nhsoft.module.report.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface ReplaceCardDao {

	 /**
     * 查询换卡现金收入
     * @param systemBookCode
     * @param branchNums
     * @param dateFrom
     * @param dateTo
     * @return
     */
    public BigDecimal getCashMoney(String systemBookCode,
                                   List<Integer> branchNums, Date dateFrom, Date dateTo);
    /**
     * 按门店汇总换卡现金收入
     * @param systemBookCode
     * @param branchNums
     * @param dateFrom
     * @param dateTo
     * @return
     */
	public List<Object[]> findCashGroupByBranch(String systemBookCode,
	                                            List<Integer> branchNums, Date dateFrom, Date dateTo);

	/**
	 * 按分店汇总卡换卡数量
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findBranchCount(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);
	
}
