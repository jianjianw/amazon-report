package com.nhsoft.report.service;

import java.util.Date;
import java.util.List;

/**
 * 调入单
 * @author nhsoft
 *
 */
public interface TransferInOrderService {
	/**
	 * 按商品、多特性编码汇总数量、调入金额、成本金额
	 *
	 * @param systemBookCode
	 * @param inBranchNums     调入分店列表
	 * @param branchNums       调出分店列表
	 * @param dateFrom         审核时间起
	 * @param dateTo           审核时间止
	 * @param categoryCodeList 商品类别代码列表
	 * @param itemNums         商品主键列表
	 * @return
	 */
	public List<Object[]> findProfitGroupByItem(String systemBookCode, List<Integer> inBranchNums, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> categoryCodeList, List<Integer> itemNums);

	public List<Object[]> findMoneyByBranch(String systemBookCode, List<Integer> centerBranchNums, List<Integer> branchNums, Date dateFrom, Date dateTo);

	public List<Object[]> findMoneyByBizday(String systemBookCode, List<Integer> centerBranchNums, List<Integer> branchNums, Date dateFrom, Date dateTo);
}