package com.nhsoft.report.dao;


import java.util.Date;
import java.util.List;

public interface InventoryCollectDao {

	
	public Date getPreviousDate(String systemBookCode, Integer branchNum);
	
	public Date getLastDate(String systemBookCode, Integer branchNum);
	
	public Date getDetailLastDate(String systemBookCode, Integer branchNum);

	public Date getDetailPreviousDate(String systemBookCode, Integer branchNum);
	
	public List<Object[]> findSummaryByBranchFlag(String systemBookCode, List<Integer> branchNum,
                                                  Date dateFrom, Date dateTo, String itemCategoryCode, Integer storehouseNum);

	public List<Object[]> findSummaryByBranchItemFlag(String systemBookCode, List<Integer> branchNum, Date dateFrom, Date dateTo);

	public void batchDelete(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo);

	public List<Object[]> findSummaryByItemFlag(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo,
                                                boolean centerStorehouse);

	public List<Object[]> findSummaryByItemMatrixFlag(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo,
                                                      String summary, List<Integer> itemNums, Integer storehouseNum);

	/**
	 * 按商品 天  进出类型 进出标记 汇总数量
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findSummaryByItemDateTypeFlag(String systemBookCode, Integer branchNum, Date dateFrom,
                                                        Date dateTo, List<Integer> itemNums);

	/**
	 * 按分店、进出标记汇总数量 金额 辅助数量
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param collectDetailSummarys
	 * @return
	 */
	public List<Object[]> findBranchFlagSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom,
                                                Date dateTo, String collectDetailSummarys);

	/**
	 * 查询商品最近操作日期
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param itemNums
	 * @param collectDetailSummary
	 * @return
	 */
	public List<Object[]> findItemLatestDate(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<Integer> itemNums, String collectDetailSummary);
}
