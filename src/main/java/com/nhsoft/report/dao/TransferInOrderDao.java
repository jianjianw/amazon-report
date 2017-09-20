package com.nhsoft.report.dao;

import java.util.Date;
import java.util.List;

public interface TransferInOrderDao {

	/**
	 * 商品汇总调入金额、毛利、数量
	 * @param systemBookCode
	 * @param transferBranchNum
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param categoryCodeList
	 * @param itemNums
	 * @return
	 */
	public List<Object[]> findProfitGroupByItem(String systemBookCode, List<Integer> transferBranchNums, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> categoryCodeList, List<Integer> itemNums);


	/**
	 * 按商品和天汇总金额和毛利
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param itemNums
	 * @return
	 */
	public List<Object[]> findMoneyByItemDate(String systemBookCode, List<Integer> transferBranchNums, List<Integer> branchNums,
											  Date dateFrom, Date dateTo, List<Integer> itemNums);


	/**
	 * 按商品和月份汇总金额和毛利
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param itemNums
	 * @return
	 */
	public List<Object[]> findMoneyByItemAndMonth(String systemBookCode, List<Integer> transferBranchNums,
												  Date dateFrom, Date dateTo, List<Integer> itemNums);
}