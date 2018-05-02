package com.nhsoft.module.report.rpc.impl;

import com.nhsoft.module.report.dto.CardUserLogDTO;
import com.nhsoft.module.report.model.CardUserLog;
import com.nhsoft.module.report.queryBuilder.CardReportQuery;
import com.nhsoft.module.report.rpc.CardUserLogRpc;
import com.nhsoft.module.report.service.CardUserLogService;
import com.nhsoft.report.utils.CopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CardUserLogRpcImpl implements CardUserLogRpc {

	@Autowired
	private CardUserLogService cardUserLogService;


	@Override
	public List<CardUserLogDTO> findByCardReportQuery(String systemBookCode, CardReportQuery cardReportQuery, int offset, int limit) {
		return CopyUtil.toList(cardUserLogService.findByCardReportQuery(cardReportQuery, offset, limit), CardUserLogDTO.class);
	}

	@Override
	public int countByCardReportQuery(String systemBookCode, CardReportQuery cardReportQuery, int offset, int limit) {
		return cardUserLogService.countByCardReportQuery(cardReportQuery, offset, limit);
	}
}