package com.nhsoft.module.report.service;

import com.nhsoft.module.report.model.OtherInout;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by yangqin on 2017/9/20.
 */
public interface OtherInoutService {
	
	/**
	 * 按分店、标记、收支类型汇总前台金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findPosBranchFlagKindSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom,
	                                                   Date dateTo);
	
	/**
	 * 按结算分店、收支标记汇总 总金额 未结金额
	 * @param systemBookCode
	 * @param branchNum 分店号
	 * @param dateFrom 审核时间起
	 * @param dateTo 审核时间止
	 * @param innerBranchNums 结算分店列表
	 * @return
	 */
	public List<Object[]> findBranchsMoney(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, List<Integer> innerBranchNums);
	
	/**
	 * 查询未结金额
	 * @param systemBookCode
	 * @param branchNum 分店号
	 * @param innerBranchNum 结算分店号
	 * @param supplierNum 供应商主键
	 * @param clientFid 客户主键
	 * @param type =0 查分店 1查供应商  2查客户
	 * @return
	 */
	public BigDecimal getUnPaidMoney(String systemBookCode, Integer branchNum, Integer innerBranchNum, Integer supplierNum, String clientFid, int type);
	
	/**
	 * 按结算分店查询
	 * @param systemBookCode
	 * @param branchNum 分店号
	 * @param innerBranchNum 结算分店号
	 * @param dateFrom 审核时间起
	 * @param dateTo 审核时间止
	 * @return
	 */
	public List<OtherInout> findByBranch(String systemBookCode, Integer branchNum, Integer innerBranchNum, Date dateFrom, Date dateTo);
	
	/**
	 * 按收支标记汇总 总金额 未结金额
	 * @param systemBookCode
	 * @param branchNum 分店号
	 * @param innerBranchNum 结算分店号
	 * @param dateFrom 审核时间起
	 * @param dateTo 审核时间止
	 * @return
	 */
	public List<Object[]> findMoneybyBranch(String systemBookCode, Integer branchNum, Integer innerBranchNum, Date dateFrom, Date dateTo);
	
	
	
	
}
