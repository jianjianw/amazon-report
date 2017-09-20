package com.nhsoft.report.dao;

import com.nhsoft.report.shared.queryBuilder.WholesaleProfitQuery;

import java.util.Date;
import java.util.List;

public interface WholesaleReturnDao {


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

}