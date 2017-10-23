package com.nhsoft.module.report.dao;


import com.nhsoft.module.report.shared.queryBuilder.TransferProfitQuery;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface TransferInOrderDao {


	/**
	 * 按商品主键汇总基本数量 金额 常用数量
	 * @param systemBookCode
	 * @param inBranchNum
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param itemNums
	 * @return
	 */
	public List<Object[]> findItemSummary(String systemBookCode, Integer inBranchNum, List<Integer> branchNums,
										  Date dateFrom, Date dateTo, List<Integer> itemNums);

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


	public BigDecimal findBalance(String systemBookCode,
								  Integer centerBranchNum, Integer branchNum, Date dtFrom, Date dtTo);

	public List<Object[]> findMoneyByBranch(String systemBookCode, List<Integer> centerBranchNums, List<Integer> branchNums, Date dateFrom, Date dateTo);

	public List<Object[]> findMoneyByBizday(String systemBookCode, List<Integer> centerBranchNums, List<Integer> branchNums, Date dateFrom, Date dateTo);
}