package com.nhsoft.module.report.rpc;

import com.nhsoft.module.report.dto.CardUserLogDTO;
import com.nhsoft.module.report.queryBuilder.CardReportQuery;

import java.util.List;


public interface CardUserRegisterRpc {


	/**
	 * 拼装成CardUserLog对象
	 * @param cardReportQuery
	 * @param limit 
	 * @param offset 
	 * @return
	 */
	public List<CardUserLogDTO> findByCardReportQuery(String systemBookCode, CardReportQuery cardReportQuery, int offset, int limit);



}
