package com.nhsoft.report.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface CardSettlementDao {
	/**
	 * 查询卡结算单数量
	 * @param systemBookCode
	 * @param object
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public Integer countByBranch(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo);

	/**
	 * 门店未结算金额
	 * @param systemBookCode
	 * @param branchNum
	 * @param centerBranchNum
	 * @return
	 */
	public BigDecimal readBranchUnPaidMoney(String systemBookCode,
											Integer branchNum, Integer centerBranchNum);
}
