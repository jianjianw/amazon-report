package com.nhsoft.report.rpc;

import com.nhsoft.report.dto.AlipayLogDTO;
import com.nhsoft.report.shared.queryBuilder.LogQuery;

import java.util.List;

public interface AlipayLogRpc {


	public int countByLogQuery(String systemBookCode, Integer branchNum, LogQuery logQuery);

	public List<AlipayLogDTO> findByLogQuery(String systemBookCode, Integer branchNum, LogQuery logQuery, int offset, int limit);

	/**
	 * 查询上一条记录
	 * @param systemBookCode 帐套号
	 * @param branchNum  分店号
	 * @param orderNo   单据号
	 * @param alipayLogType  支付类型
	 * @return
	 */
	public AlipayLogDTO readLast(String systemBookCode, Integer branchNum, String orderNo, String alipayLogType);


}
