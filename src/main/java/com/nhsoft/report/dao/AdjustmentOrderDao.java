package com.nhsoft.report.dao;

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

}