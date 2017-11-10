package com.nhsoft.module.report.dao;



import com.nhsoft.module.report.model.InnerPreSettlement;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


public interface InnerPreSettlementDao {

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
	 * 未结算金额
	 * @param systemBookCode
	 * @param branchNum
	 * @param centerBranchNum
	 * @return
	 */
	public BigDecimal readBranchUnPaidMoney(String systemBookCode, Integer branchNum, Integer centerBranchNum);
	
	/**
	 * 根据结算门店查询单据
	 * @param systemBookCode
	 * @param branchNum
	 * @param centerBranchNum
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<InnerPreSettlement> findBySettleBranch(String systemBookCode, Integer branchNum, Integer centerBranchNum, Date dateFrom, Date dateTo);
	
	
	/**
	 * 门店汇总 金额
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param branchNums
	 * @return
	 */
	public List<Object[]> findMoneyByBranchNums(String systemBookCode, Integer branchNum,
	                                            Date dateFrom, Date dateTo, List<Integer> branchNums);
	
	/**
	 * 根据加盟店汇总预付金额
	 * @param systemBookCode
	 * @param branchNum
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findDueMoney(String systemBookCode, Integer branchNum, List<Integer> branchNums, Date dateFrom, Date dateTo);
	
	
}
