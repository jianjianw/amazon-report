package com.nhsoft.report.dao;

import com.nhsoft.module.report.query.WholesaleProfitQuery;

import java.util.Date;
import java.util.List;

public interface WholesaleReturnDao {

	/**
	 * 按商品主键汇总 数量、 金额、成本
	 * @param systemBookCode
	 * @param branchNums 分店列表
	 * @param dateFrom 审核时间起
	 * @param dateTo 审核时间止
	 * @return
	 */
	public List<Object[]> findItemSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);

	public List<Object[]> findItemSum(String systemBookCode, Integer branchNum, List<String> cleintFids, Date dateFrom, Date dateTo,
									  List<Integer> itemNums, List<String> categoryCodes, List<Integer> regionNums);


	public List<Object[]> findSupplierSum(String systemBookCode, List<Integer> branchNums, List<String> clientFids, Date dateFrom,
										  Date dateTo, List<Integer> itemNums, List<String> categoryCodes, List<Integer> regionNums);


	public List<Object[]> findItemSupplierSum(String systemBookCode, List<Integer> branchNums, List<String> clientFids, Date dateFrom,
											  Date dateTo, List<Integer> itemNums, List<String> categoryCodes, List<Integer> regionNums);

	public List<Object[]> findBranchItemSupplierSum(String systemBookCode, List<Integer> branchNums, List<String> clientFids, Date dateFrom,
													Date dateTo, List<Integer> itemNums, List<String> categoryCodes, List<Integer> regionNums);

	public List<Object[]> findItemSupplierDetail(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);

	/**
	 * 按商品和日期汇总 数量 销售金额 成本
	 * @param systemBookCode
	 * @param branchNum
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findItemDateSummary(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo);

	/**
	 * 按商品和月份汇总 销售金额和毛利
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param itemNums
	 * @param regionNums
	 * @return
	 */
	public List<Object[]> findMoneyByItemAndMonth(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo,
												  List<Integer> itemNums, List<Integer> regionNums);

	/**
	 * 按客户汇总退货金额 成本金额
	 * @param wholesaleProfitQuery
	 * @return
	 */
	public List<Object[]> findMoneyGroupByClient(WholesaleProfitQuery wholesaleProfitQuery);

	/**
	 * 按商品 多特性编码汇总退货数量 退货金额 退货成本
	 * @param wholesaleProfitQuery
	 * @return
	 */
	public List<Object[]> findMoneyGroupByItemMatrix(WholesaleProfitQuery wholesaleProfitQuery);

	/**
	 * 退货明细
	 * @param wholesaleProfitQuery
	 * @return
	 */
	public List<Object[]> findDetail(WholesaleProfitQuery wholesaleProfitQuery);

	/**
	 * 读取批发毛利报表总合计
	 * @param wholesaleProfitQuery
	 * @return
	 */
	public Object[] readProfitSummary(WholesaleProfitQuery wholesaleProfitQuery);

	/**
	 * 按商品汇总退货数量 退货金额 退货成本
	 * @param wholesaleProfitQuery
	 * @return
	 */
	public List<Object[]> findMoneyGroupByItemNum(WholesaleProfitQuery wholesaleProfitQuery);

	/**
	 * 按客户 商品汇总 批发数量 批发金额 批发成本 零售金额
	 * @param wholesaleProfitQuery
	 * @return
	 */
	public List<Object[]> findMoneyGroupByClientItemNum(WholesaleProfitQuery wholesaleProfitQuery);

	public List<Object[]> findMoneyGroupByBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> itemCategories,
												 List<Integer> itemNums, List<String> clients, List<Integer> regionNums, Integer storehouseNum, String auditor, String dateType, List<String> sellers);


	/**
	 * 根据客户汇总未付金额
	 * @param systemBookCode
	 * @param branchNum
	 * @param supplierNums
	 * @return
	 */
	public List<Object[]> findDueMoney(String systemBookCode, Integer branchNum, List<String> clientFid, Date dateFrom, Date dateTo);

	/**
	 * 按门店统计单据数
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateTo
	 * @param dateFrom
	 * @return
	 */
	public int countByBranch(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo);
}