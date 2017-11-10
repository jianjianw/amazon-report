package com.nhsoft.module.report.service;

import com.nhsoft.module.report.model.CardSettlement;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 卡结算单
 * @author nhsoft
 *
 */
public interface CardSettlementService {
	
	
	/**
	 * 
	 * @param systemBookCode
	 * @param branchNum 分店
	 * @param settleBranchNum 结算分店
	 * @param dateFrom 审核时间起
	 * @param dateTo 审核时间止
	 * @return
	 */
	public List<CardSettlement> findBySettleBranch(String systemBookCode, Integer branchNum, Integer settleBranchNum, Date dateFrom, Date dateTo);
	
	/**
	 * 汇总 金额 已付金额 折扣金额
	 * @param systemBookCode
	 * @param branchNum 分店
	 * @param settleBranchNum 结算分店
	 * @param dateFrom 审核时间起
	 * @param dateTo 审核时间止
	 * @return
	 */
	public List<Object[]> findMoneyBySettleBranch(String systemBookCode, Integer branchNum, Integer settleBranchNum, Date dateFrom, Date dateTo);
	
	/**
	 * 查询未结金额
	 * @param systemBookCode
	 * @param toBranchNum  结算分店号
	 * @param branchNum  分店号
	 * @return
	 */
	public BigDecimal readBranchUnPaidMoney(String systemBookCode,
	                                        Integer toBranchNum, Integer branchNum);
	
	/**
	 * 按结算分店汇总单据金额、未结金额
	 * @param systemBookCode
	 * @param branchNum 分店号
	 * @param toBranchNums 结算分店
	 * @param dateFrom 审核时间起
	 * @param dateTo 审核时间止
	 * @return
	 */
	public List<Object[]> findBranchsMoney(String systemBookCode, Integer branchNum, List<Integer> toBranchNums, Date dateFrom, Date dateTo);
}
