package com.nhsoft.module.report.service;


import com.nhsoft.module.report.model.CardUserLog;
import com.nhsoft.module.report.queryBuilder.CardReportQuery;

import java.util.List;

/**
 * 会员注册
 * @author nhsoft
 *
 */
public interface CardUserRegisterService {


	
	/**
	 * 拼装成CardUserLog对象
	 * @param cardReportQuery
	 * @param limit 
	 * @param offset 
	 * @return
	 */
	public List<CardUserLog> findByCardReportQuery(CardReportQuery cardReportQuery, int offset, int limit);

}
