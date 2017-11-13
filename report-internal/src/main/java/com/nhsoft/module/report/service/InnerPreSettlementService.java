package com.nhsoft.module.report.service;


import com.nhsoft.module.report.model.InnerPreSettlement;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 门店预付款单
 * @author nhsoft
 *
 */
public interface InnerPreSettlementService {
	
	/**
	 * 根据结算门店查询单据 仅包含preSettlementNo、preSettlementAuditTime、preSettlementMemo、preSettlementPaid、preSettlementMoney
	 * @param systemBookCode
	 * @param branchNum 结算分店
	 * @param centerBranchNum 中心分店
	 * @param dateFrom 审核时间起
	 * @param dateTo 审核时间止
	 * @return
	 */
	public List<InnerPreSettlement> findBySettleBranch(String systemBookCode, Integer branchNum, Integer centerBranchNum, Date dateFrom, Date dateTo);
	
	/**
	 * 查询未结算金额
	 * @param systemBookCode
	 * @param branchNum 结算分店
	 * @param centerBranchNum  中心分店
	 * @return
	 */
	public BigDecimal readBranchUnPaidMoney(String systemBookCode, Integer branchNum, Integer centerBranchNum);
	
	/**
	 * 门店汇总 金额
	 * @param systemBookCode
	 * @param centerBranchNum 中心分店
	 * @param dateFrom 预付时间起
	 * @param dateTo 预付时间止
	 * @param branchNums 结算分店列表
	 * @return
	 */
	public List<Object[]> findMoneyByBranchNums(String systemBookCode, Integer centerBranchNum,
	                                            Date dateFrom, Date dateTo, List<Integer> branchNums);
    
    /**
     *按结算分店汇总 未结金额
     * @param systemBookCode
     * @param centerBranchNum 中心分店
     * @param branchNums 结算分店列表
     * @param dateFrom 预付时间起
     * @param dateTo 预付时间止
     * @return
     */
    public List<Object[]> findDueMoney(String systemBookCode, Integer centerBranchNum, List<Integer> branchNums, Date dateFrom, Date dateTo);

}
