package com.nhsoft.module.report.service;

import com.nhsoft.module.report.model.AlipayLog;
import com.nhsoft.module.report.query.LogQuery;

import java.util.Date;
import java.util.List;

/**
 * 线上支付日志
 * @author nhsoft
 *
 */
public interface AlipayLogService {
	

	
	public int countByLogQuery(String systemBookCode, Integer branchNum, LogQuery logQuery);
	
	public List<AlipayLog> findByLogQuery(String systemBookCode, Integer branchNum, LogQuery logQuery, int offset, int limit);

	/**
	 * 按分店汇总支付失败金额、次数
	 * @param systemBookCode
	 * @param branchNums
	 * @param dateFrom
	 * @param dateTo
	 * @param isDeposit 是否存款单据
	 * @param alipayLogTypes
	 * @return
	 */
	public List<Object[]> findBranchSummaryPayFail(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo,
												   boolean isDeposit, String alipayLogTypes);

	public List<AlipayLog> test(String systemBookCode,Date dateFrom,Date dateTo);

}
