package com.nhsoft.module.report.service;

import java.util.Date;
import java.util.List;

/**
 * 批发退货单
 * @author nhsoft
 *
 */
public interface WholesaleReturnService {

	/**
	 * 按商品 多特性编号汇总 数量和赠送数量
	 * @param systemBookCode
	 * @param branchNum
	 * @param clientFids 客户主键列表
	 * @param dateFrom 审核时间起
	 * @param dateTo 审核时间止
	 * @param itemNums  商品主键列表
	 * @param categoryCodes  商品类别代码列表
	 * @return
	 */
	public List<Object[]> findItemSum(String systemBookCode, Integer branchNum, List<String> clientFids, Date dateFrom, Date dateTo,
									  List<Integer> itemNums, List<String> categoryCodes);

	/**
	 * 主供应商汇总数量，金额
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
	public List<Object[]> findSupplierSum(String systemBookCode, List<Integer> branchNums, List<String> clientFids, Date dateFrom,
										  Date dateTo, List<Integer> itemNums, List<String> categoryCodes, List<Integer> regionNums);


	/**
	 * 按商品和主供应商汇总数量，金额
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
	public List<Object[]> findItemSupplierSum(String systemBookCode, List<Integer> branchNums, List<String> clientFids, Date dateFrom,
											  Date dateTo, List<Integer> itemNums, List<String> categoryCodes, List<Integer> regionNums);


	/**
	 * 按门店商品和主供应商汇总数量，金额
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
	public List<Object[]> findBranchItemSupplierSum(String systemBookCode, List<Integer> branchNums, List<String> clientFids, Date dateFrom,
													Date dateTo, List<Integer> itemNums, List<String> categoryCodes, List<Integer> regionNums);



	/**
	 * 按商品，主供应商查询明细
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findItemSupplierDetail(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);


}