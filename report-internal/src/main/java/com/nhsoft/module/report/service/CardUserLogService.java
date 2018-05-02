package com.nhsoft.module.report.service;

import com.nhsoft.module.report.model.CardUserLog;
import com.nhsoft.module.report.queryBuilder.CardReportQuery;

import java.util.List;

/**
 * 会员卡特殊操作日志
 * @author nhsoft
 *
 */
public interface CardUserLogService {

	
	public List<CardUserLog> findByCardReportQuery(CardReportQuery cardReportQuery, int offset, int limit);
	
	public int countByCardReportQuery(CardReportQuery cardReportQuery, int offset, int limit);
	


}
