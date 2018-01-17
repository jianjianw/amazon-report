package com.nhsoft.module.report.service;

import com.nhsoft.module.report.model.TransferOutOrder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 调出单
 * @author nhsoft
 *
 */
public interface TransferOutOrderService {


	public List<Object[]> findMoneyByBizday(String systemBookCode, List<Integer> centerBranchNums, List<Integer> branchNums, Date dateFrom, Date dateTo);

	public List<Object[]> findUnTransferedItems(String systemBookCode, Integer outBranchNum, List<Integer> branchNums, List<Integer> storehouseNums);

	/**
	 * 按商品、多特性编码汇总基本数量，调出金额，成本金额
	 * @param systemBookCode
	 * @param outBranchNums 调出分店列表
	 * @param branchNums 调入分店列表
	 * @param dateFrom 审核时间起
	 * @param dateTo 审核时间止
	 * @param categoryCodeList 商品类别代码列表
	 * @param itemNums 商品主键列表
	 * @return
	 */
	public List<Object[]> findProfitGroupByItem(String systemBookCode, List<Integer> outBranchNums, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> categoryCodeList, List<Integer> itemNums);


	/**
	 * 按商品 分店 汇总金额
	 * @param systemBookCode
	 * @param centerBranchNum
	 * @param branchNums
	 * @param dateFrom 审核时间起
	 * @param dateTo 审核时间止
	 * @return
	 */
	public List<Object[]> findBranchItemMatrixSummary(String systemBookCode, Integer centerBranchNum, List<Integer> branchNums,
													  Date dateFrom, Date dateTo, List<Integer> itemNums);

	public List<Object[]> findMoneyByBranch(String systemBookCode, List<Integer> centerBranchNums, List<Integer> branchNums, Date dateFrom, Date dateTo);

	/***
	 * 按分店查询配送额
	 * @param systemBookCode
	 * @param branchNums 分店号
	 * @param dateFrom 时间起
	 * @param dateTo 时间止
	 */
	public List<Object[]> findMoneyBranchSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);

	/***
	 * 按营业日查询配送额
	 * @param systemBookCode
	 * @param branchNums 分店号
	 * @param dateFrom 时间起
	 * @param dateTo 时间止
	 */
	public List<Object[]> findMoneyBizdaySummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);

	/***
	 * 按月份查询配送额
	 * @param systemBookCode
	 * @param branchNums 分店号
	 * @param dateFrom 时间起
	 * @param dateTo 时间止
	 */
	public List<Object[]> findMoneyBymonthSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);
	
	/**
	 * 查询配送过的商品编码
	 * @param systemBookCode
	 * @param branchNum
	 * @return
	 */
	public List<Integer> findTransferedItems(String systemBookCode, Integer branchNum, Integer outBranchNum);
	
	
	/**
	 * 按调入分店汇总 应付金额
	 * @param systemBookCode
	 * @param outBranchNum 调出分店
	 * @param dateFrom 约定付款日期起
	 * @param dateTo  约定付款日期止
	 * @param branchNums 调入分店列表
	 * @return
	 */
	public List<Object[]> findMoneyByBranchNums(String systemBookCode, Integer outBranchNum,
	                                            Date dateFrom, Date dateTo, List<Integer> branchNums);
	
	/**
	 * 按调入分店汇总未付金额
	 * @param systemBookCode
	 * @param outBranchNum 调出分店
	 * @param branchNums 调入分店列表
	 * @param dateFrom 约定付款日期起
	 * @param dateTo 约定付款日期止
	 * @return
	 */
	public List<Object[]> findDueMoney(String systemBookCode, Integer outBranchNum, List<Integer> branchNums, Date dateFrom, Date dateTo);
	
	/**
	 * 查询未结算金额
	 * @param systemBookCode
	 * @param branchNum 调入分店
	 * @param outBranchNum 调出分店
	 * @return
	 */
	public BigDecimal readBranchUnPaidMoney(String systemBookCode, Integer branchNum, Integer outBranchNum);
	
	/**
	 * 查询
	 * @param systemBookCode
	 * @param branchNum 调入分店
	 * @param outBranchNum 调出分店
	 * @param dateFrom 约定付款日期起
	 * @param dateTo 约定付款日期止
	 * @return
	 */
	public List<TransferOutOrder> findBySettleBranch(String systemBookCode, Integer branchNum, Integer outBranchNum, Date dateFrom, Date dateTo);

	/**
	 * 按商品主键汇总基本数量 金额 常用数量
	 * @param systemBookCode
	 * @param outBranchNums
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param itemNums
	 * @return
	 */
	public List<Object[]> findItemSummary(String systemBookCode, List<Integer> outBranchNums, List<Integer> branchNums,
										  Date dateFrom, Date dateTo, List<Integer> itemNums);
}