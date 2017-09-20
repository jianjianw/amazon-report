package com.nhsoft.report.service;


import com.nhsoft.report.model.AdjustmentOrder;
import com.nhsoft.report.model.AdjustmentOrderDetail;
import com.nhsoft.report.model.OrderQueue;
import com.nhsoft.report.shared.queryBuilder.OrderQueryCondition;

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

}