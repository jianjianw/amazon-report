package com.nhsoft.module.report.dao;



import java.math.BigDecimal;
import java.util.Date;


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

}
