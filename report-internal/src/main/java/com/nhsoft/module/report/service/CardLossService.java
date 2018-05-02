package com.nhsoft.module.report.service;

import com.nhsoft.module.report.model.CardLoss;
import com.nhsoft.module.report.queryBuilder.CardReportQuery;

import java.util.Date;
import java.util.List;

/**
 * 卡挂失记录
 * @author nhsoft
 *
 */
public interface CardLossService {

	/**
	 * 查询
	 * @param cardUserNum 会员卡主键
	 * @param operateName 操作人
	 * @return
	 */
	public List<CardLoss> findByCardUserNum(Integer cardUserNum, String operateName);
	
	public List<CardLoss> findByCardReportQuery(CardReportQuery cardReportQuery, int offset, int limit);
	
	public int countByCardReportQuery(CardReportQuery cardReportQuery);

	public List<CardLoss> findToLog(Date dateFrom, Date dateTo);
}
