package com.nhsoft.report.service;

import java.util.Date;
import java.util.List;

/**
 * Created by yangqin on 2017/9/19.
 */
public interface CardUserService {
	
	/**
	 * 查询时间段内发卡量
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param cardUserCardType
	 * @return
	 */
	public List<Object[]> findCardCount(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer cardUserCardType);
	
	/**
	 * 查询卡回收数
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param cardUserCardType
	 * @return
	 */
	public List<Object[]> findRevokeCardCount(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer cardUserCardType);
	
	/**
	 * 按营业日查询卡回收数
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param cardUserCardType
	 * @return
	 */
	public List<Object[]> findRevokeCardCountByBizday(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer cardUserCardType);
}
