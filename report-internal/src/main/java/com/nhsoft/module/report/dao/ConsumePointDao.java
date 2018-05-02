package com.nhsoft.module.report.dao;

import com.nhsoft.module.report.model.ConsumePoint;
import com.nhsoft.module.report.queryBuilder.CardReportQuery;

import java.util.List;

public interface ConsumePointDao {

	
	public List<ConsumePoint> findByCardReportQuery(CardReportQuery cardReportQuery, int offset, int limit);

	public Object[] sumByCardReportQuery(CardReportQuery cardReportQuery);
	

}
