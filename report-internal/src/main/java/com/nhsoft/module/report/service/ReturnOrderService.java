package com.nhsoft.module.report.service;


import java.util.Date;
import java.util.List;

/**
 * 退货单
 * @author nhsoft
 *
 */
public interface ReturnOrderService {


	/**
	 * 按供应商查询退货信息
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param categoryCodes
	 * @param itemNums
	 * @return
	 */
	public List<Object[]> findSupplierAmountAndMoney(String systemBookCode,
													 List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> categoryCodes, List<Integer> itemNums);


	/**
	 * 按商品供应商查询退货信息
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param categoryCodes
	 * @param itemNums
	 * @return
	 */
	public List<Object[]> findItemSupplierAmountAndMoney(String systemBookCode,
														 List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> categoryCodes, List<Integer> itemNums);


	/**
	 * 按门店商品供应商查询退货信息
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param categoryCodes
	 * @param itemNums
	 * @return
	 */
	public List<Object[]> findBranchItemSupplierAmountAndMoney(String systemBookCode,
															   List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> categoryCodes, List<Integer> itemNums);


	/**
	 * 根据供应商查询明细
	 * @param systemBookCode
	 * @param branchNum 操作分店号
	 * @param supplierNum 供应商主键号
	 * @param dateFrom 审核时间起
	 * @param dateTo 审核时间止
	 * @param itemNums 商品主键列表
	 * @return
	 */
	public List<Object[]> findDetailBySupplierNum(String systemBookCode, List<Integer> branchNums, Integer supplierNum,
												  Date dateFrom, Date dateTo, List<Integer> itemNums);

	/**
	 * 查询退货汇总
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findReturnMonth(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo,String strDate);

}