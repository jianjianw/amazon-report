package com.nhsoft.module.report.dao;

import com.nhsoft.module.report.model.RetailPosLog;
import com.nhsoft.module.report.query.LogQuery;

import java.util.Date;
import java.util.List;


public interface RetailPosLogDao {

	public List<RetailPosLog> findByQuery(String systemBookCode,
										  Integer branchNum, LogQuery queryCondition,
										  Integer offset, Integer limit);

	/**
	 * 按操作类型汇总次数和金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param logTypes
	 * @return
	 */
	public List<Object[]> findTypeCountAndMoney(String systemBookCode, List<Integer> branchNums, Date dateFrom,
	                                            Date dateTo, String logTypes);

	/**
	 * 按操作类型汇总次数和金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param logTypes
	 * @return
	 */
	public List<Object[]> findTypeCountAndMoneyByOrder(String systemBookCode, List<Integer> branchNums, Date dateFrom,
	                                                   Date dateTo, String logTypes);


}
