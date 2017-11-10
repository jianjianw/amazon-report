package com.nhsoft.module.report.service;

import com.nhsoft.module.report.model.InnerSettlement;

import java.util.Date;
import java.util.List;

/**
 * 门店结算单
 * @author nhsoft
 *
 */
public interface InnerSettlementService {
	
	
	/**
	 * 根据结算分店查询
	 * @param systemBookCode
	 * @param centerBranchNum 中心分店号
	 * @param branchNum 结算分店
	 * @param dateFrom 审核时间起
	 * @param dateTo 审核时间止
	 * @return
	 */
	public List<InnerSettlement> findBySettleBranchNum(String systemBookCode, Integer centerBranchNum, Integer branchNum, Date dateFrom,
	                                                   Date dateTo);
	
	/**
	 * 按门店汇总 结算金额 折扣
	 * @param systemBookCode
	 * @param centerBranchNum 中心分店号
	 * @param dateFrom 审核时间起
	 * @param dateTo 审核时间止
	 * @param branchNums 结算分店列表
	 * @param flag = true 结算金额不包括预收冲应收 false包括
	 * @return
	 */
	public List<Object[]> findMoneyByBranchNums(String systemBookCode, Integer centerBranchNum,
	                                            Date dateFrom, Date dateTo, List<Integer> branchNums, boolean flag);
    
   
}
