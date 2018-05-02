package com.nhsoft.module.report.rpc;

import com.nhsoft.module.report.dto.CardUserLogDTO;
import com.nhsoft.module.report.queryBuilder.CardReportQuery;

import java.util.List;

public interface CardUserLogRpc {

	public List<CardUserLogDTO> findByCardReportQuery(String systemBookCode, CardReportQuery cardReportQuery, int offset, int limit);

	public int countByCardReportQuery(String systemBookCode, CardReportQuery cardReportQuery, int offset, int limit);



}
