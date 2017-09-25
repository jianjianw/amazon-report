package com.nhsoft.report.rpc.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.nhsoft.report.dto.AlipayLogDTO;
import com.nhsoft.report.rpc.AlipayLogRpc;
import com.nhsoft.report.service.AlipayLogService;
import com.nhsoft.report.shared.queryBuilder.LogQuery;
import com.nhsoft.report.util.CopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Service
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

	@Override
	public AlipayLogDTO readLast(String systemBookCode, Integer branchNum, String orderNo, String alipayLogType) {
		return CopyUtil.to(alipayLogService.readLast(systemBookCode, branchNum, orderNo, alipayLogType), AlipayLogDTO.class);
	}


}