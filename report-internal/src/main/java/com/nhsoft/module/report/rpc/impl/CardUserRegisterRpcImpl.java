package com.nhsoft.module.report.rpc.impl;

import com.nhsoft.module.report.dto.CardUserLogDTO;
import com.nhsoft.module.report.queryBuilder.CardReportQuery;
import com.nhsoft.module.report.rpc.CardUserRegisterRpc;
import com.nhsoft.module.report.service.CardUserRegisterService;
import com.nhsoft.report.utils.CopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CardUserRegisterRpcImpl implements CardUserRegisterRpc {

	@Autowired
	private CardUserRegisterService cardUserRegisterService;


	@Override
	public List<CardUserLogDTO> findByCardReportQuery(String systemBookCode, CardReportQuery cardReportQuery, int offset, int limit) {
		return CopyUtil.toList(cardUserRegisterService.findByCardReportQuery(cardReportQuery, offset, limit), CardUserLogDTO.class);
	}



}