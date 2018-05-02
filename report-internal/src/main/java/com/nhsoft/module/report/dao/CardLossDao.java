package com.nhsoft.module.report.dao;

import com.nhsoft.module.report.model.CardLoss;
import com.nhsoft.module.report.queryBuilder.CardReportQuery;

import java.util.Date;
import java.util.List;

public interface CardLossDao {

	public List<CardLoss> findByCardUserNum(Integer cardUserNum, String operateName);
	
	public List<CardLoss> findByCardReportQuery(CardReportQuery cardReportQuery, int offset, int limit);
	
	public int countByCardReportQuery(CardReportQuery cardReportQuery);

	public List<CardLoss> findToLog(Date dateFrom, Date dateTo);
}
