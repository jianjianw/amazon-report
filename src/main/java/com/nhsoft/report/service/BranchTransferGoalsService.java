package com.nhsoft.report.service;

import java.util.Date;
import java.util.List;

/**
 * Created by yangqin on 2017/9/20.
 */
public interface BranchTransferGoalsService {
	
	/**
	 * 根据时间汇总
	 * @param systemBookCode 帐套号
	 * @param branchNums 分店号列表
	 * @param dateFrom 时间起
	 * @param dateTo 时间止
	 * @param dateType 月 或 年
	 * @return
	 */
	public List<Object[]> findSummaryByDate(String systemBookCode,
	                                        List<Integer> branchNums, Date dateFrom, Date dateTo, String dateType);
}
