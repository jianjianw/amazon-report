package com.nhsoft.module.report.dao;


import com.nhsoft.module.report.model.PosItemLog;
import com.nhsoft.module.report.shared.queryBuilder.StoreQueryCondition;

import java.math.BigDecimal;
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

	public List<PosItemLog> findByStoreQueryCondition(StoreQueryCondition storeQueryCondition, int offset, int limit);

	/**
	 * 按商品、摘要汇总进出库量 和 金额
	 * @param storeQueryCondition
	 * @return
	 */
	public List<Object[]> findItemSummaryInOutQtyAndMoney(StoreQueryCondition storeQueryCondition);

	/**
	 * 按分店、摘要汇总进出库量 和 金额
	 * @param storeQueryCondition
	 * @return
	 */
	public List<Object[]> findBranchSummaryInOutQtyAndMoney(StoreQueryCondition storeQueryCondition);

	/**
	 * 按分店、进出标记汇总进出库数量 和 金额
	 * @param storeQueryCondition
	 * @return
	 */
	public List<Object[]> findBranchInOutSummary(StoreQueryCondition storeQueryCondition);

	/**
	 * 根据摘要查询商品进出库量
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param summaries
	 * @return
	 */
	public List<Object[]> findItemAmountBySummary(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, String summaries);

	/**
	 * 根据摘要查询商品进出库量
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @param summaries
	 * @return
	 */
	public BigDecimal findItemAmountBySummary(String systemBookCode,
                                              Integer branchNum, Date dateFrom, Date dateTo, String summaries, Integer itemNum, Boolean inoutFlag);

	/**
	 * 根据门店 商品 进出库类型汇总 数量 金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param summaries
	 * @param itemNums
	 * @return
	 */
	public List<Object[]> findSumByBranchAndItemFlag(String systemBookCode,
                                                     List<Integer> branchNums, Date dateFrom, Date dateTo, String summaries, List<Integer> itemNums);

    /**
     * 按门店统计单据数
     * @param systemBookCode
     * @param branchNum
     * @return
     */
    public int countByBranch(String systemBookCode, Integer branchNum);


	/**
	 * 根据门店 进出库类型汇总 金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param storehouseNum
	 * @return
	 */
	public List<Object[]> findMoneyByBranchFlag(String systemBookCode,
                                                List<Integer> branchNums, Date dateFrom, Date dateTo, Integer storehouseNum);
	
	/**
	 * 根据门店 商品 进出库类型汇总 金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findMoneyByBranchItemFlag(String systemBookCode,
                                                    List<Integer> branchNums, Date dateFrom, Date dateTo);

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

}
