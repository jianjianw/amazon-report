package com.nhsoft.module.report.service.impl;

import com.nhsoft.module.report.dao.CardUserDao;
import com.nhsoft.module.report.dao.CardUserLogDao;
import com.nhsoft.module.report.model.CardUserLog;
import com.nhsoft.module.report.queryBuilder.CardReportQuery;
import com.nhsoft.module.report.service.BranchService;
import com.nhsoft.module.report.service.CardUserLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardUserLogServiceImpl implements CardUserLogService {
	
	@Autowired
	private CardUserLogDao cardUserLogDao;
	@Override
	public List<CardUserLog> findByCardReportQuery(CardReportQuery cardReportQuery, int offset, int limit) {
		return cardUserLogDao.findByCardReportQuery(cardReportQuery, offset, limit);
	}

	@Override
	public int countByCardReportQuery(CardReportQuery cardReportQuery, int offset, int limit) {
		return cardUserLogDao.countByCardReportQuery(cardReportQuery, offset, limit);
	}

}
