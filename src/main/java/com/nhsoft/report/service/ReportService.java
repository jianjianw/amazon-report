package com.nhsoft.report.service;

import com.nhsoft.report.dto.BusinessCollection;

import java.util.Date;
import java.util.List;

/**
 * Created by yangqin on 2017/9/5.
 */
public interface ReportService {
	
	public Object excuteSql(String sql);
	
	
	/**
	 * 日销售报表
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @parma isMember 是否会员
	 * @return
	 */
	public List<Object[]> findDayWholes(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, boolean isMember);
	
	/**
	 * 月销售报表
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param isMember 是否会员
	 * @return
	 */
	public List<Object[]> findMonthWholes(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, boolean isMember);
	
	/**
	 *  营业收款统计  根据分店汇总
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<BusinessCollection> findBusinessCollectionByBranch(String systemBookCode,
	                                                               List<Integer> branchNums, Date dateFrom, Date dateTo);
	
	/**
	 * 营业收款统计 根据终端汇总
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<BusinessCollection> findBusinessCollectionByTerminal(String systemBookCode,
	                                                                 List<Integer> branchNums, Date dateFrom, Date dateTo);
	
	/**
	 * 营业收款统计 根据班次汇总
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param casher
	 * @return
	 */
	public List<BusinessCollection> findBusinessCollectionByShiftTable(String systemBookCode,
	                                                                   List<Integer> branchNums, Date dateFrom, Date dateTo, String casher);
	
	/**
	 *  营业收款统计  根据分店、营业日汇总
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<BusinessCollection> findBusinessCollectionByBranchDay(String systemBookCode,
	                                                                  List<Integer> branchNums, Date dateFrom, Date dateTo);
	
}
