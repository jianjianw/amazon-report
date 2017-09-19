package com.nhsoft.report.dao;

import java.util.Date;
import java.util.List;

public interface CardUserLogDao {
	
	/**
	 * 按门店汇总卡回收数量
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findBranchRevokeCount(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);
	
	/**
	 * 按门店汇总记录数
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param cardUserLogType
	 * @param cardUserCardType
	 * @return
	 */
	public List<Object[]> findBranchCount(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String cardUserLogType, Integer cardUserCardType);
	
	/**
	 * 按营业日汇总记录数
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param cardUserLogType
	 * @param cardUserCardType
	 * @return
	 */
	public List<Object[]> findBizdayCount(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String cardUserLogType, Integer cardUserCardType);

}
