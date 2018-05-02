package com.nhsoft.module.report.rpc.impl;

import com.nhsoft.module.report.dto.ConsumePointDTO;
import com.nhsoft.module.report.queryBuilder.CardReportQuery;
import com.nhsoft.module.report.rpc.ConsumePointRpc;
import com.nhsoft.module.report.service.ConsumePointService;
import com.nhsoft.report.utils.CopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class ConsumePointRpcImpl implements ConsumePointRpc {

	@Autowired
	private ConsumePointService consumePointService;

	@Override
	public List<ConsumePointDTO> findByCardReportQuery(String systemBookCode, CardReportQuery cardReportQuery, int offset, int limit) {
		return CopyUtil.toList(consumePointService.findByCardReportQuery(cardReportQuery, offset, limit), ConsumePointDTO.class);
	}

	@Override
	public Object[] sumByCardReportQuery(String systemBookCode, CardReportQuery cardReportQuery) {
		return consumePointService.sumByCardReportQuery(cardReportQuery);
	}


}