package com.nhsoft.module.report.rpc.impl;

import com.nhsoft.module.report.dto.ReplaceCardDTO;
import com.nhsoft.module.report.dto.ShiftTableDTO;
import com.nhsoft.module.report.model.ShiftTable;
import com.nhsoft.module.report.queryBuilder.CardReportQuery;
import com.nhsoft.module.report.rpc.ReplaceCardRpc;
import com.nhsoft.module.report.service.ReplaceCardService;
import com.nhsoft.report.utils.CopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class ReplaceCardRpcImpl implements ReplaceCardRpc {

	@Autowired
	private ReplaceCardService replaceCardService;

	@Override
	public List<ReplaceCardDTO> findByCardUserNum(String systemBookCode, Integer cardUserNum) {
		return CopyUtil.toList(replaceCardService.findByCardUserNum(cardUserNum), ReplaceCardDTO.class);
	}

	@Override
	public List<ReplaceCardDTO> findByCardReportQuery(String systemBookCode, CardReportQuery cardReportQuery, int offset, int limit) {
		return CopyUtil.toList(replaceCardService.findByCardReportQuery(cardReportQuery, offset, limit), ReplaceCardDTO.class);
	}

	@Override
	public int countByCardReportQuery(String systemBookCode, CardReportQuery cardReportQuery) {
		return replaceCardService.countByCardReportQuery(cardReportQuery);
	}

	@Override
	public int count(String systemBookCode, Integer branchNum, String shiftTableBizday, Integer shiftTableNum) {
		return replaceCardService.count(systemBookCode, branchNum, shiftTableBizday, shiftTableNum);
	}

	@Override
	public BigDecimal sumByByShiftTables(String systemBookCode, List<ShiftTableDTO> shiftTables) {
		return replaceCardService.sumByByShiftTables(CopyUtil.toList(shiftTables, ShiftTable.class));
	}

	@Override
	public List<Object[]> findPaymentTypeByShiftTables(String systemBookCode, List<ShiftTableDTO> shiftTables) {
		return replaceCardService.findPaymentTypeByShiftTables(CopyUtil.toList(shiftTables, ShiftTable.class));
	}

	@Override
	public ReplaceCardDTO getMaxPK(String systemBookCode, Integer branchNum, String shiftTableBizday, String replaceCardMachine) {
		return CopyUtil.to(replaceCardService.getMaxPK(systemBookCode, branchNum, shiftTableBizday, replaceCardMachine), ReplaceCardDTO.class);
	}

}