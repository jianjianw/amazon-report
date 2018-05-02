package com.nhsoft.module.report.dao;

import com.nhsoft.module.report.model.CardUserLog;
import com.nhsoft.module.report.queryBuilder.CardReportQuery;

import java.util.Date;
import java.util.List;

public interface CardUserRegisterDao {
	
	
	/**
	 * 按门店汇总发卡数量
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findBranchDeliverCount(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);

	public List<Object[]> findSalerSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> salerNames);


	/**
	 * 拼装成CardUserLog对象
	 * @param cardReportQuery
	 * @param limit
	 * @param offset
	 * @return
	 */
	public List<CardUserLog> findByCardReportQuery(CardReportQuery cardReportQuery, int offset, int limit);

}
