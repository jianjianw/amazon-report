package com.nhsoft.module.report.dao;




import com.nhsoft.module.report.model.InnerSettlement;

import java.util.Date;
import java.util.List;


public interface InnerSettlementDao {
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
	 * 门店汇总 金额 折扣
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param branchNums
	 * @return
	 */
	public List<Object[]> findMoneyByBranchNums(String systemBookCode, Integer branchNum,
	                                            Date dateFrom, Date dateTo, List<Integer> branchNums, boolean flag);
	
	/**
	 * 根据结算分店查询
	 * @param systemBookCode
	 * @param centerBranchNum
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<InnerSettlement> findBySettleBranchNum(String systemBookCode, Integer centerBranchNum, Integer branchNum, Date dateFrom,
	                                                   Date dateTo);
}
