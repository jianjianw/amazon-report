package com.nhsoft.module.report.service;

import java.util.Date;
import java.util.List;

/**
 * 批发销售单
 * @author nhsoft
 *
 */
public interface WholesaleOrderService {

	/**
	 * 按商品汇总数据
	 * @param systemBookCode
	 * @param branchNum
	 * @param clientFids
	 * @param dateFrom
	 * @param dateTo
	 * @param itemNums
	 * @param categoryCodes
	 * @param regionNums
	 * @return
	 */
	public List<Object[]> findItemSum(String systemBookCode, Integer branchNum, List<String> clientFids, Date dateFrom, Date dateTo,
									  List<Integer> itemNums, List<String> categoryCodes, List<Integer> regionNums);


	/**
	 * 按供应商汇总批发信息
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param itemNums
	 * @param regionNums
	 * @return
	 */
	public List<Object[]> findSupplierSum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> categoryCodes,
										  List<Integer> itemNums, List<Integer> regionNums);


	/**
	 * 按商品和主供应商查询批发信息
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param itemNums
	 * @param regionNums
	 * @return
	 */
	public List<Object[]> findItemSupplierSum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo,
											  List<Integer> itemNums, List<Integer> regionNums);

	/**
	 * 按门店商品和主供应商查询批发信息
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param itemNums
	 * @param regionNums
	 * @return
	 */
	public List<Object[]> findBranchItemSupplierSum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo,
													List<Integer> itemNums, List<Integer> regionNums);




	/**
	 * 按商品和主供应商查询单据明细
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param regionNums
	 * @return
	 */
	public List<Object[]> findItemSupplierDetail(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo,
												 List<Integer> regionNums);


	/**
	 * 根据营业日汇总批发数量和金额
	 * */
	public List<Object[]> findAmountAndMoneyByBiz(String systemBookCode, Date dateFrom, Date dateTo, List<Integer> itemNums);


	/**
	 * 按分店、商品汇总批发数量 批发金额 批发成本
	 * @param systemBookCode
	 * @param dateFrom
	 * @param dateTo
	 * @param itemNums
	 * @param clients
	 * @return
	 */
	public List<Object[]> findMoneyGroupByBranchItem(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo,
													 List<Integer> itemNums, List<String> clients, List<Integer> regionNums);

}