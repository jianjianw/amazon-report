package com.nhsoft.module.report.service.impl;

import com.nhsoft.module.report.dao.CardLossDao;
import com.nhsoft.module.report.dao.CardUserDao;
import com.nhsoft.module.report.model.CardLoss;
import com.nhsoft.module.report.model.CardUser;
import com.nhsoft.module.report.queryBuilder.CardReportQuery;
import com.nhsoft.module.report.service.CardLossService;
import com.nhsoft.module.report.util.AppConstants;
import com.nhsoft.report.utils.ServiceBizException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class CardLossServiceImpl implements CardLossService {
	
	@Autowired
	private CardLossDao cardLossDao;

	@Override
	public List<CardLoss> findByCardUserNum(Integer cardUserNum,
			String operateName) {
		return cardLossDao.findByCardUserNum(cardUserNum, operateName);
	}

	@Override
	public List<CardLoss> findByCardReportQuery(
			CardReportQuery cardReportQuery, int offset, int limit) {
		return cardLossDao.findByCardReportQuery(cardReportQuery, offset, limit);
	}

	@Override
	public int countByCardReportQuery(CardReportQuery cardReportQuery) {
		return cardLossDao.countByCardReportQuery(cardReportQuery);
	}

	@Override
	public List<CardLoss> findToLog(Date dateFrom, Date dateTo) {
		return cardLossDao.findToLog(dateFrom, dateTo);
	}
	
	

}
