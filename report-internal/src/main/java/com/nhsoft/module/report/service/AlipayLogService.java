package com.nhsoft.module.report.service;

import com.nhsoft.module.report.model.AlipayLog;
import com.nhsoft.module.report.query.LogQuery;

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
