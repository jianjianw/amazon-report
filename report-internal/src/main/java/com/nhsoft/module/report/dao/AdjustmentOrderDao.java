package com.nhsoft.module.report.dao;

import com.nhsoft.module.report.dto.ItemLossDailyDTO;

import java.util.Date;
import java.util.List;

public interface AdjustmentOrderDao {


	public List<Object[]> findBranchItemSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<Integer> itemNums, List<String> reasons);

	/**
	 * 按商品、多特性编码 汇总 调整金额 调整数量
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param reasons
	 * @param inOut
	 * @param isAudit
	 * @param itemNums
	 * @return
	 */
	public List<Object[]> findItemSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo,
										  List<String> reasons, Boolean inOut, Boolean isAudit, List<Integer> itemNums);

	/**
	 * 按门店汇总 调整金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param reasons
	 * @return
	 */
	public List<Object[]> findBranchSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> reasons);


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
	 * 按分店查询报损金额
	 * @param systemBookCode
	 * @param branchNums 分店号
	 * @param dateFrom 时间起
	 * @param dateTo 时间止
	 */
	public List<Object[]> findLossMoneyByBranch(String systemBookCode,List<Integer> branchNums,Date dateFrom, Date dateTo);

	/**
	 * 按分店查询盘损金额
	 * @param systemBookCode
	 * @param branchNums 分店号
	 * @param dateFrom 时间起
	 * @param dateTo 时间止
	 */
	public List<Object[]> findCheckMoneyByBranch(String systemBookCode,List<Integer> branchNums,Date dateFrom, Date dateTo);

	/**
	 *按分店查询损耗金额		试吃，去皮，报损，其它
	 * @param systemBookCode
	 * @param branchNums 分店号
	 * @param dateFrom 时间起
	 * @param dateTo 时间止
	 * */
	public List<Object[]> findAdjustmentCauseMoneyByBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);


}