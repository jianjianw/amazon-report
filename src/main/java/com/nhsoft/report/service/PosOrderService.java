package com.nhsoft.report.service;


import com.nhsoft.report.dto.ItemQueryDTO;
import com.nhsoft.report.shared.queryBuilder.CardReportQuery;

import java.util.Date;
import java.util.List;

/**
 * 前台销售单
 * @author nhsoft
 *
 */
public interface PosOrderService {

	public List<Object[]> findCustomReportByBizday(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);

	public List<Object[]> findSummaryByBizday(CardReportQuery cardReportQuery);

	/**
	 * 根据门店汇总 单据数 单据总额 折扣总额 积分总额
	 * @param cardReportQuery
	 * @return
	 */
	public List<Object[]> findSummaryByBranch(CardReportQuery cardReportQuery);

	/**
	 * 按商品主键汇总销售数量 销售金额 毛利金额
	 * @param systemBookCode
	 * @param branchNums  销售分店列表
	 * @param dateFrom 营业日起
	 * @param dateTo 营业日止
	 * @param itemNums 商品主键列表
	 * @param queryKit 组合商品是否按明细查询
	 * @return
	 */
	public List<Object[]> findItemSum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo,
									  List<Integer> itemNums, boolean queryKit);
	/**
	 * 按门店汇总明细金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param itemNums
	 * @param queryKit 组合商品是否按明细查询
	 * @return
	 */
	public List<Object[]> findBranchDetailSum(String systemBookCode, List<Integer> branchNums, Date dateFrom,
											  Date dateTo, List<Integer> itemNums, boolean queryKit);



	/**
	 * 按分店、商品主键汇总销售数量 销售金额 毛利金额
	 * @param systemBookCode
	 * @param branchNums  销售分店列表
	 * @param dateFrom 营业日起
	 * @param dateTo 营业日止
	 * @param itemNums 商品主键列表
	 * @param queryKit 组合商品是否按明细查询
	 * @return
	 */
	public List<Object[]> findBranchItemSum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo,
											List<Integer> itemNums, boolean queryKit);


	public List<Object[]> findItemSum(ItemQueryDTO itemQueryDTO);


	public List<Object[]> findBranchItemSum(ItemQueryDTO itemQueryDTO);



	/**
	 * 按营业月 商品 汇总 销售金额 销售数量
	 * @param systemBookCode
	 * @param branchNums 销售分店列表
	 * @param dateFrom 营业日起
	 * @param dateTo 营业日止
	 * @param itemNums 商品主键列表
	 * @return
	 */
	public List<Object[]> findBizmonthItemSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom,
												  Date dateTo, List<Integer> itemNums);


	/**
	 * 商品主键、多特性 汇总销售数量 销售金额 毛利金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param itemNums
	 * @return
	 */
	public List<Object[]> findItemSum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);



	/**
	 * 按供应商汇总数据
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param queryKit
	 * @return
	 */
	public List<Object[]> findSupplierSum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> categoryCodes, List<Integer> itemNums,
										  boolean queryKit);


	/**
	 * 按商品供应商查询销售数据
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param queryKit
	 * @return
	 */
	public List<Object[]> findItemSupplierMatrixSum(String systemBookCode, List<Integer> branchNums, Date dateFrom,
													Date dateTo, boolean queryKit);


	/**
	 * 按商品分店供应商查询销售数据
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param categoryCodes
	 * @param queryKit
	 * @param itemNums
	 * @return
	 */
	public List<Object[]> findItemSupplierSumByCategory(String systemBookCode, List<Integer> branchNums, Date dateFrom,
														Date dateTo, List<String> categoryCodes, boolean queryKit, List<Integer> itemNums);


	/**
	 * 按供应商查询销售明细
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param categoryCodes
	 * @param queryKit
	 * @return
	 */
	public List<Object[]> findOrderDetailWithSupplier(String systemBookCode, List<Integer> branchNums, Date dateFrom,
													  Date dateTo, List<String> categoryCodes, boolean queryKit);


	public List<Object[]> findCustomReportByBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String dateType);
}

