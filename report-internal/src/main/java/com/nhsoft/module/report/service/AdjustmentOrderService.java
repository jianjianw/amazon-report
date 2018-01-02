package com.nhsoft.module.report.service;

import java.util.Date;
import java.util.List;

/**
 * 调整单
 * @author nhsoft
 *
 */
public interface AdjustmentOrderService {

	/**
	 * 按商品门店汇总 调整金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param reasons
	 * @return
	 */
	public List<Object[]> findBranchItemSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<Integer> itemNums, List<String> reasons);


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
	 * */
	public List<Object[]> findCheckMoneyByBranch(String systemBookCode,List<Integer> branchNums,Date dateFrom, Date dateTo);

	/**
	 *按分店查询损耗金额		试吃，去皮，报损，其它
	 * @param systemBookCode
	 * @param branchNums 分店号
	 * @param dateFrom 时间起
	 * @param dateTo 时间止
	 * */
	public List<Object[]> findAdjustmentCauseMoneyByBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);

	/**
	 * bi 商品日报损汇总
	 *
	 * */
	public List<Object[]> findItemLoss(String systemBookCode, Date dateFrom, Date dateTo);

}