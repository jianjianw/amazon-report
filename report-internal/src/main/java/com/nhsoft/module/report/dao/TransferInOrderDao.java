package com.nhsoft.module.report.dao;


import com.nhsoft.module.report.model.TransferInOrder;
import com.nhsoft.module.report.queryBuilder.TransferProfitQuery;

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
	
	/**
	 * 按调出分店汇总应付金额
	 * @param systemBookCode
	 * @param inBranchNum 调入分店
	 * @param dateFrom 约定付款日期起
	 * @param dateTo 约定付款日期止
	 * @param branchNums 调出分店列表
	 * @return
	 */
	public List<Object[]> findMoneyByBranchNums(String systemBookCode, Integer inBranchNum,
	                                            Date dateFrom, Date dateTo, List<Integer> branchNums);
	
	/**
	 * 查询未结算
	 * @param systemBookCode
	 * @param branchNum 调出分店
	 * @param inBranchNum  调入分店
	 * @return
	 */
	public BigDecimal readBranchUnPaidMoney(String systemBookCode, Integer branchNum, Integer inBranchNum);
	
	/**
	 * 按条件查询审核单据
	 * @param systemBookCode
	 * @param branchNum 调出分店
	 * @param inBranchNum 调入分店
	 * @param dateFrom 约定付款时间起
	 * @param dateTo 约定付款时间止
	 * @return
	 */
	public List<TransferInOrder> findBySettleBranch(String systemBookCode, Integer branchNum, Integer inBranchNum, Date dateFrom, Date dateTo);
	
	/**
	 * 按调出分店汇总未付金额
	 * @param systemBookCode
	 * @param inBranchNum 调入分店
	 * @param branchNums 调出分店
	 * @param dateFrom 约定付款日期起
	 * @param dateTo 约定付款日期止
	 * @return
	 */
	public List<Object[]> findDueMoney(String systemBookCode, Integer inBranchNum, List<Integer> branchNums, Date dateFrom, Date dateTo);


	/**
	 * 配送中心、调往分店汇总商品调入金额、毛利、数量
	 * @param transferProfitQuery
	 * @return
	 */
	public List<Object[]> findProfitGroupByBranchAndItem(TransferProfitQuery transferProfitQuery);

	/**
	 * 配送毛利 商品明细
	 * @param transferProfitQuery
	 * @return
	 */
	public List<Object[]> findDetails(TransferProfitQuery transferProfitQuery);
}