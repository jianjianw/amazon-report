package com.nhsoft.report.service;

import java.util.Date;
import java.util.List;

/**
 * Created by yangqin on 2017/9/20.
 */
public interface OtherInoutService {
	
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
}
