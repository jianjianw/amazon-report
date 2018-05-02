package com.nhsoft.module.report.rpc.impl;
import com.nhsoft.module.report.dto.CardLossDTO;
import com.nhsoft.module.report.queryBuilder.CardReportQuery;
import com.nhsoft.module.report.rpc.CardLossRpc;
import com.nhsoft.module.report.service.CardLossService;
import com.nhsoft.report.utils.CopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CardLossRpcImpl implements CardLossRpc {

	@Autowired
	private CardLossService cardLossService;

	@Override
	public List<CardLossDTO> findByCardUserNum(String systemBookCode, Integer cardUserNum, String operateName) {
		return CopyUtil.toList(cardLossService.findByCardUserNum(cardUserNum, operateName), CardLossDTO.class);
	}

	@Override
	public List<CardLossDTO> findByCardReportQuery(String systemBookCode, CardReportQuery cardReportQuery, int offset, int limit) {
		return CopyUtil.toList(cardLossService.findByCardReportQuery(cardReportQuery, offset, limit), CardLossDTO.class);
	}

	@Override
	public int countByCardReportQuery(String systemBookCode, CardReportQuery cardReportQuery) {
		return cardLossService.countByCardReportQuery(cardReportQuery);
	}
}