package com.nhsoft.report.dao;

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

}