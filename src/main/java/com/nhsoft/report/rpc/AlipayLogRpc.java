package com.nhsoft.report.rpc;

import com.nhsoft.report.dto.AlipayLogDTO;
import com.nhsoft.report.shared.queryBuilder.LogQuery;

import java.util.List;

public interface AlipayLogRpc {


	public int countByLogQuery(String systemBookCode, Integer branchNum, LogQuery logQuery);

	public List<AlipayLogDTO> findByLogQuery(String systemBookCode, Integer branchNum, LogQuery logQuery, int offset, int limit);



}
