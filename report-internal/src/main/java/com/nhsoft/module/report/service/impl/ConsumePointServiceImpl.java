package com.nhsoft.module.report.service.impl;

import com.google.gson.Gson;
import com.nhsoft.module.report.dao.ConsumePointDao;
import com.nhsoft.module.report.model.ConsumePoint;
import com.nhsoft.module.report.queryBuilder.CardReportQuery;
import com.nhsoft.module.report.service.ConsumePointService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ConsumePointServiceImpl implements ConsumePointService {
	private static Logger logger = LoggerFactory.getLogger(ConsumePointServiceImpl.class);

	@Autowired
	private ConsumePointDao consumePointDao;

	@Override
	public List<ConsumePoint> findByCardReportQuery(CardReportQuery cardReportQuery, int offset, int limit) {
		return consumePointDao.findByCardReportQuery(cardReportQuery, offset, limit);
	}

	@Override
	public Object[] sumByCardReportQuery(CardReportQuery cardReportQuery) {
		return consumePointDao.sumByCardReportQuery(cardReportQuery);
	}


}
