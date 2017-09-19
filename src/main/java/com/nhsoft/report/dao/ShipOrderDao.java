package com.nhsoft.report.dao;


import com.nhsoft.report.dto.ShipOrderDTO;
import com.nhsoft.report.shared.queryBuilder.OrderQueryCondition;
import com.nhsoft.report.shared.queryBuilder.ShipRecordingQuery;

import java.util.Date;
import java.util.List;

public interface ShipOrderDao {
	

	
	/**
	 * 按天汇总运费金额
	 * @param systemBookCode
	 * @param branchNum 发货分店号
	 * @param branchNums 收货分店列表
	 * @param dateFrom 审核时间起
	 * @param dateTo 审核时间至
	 * @return
	 */
	public List<Object[]> findDateSummary(String systemBookCode, Integer branchNum,
                                          List<Integer> branchNums, Date dateFrom, Date dateTo);

	/**
	 * 按门店汇总运费金额
	 * @param systemBookCode
	 * @param branchNum 发货分店号
	 * @param branchNums 收货分店列表
	 * @param dateFrom 审核时间起
	 * @param dateTo 审核时间至
	 * @return
	 */
	public List<Object[]> findBranchSummary(String systemBookCode, Integer branchNum,
                                            List<Integer> branchNums, Date dateFrom, Date dateTo);

	/**
	 * 按门店商品汇总运费金额
	 * @param systemBookCode
	 * @param branchNum
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findBranchItemSummary(String systemBookCode, Integer branchNum, List<Integer> branchNums, List<String> categoryCodes, List<Integer> exceptItemNums,
                                                Date dateFrom, Date dateTo);

	/**
	 * 按门店汇总新品数
	 * @param systemBookCode
	 * @param branchNum
	 * @param branchNums
	 * @param newItemNums
	 * @param categoryCodes
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findBranchNewItemSummary(String systemBookCode, Integer branchNum, List<Integer> branchNums, List<Integer> newItemNums, List<String> categoryCodes,
                                                   Date dateFrom, Date dateTo);

	/**
	 * 查询商品收货金额
	 * @param systemBookCode
	 * @param outBranchNum
	 * @param branchNum
	 * @param itemNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findBranchNewItemDetail(String systemBookCode, Integer outBranchNum, Integer branchNum, List<Integer> newItemNums, Date dateFrom, Date dateTo, List<String> categoryCodes);


	/**
	 * 按线路汇总运费金额
	 * @param systemBookCode
	 * @param branchNum 发货分店号
	 * @param branchNums 收货分店列表
	 * @param dateFrom 审核时间起
	 * @param dateTo 审核时间至
	 * @return
	 */
	public List<Object[]> findLineSummary(String systemBookCode, Integer branchNum,
                                          List<Integer> transferLineNums, Date dateFrom, Date dateTo);

	/**
	 * 按线路汇总调出数量、金额
	 * @param systemBookCode
	 * @param branchNum
	 * @param transferLineNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findLineOutSummary(String systemBookCode, Integer branchNum, List<Integer> transferLineNums,
                                             Date dateFrom, Date dateTo);

	/**
	 * 按线路汇总发车趟次 无发车金额的不算
	 * @param systemBookCode
	 * @param branchNum
	 * @param transferLineNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findLineCount(String systemBookCode, Integer branchNum, List<Integer> transferLineNums,
                                        Date dateFrom, Date dateTo);


	/**
	 * 查询shipOrder部分字段
	 * @param systemBookCode
	 * @param branchNum
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<ShipOrderDTO> findShipOrderDTOs(String systemBookCode, Integer branchNum,
												List<Integer> branchNums, Date dateFrom, Date dateTo);

}
