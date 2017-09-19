package com.nhsoft.report.service;

import java.util.Date;
import java.util.List;

/**
 * 调出单
 * @author nhsoft
 *
 */
public interface TransferOutOrderService {

	/**
	 * 按商品、多特性编码汇总基本数量，调出金额，成本金额
	 * @param systemBookCode
	 * @param outBranchNums 调出分店列表
	 * @param branchNums 调入分店列表
	 * @param dateFrom 审核时间起
	 * @param dateTo 审核时间止
	 * @param categoryCodeList 商品类别代码列表
	 * @param itemNums 商品主键列表
	 * @return
	 */
	public List<Object[]> findProfitGroupByItem(String systemBookCode, List<Integer> outBranchNums, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> categoryCodeList, List<Integer> itemNums);


	/**
	 * 按商品 分店 汇总金额
	 * @param systemBookCode
	 * @param centerBranchNum
	 * @param branchNums
	 * @param dateFrom 审核时间起
	 * @param dateTo 审核时间止
	 * @return
	 */
	public List<Object[]> findBranchItemMatrixSummary(String systemBookCode, Integer centerBranchNum, List<Integer> branchNums,
													  Date dateFrom, Date dateTo, List<Integer> itemNums);

}