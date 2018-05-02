package com.nhsoft.module.report.rpc.impl;


import com.nhsoft.module.report.dto.RelatCardDTO;
import com.nhsoft.module.report.dto.ShiftTableDTO;
import com.nhsoft.module.report.model.ShiftTable;
import com.nhsoft.module.report.queryBuilder.CardReportQuery;
import com.nhsoft.module.report.rpc.RelatCardRpc;
import com.nhsoft.module.report.service.RelatCardService;
import com.nhsoft.report.utils.CopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RelatCardRpcImpl implements RelatCardRpc {

	@Autowired
	private RelatCardService relatCardService;

	@Override
	public List<RelatCardDTO> findByCardUserNum(String systemBookCode, Integer cardUserNum) {
		return CopyUtil.toList(relatCardService.findByCardUserNum(cardUserNum), RelatCardDTO.class);
	}

	@Override
	public List<RelatCardDTO> findByShiftTables(String systemBookCode, List<ShiftTableDTO> shiftTables) {
		return CopyUtil.toList(relatCardService.findByShiftTables(CopyUtil.toList(shiftTables, ShiftTable.class)), RelatCardDTO.class);
	}

	@Override
	public List<RelatCardDTO> findByCardReportQuery(String systemBookCode, CardReportQuery cardReportQuery, int offset, int limit) {
		return CopyUtil.toList(relatCardService.findByCardReportQuery(cardReportQuery, offset, limit), RelatCardDTO.class);
	}

	@Override
	public int countByCardReportQuery(String systemBookCode, CardReportQuery cardReportQuery) {
		return relatCardService.countByCardReportQuery(cardReportQuery);
	}

	@Override
	public Object[] sumByByShiftTables(String systemBookCode, List<ShiftTableDTO> shiftTables) {
		return relatCardService.sumByByShiftTables(CopyUtil.toList(shiftTables, ShiftTable.class));
	}

	@Override
	public List<Object[]> findPaymentTypeByShiftTables(String systemBookCode, List<ShiftTableDTO> shiftTables) {
		return relatCardService.findPaymentTypeByShiftTables(CopyUtil.toList(shiftTables, ShiftTable.class));
	}


}