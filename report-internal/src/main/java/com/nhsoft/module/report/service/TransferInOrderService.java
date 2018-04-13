package com.nhsoft.module.report.service;

import com.nhsoft.module.report.model.TransferInOrder;
import com.nhsoft.module.report.queryBuilder.TransferProfitQuery;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 调入单
 * @author nhsoft
 *
 */
public interface TransferInOrderService {
	/**
	 * 按商品、多特性编码汇总数量、调入金额、成本金额
	 *
	 * @param systemBookCode
	 * @param inBranchNums     调入分店列表
	 * @param branchNums       调出分店列表
	 * @param dateFrom         审核时间起
	 * @param dateTo           审核时间止
	 * @param categoryCodeList 商品类别代码列表
	 * @param itemNums         商品主键列表
	 * @return
	 */
	public List<Object[]> findProfitGroupByItem(String systemBookCode, List<Integer> inBranchNums, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> categoryCodeList, List<Integer> itemNums);

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
	 * 按调出分店、调入分店、商品、多特性编码汇总基本数量、成本金额、调入金额、销售金额
	 * @param systemBookCode
	 * @param inBranchNums 调入分店列表
	 * @param branchNums 调出分店列表
	 * @param dateFrom 审核时间起
	 * @param dateTo 审核时间止
	 * @param categoryCodeList 商品类别代码列表
	 * @param itemNums 商品主键列表
	 * @return
	 */
	public List<Object[]> findProfitGroupByBranchAndItem(String systemBookCode, List<Integer> inBranchNums, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> categoryCodeList, List<Integer> itemNums);


	/**
	 * 按调出分店、调入分店、商品、多特性编码汇总基本数量、成本金额、调入金额、销售金额
	 * @param transferProfitQuery
	 * @return
	 */
	public List<Object[]> findProfitGroupByBranchAndItem(TransferProfitQuery transferProfitQuery);


	/**
	 * 调入明细(部分字段)
	 * @param transferProfitQuery
	 * @return
	 */
	public List<Object[]> findDetails(TransferProfitQuery transferProfitQuery);

}