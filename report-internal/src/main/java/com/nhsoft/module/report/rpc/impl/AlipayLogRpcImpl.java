package com.nhsoft.module.report.rpc.impl;


import com.nhsoft.module.report.dto.AlipayLogDTO;
import com.nhsoft.module.report.dto.PayFailDTO;
import com.nhsoft.module.report.rpc.AlipayLogRpc;

import com.nhsoft.module.report.query.LogQuery;

import com.nhsoft.module.report.service.AlipayLogService;
import com.nhsoft.report.utils.CopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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

	@Override
	public List<PayFailDTO> findBranchSummaryPayFail(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, boolean isDeposit, String alipayLogTypes) {

		List<Object[]> objects = alipayLogService.findBranchSummaryPayFail(systemBookCode, branchNums, dateFrom, dateTo, isDeposit, alipayLogTypes);
		int size = objects.size();
		List<PayFailDTO> list = new ArrayList<>(size);
		for (int i = 0; i <size ; i++) {
			Object[] object = objects.get(i);
			PayFailDTO dto = new PayFailDTO();
			dto.setBranchNum((Integer) object[0]);
			dto.setLogMoney((BigDecimal) object[1]);
			dto.setLogId((Long) object[2]);
			list.add(dto);

		}
		return list;
	}


}