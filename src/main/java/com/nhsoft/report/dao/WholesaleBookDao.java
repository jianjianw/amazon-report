package com.nhsoft.report.dao;



import com.nhsoft.report.model.WholesaleBook;

import java.util.Date;
import java.util.List;

public interface WholesaleBookDao {

	/**
	 * 按门店统计单据数
	 * @param systemBookCode
	 * @param branchNum
	 * @param dateTo
	 * @param dateFrom
	 * @param regionNums
	 * @return
	 */
	public int countByBranch(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, List<Integer> regionNums);


	public WholesaleBook read(String fid);


	/**
	 * 按商品汇总数量 金额
	 * @param systemBookCode
	 * @param branchNum
	 * @param clientFid
	 * @param dateFrom
	 * @param dateTo
	 * @param itemNums
	 * @param categoryCodes
	 * @param regionNums
	 * @return
	 */
	public List<Object[]> findItemSum(String systemBookCode, Integer branchNum, String clientFid, Date dateFrom, Date dateTo, List<Integer> itemNums, List<String> categoryCodes, List<Integer> regionNums);
}