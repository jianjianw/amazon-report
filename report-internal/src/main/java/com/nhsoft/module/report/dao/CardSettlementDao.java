package com.nhsoft.module.report.dao;

import com.nhsoft.module.report.model.CardSettlement;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface CardSettlementDao {
	
	/**
	 * 查询卡结算单数量
	 * @param systemBookCode
	 * @param branchNum
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
	
	/**
	 * 根据结算分店查询
	 * @param systemBookCode
	 * @param branchNum
	 * @param settleBranchNum
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<CardSettlement> findBySettleBranch(String systemBookCode, Integer branchNum, Integer settleBranchNum, Date dateFrom, Date dateTo);
	
	/**
	 * 根据结算分店查询金额
	 * @param systemBookCode
	 * @param branchNum
	 * @param settleBranchNum
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findMoneyBySettleBranch(String systemBookCode, Integer branchNum, Integer settleBranchNum, Date dateFrom, Date dateTo);
	
	public List<Object[]> findBranchsMoney(String systemBookCode, Integer branchNum, List<Integer> branchNums, Date dateFrom, Date dateTo);
	
	
	
}
