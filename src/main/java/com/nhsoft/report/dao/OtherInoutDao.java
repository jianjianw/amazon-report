package com.nhsoft.report.dao;



import java.util.Date;
import java.util.List;

public interface OtherInoutDao {
	
	/**
	 * 按分店、标记、收支类型汇总前台金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public List<Object[]> findPosBranchFlagKindSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom,
	                                                   Date dateTo);
	public List<Object[]> findClientsMoney(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> clientFids);
}