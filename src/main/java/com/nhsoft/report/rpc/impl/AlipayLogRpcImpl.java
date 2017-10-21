package com.nhsoft.report.rpc.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.nhsoft.module.report.dto.AlipayLogDTO;
import com.nhsoft.module.report.rpc.AlipayLogRpc;
import com.nhsoft.report.service.AlipayLogService;
import com.nhsoft.module.report.query.LogQuery;
import com.nhsoft.report.util.CopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class AlipayLogRpcImpl implements AlipayLogRpc {

	@Autowired
	private AlipayLogService alipayLogService;


	@Override
	public int countByLogQuery(String systemBookCode, Integer branchNum, LogQuery logQuery) {
		return alipayLogService.countByLogQuery(systemBookCode, branchNum, logQuery);
	}

	@Override
	public List<AlipayLogDTO> findByLogQuery(String systemBookCode, Integer branchNum, LogQuery logQuery, int offset, int limit) {
		return CopyUtil.toList(alipayLogService.findByLogQuery(systemBookCode, branchNum, logQuery, offset, limit), AlipayLogDTO.class);
	}


}