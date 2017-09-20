package com.nhsoft.report.dao;


import java.util.Date;
import java.util.List;

public interface InventoryCollectDao {



	public List<Object[]> findSummaryByItemFlag(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo,
                                                boolean centerStorehouse);

	public List<Object[]> findSummaryByItemMatrixFlag(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo,
                                                      String summary, List<Integer> itemNums, Integer storehouseNum);

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
