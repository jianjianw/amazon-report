package com.nhsoft.module.report.service;


import com.nhsoft.module.report.model.ConsumePoint;
import com.nhsoft.module.report.queryBuilder.CardReportQuery;

import java.util.Date;
import java.util.List;

/**
 * 积分兑换记录
 * @author nhsoft
 *
 */
public interface ConsumePointService {

	public List<ConsumePoint> findByCardReportQuery(CardReportQuery cardReportQuery, int offset, int limit);
	
	public Object[] sumByCardReportQuery(CardReportQuery cardReportQuery);

	
}
