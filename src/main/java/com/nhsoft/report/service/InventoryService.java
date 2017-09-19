package com.nhsoft.report.service;


import com.nhsoft.report.model.*;
import com.nhsoft.report.shared.queryBuilder.InventoryQuery;

import java.math.BigDecimal;
import java.util.List;

/**
 * 库存
 * @author nhsoft
 *
 */
public interface InventoryService {
	/**
	 * 按商品主键汇总库存数量 金额
	 * @param systemBookCode
	 * @param branchNums 分店号
	 * @param itemNums 商品主键列表
	 * @return
	 */
	public List<Object[]> findItemAmount(String systemBookCode, List<Integer> branchNums, List<Integer> itemNums);

	/**
	 * 按分店 商品主键汇总 库存数量 金额
	 * @param systemBookCode
	 * @param branchNums 分店号列表
	 * @return
	 */
	public List<Object[]> findBranchItemSummary(String systemBookCode, List<Integer> branchNums);
}