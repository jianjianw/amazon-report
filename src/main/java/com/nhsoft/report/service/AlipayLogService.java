package com.nhsoft.report.service;


import com.nhsoft.report.dto.LogQuery;
import com.nhsoft.report.model.AlipayLog;

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
	 * 查询上一条记录
	 * @param systemBookCode 帐套号
	 * @param branchNum  分店号
	 * @param orderNo   单据号
	 * @param alipayLogType  支付类型
	 * @return
	 */
	public AlipayLog readLast(String systemBookCode, Integer branchNum, String orderNo, String alipayLogType);

	

}
