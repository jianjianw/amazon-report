package com.nhsoft.report.dao;

import com.nhsoft.report.shared.queryBuilder.TransferProfitQuery;

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


	/**
	 * 门店汇总调入金额、毛利、数量
	 * @param transferProfitQuery
	 * @return
	 */
	public List<Object[]> findProfitGroupByBranch(TransferProfitQuery transferProfitQuery);


	/**
	 * 按年 月 日汇总门店调入金额
	 * @param systemBookCode
	 * @param inBranchNum
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param dateType
	 * @return
	 */
	public List<Object[]> findBranchSumByDateType(String systemBookCode, Integer inBranchNum,
												  List<Integer> branchNums, Date dateFrom, Date dateTo, String dateType);

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