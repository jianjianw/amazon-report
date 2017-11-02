package com.nhsoft.module.report.dao;


import com.nhsoft.module.report.model.PosItemLog;
import com.nhsoft.module.report.query.StoreQueryCondition;

import java.util.Date;
import java.util.List;

public interface PosItemLogDao {
	
	/**
	 * 查询时间范围内有出入库记录的商品
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Integer> findItemNum(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, Boolean inOut);

	public List<PosItemLog> findByStoreQueryCondition(StoreQueryCondition storeQueryCondition, int offset, int limit);

	/**
	 * 查询商品最高进价和最低进价
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param itemNums
	 * @return
	 */
	public List<Object[]> findItemPrice(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, List<Integer> itemNums);

	/**
	 * 根据商品 进出库类型汇总 数量 金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param summaries
	 * @param itemNums
	 * @param storehouseNum
	 * @param memos
	 * @return
	 */
	public List<Object[]> findSumByItemFlag(String systemBookCode,
                                            List<Integer> branchNums, Date dateFrom, Date dateTo, String summaries, List<Integer> itemNums, Integer storehouseNum, List<String> memos);

	/**
	 * 按商品 多特性汇总进出库量 和 金额
	 * @param storeQueryCondition
	 * @return
	 */
	public List<Object[]> findItemMatrixInOutQtyAndMoney(StoreQueryCondition storeQueryCondition);


	/**
	 * 根据分店 商品 进出库类型 汇总 数量 金额 辅助数量
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param summaries
	 * @param itemNums
	 * @param storehouseNum
	 * @return
	 */
	public List<Object[]> findBranchItemFlagSummary(String systemBookCode,
                                                    List<Integer> branchNums, Date dateFrom, Date dateTo, String summaries, List<Integer> itemNums, Integer storehouseNum);

	/**
	 * 根据分店 营业日 商品 多特性编码 进出库类型汇总 数量 金额 辅助数量
	 * @param systemBookCode
	 * @param branchNums 进出分店列表
	 * @param dateFrom 进出时间起
	 * @param dateTo 进出时间止
	 * @param summaries 进出类型  有多个用逗号隔开
	 * @param itemNums 商品主键列表
	 * @param storehouseNum 仓库号
	 * @param memos 调整类型名称列表
	 * @return
	 */
	public List<Object[]> findSumByBranchDateItemFlag(String systemBookCode,
                                                      List<Integer> branchNums, Date dateFrom, Date dateTo, String summaries, List<Integer> itemNums,
                                                      Integer storehouseNum, List<String> memos);

	/**
	 * 按商品 批次号汇总 数量 金额 毛利 常用数量
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param itemNums
	 * @param summaries
	 * @return
	 */
	public List<Object[]> findItemLotSummary(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo,
                                             List<Integer> itemNums, List<String> summaries);
	
	/**
	 * 查询商品的最近价格 最近操作日期
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param itemNums
	 * @return
	 */
	public List<Object[]> findItemLatestPriceDate(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, List<Integer> itemNums, String posItemLogSummary);
	
	/**
	 * 按商品、日期、类型、进出标记汇总
	 * @param storeQueryCondition
	 * @return
	 */
	public List<Object[]> findItemBizTypeFlagSummary(StoreQueryCondition storeQueryCondition);
	
	/**
	 * 按商品、进出标记汇总
	 * @param storeQueryCondition
	 * @return
	 */
	public List<Object[]> findItemFlagSummary(StoreQueryCondition storeQueryCondition);
	
	/**
	 * 按分店、进出标记汇总
	 * @param storeQueryCondition
	 * @return
	 */
	public List<Object[]> findBranchFlagSummary(StoreQueryCondition storeQueryCondition);
}
