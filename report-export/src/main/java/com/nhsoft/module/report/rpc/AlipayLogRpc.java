package com.nhsoft.module.report.rpc;

import com.nhsoft.module.report.dto.AlipayLogDTO;
import com.nhsoft.module.report.query.LogQuery;


import java.util.List;

public interface AlipayLogRpc {


	public int countByLogQuery(String systemBookCode, Integer branchNum, LogQuery logQuery);

	public List<AlipayLogDTO> findByLogQuery(String systemBookCode, Integer branchNum, LogQuery logQuery, int offset, int limit);



}
