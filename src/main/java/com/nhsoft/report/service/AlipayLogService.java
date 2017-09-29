package com.nhsoft.report.service;


import com.nhsoft.report.model.AlipayLog;
import com.nhsoft.report.shared.queryBuilder.LogQuery;

import java.util.List;

/**
 * 线上支付日志
 * @author nhsoft
 *
 */
public interface AlipayLogService {
	

	
	public int countByLogQuery(String systemBookCode, Integer branchNum, LogQuery logQuery);
	
	public List<AlipayLog> findByLogQuery(String systemBookCode, Integer branchNum, LogQuery logQuery, int offset, int limit);


	

}
