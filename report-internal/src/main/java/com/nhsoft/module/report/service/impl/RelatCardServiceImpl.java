package com.nhsoft.module.report.service.impl;

import com.nhsoft.module.report.dao.CardUserDao;
import com.nhsoft.module.report.dao.RelatCardDao;
import com.nhsoft.module.report.model.RelatCard;
import com.nhsoft.module.report.model.ShiftTable;
import com.nhsoft.module.report.queryBuilder.CardReportQuery;
import com.nhsoft.module.report.service.RelatCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RelatCardServiceImpl implements RelatCardService {
	
	@Autowired
	private RelatCardDao relatCardDao;
	@Autowired
	private CardUserDao cardUserDao;

	@Override
	public List<RelatCard> findByCardUserNum(Integer cardUserNum) {
		return relatCardDao.findByCardUserNum(cardUserNum);
	}

	@Override
	public List<RelatCard> findByShiftTables(List<ShiftTable> shiftTables) {
		return relatCardDao.findByShiftTables(shiftTables);
	}

	@Override
	public List<RelatCard> findByCardReportQuery(
			CardReportQuery cardReportQuery, int offset, int limit) {
		return relatCardDao.findByCardReportQuery(cardReportQuery, offset, limit);
	}

	@Override
	public int countByCardReportQuery(CardReportQuery cardReportQuery) {
		return relatCardDao.countByCardReportQuery(cardReportQuery);
	}

	@Override
	public Object[] sumByByShiftTables(List<ShiftTable> shiftTables) {
		return relatCardDao.sumByByShiftTables(shiftTables);
	}

	@Override
	public List<Object[]> findPaymentTypeByShiftTables(List<ShiftTable> shiftTables) {
		return relatCardDao.findPaymentTypeByShiftTables(shiftTables);
	}


	@Override
	public List<RelatCard> findToLog(Date dateFrom, Date dateTo) {
		return relatCardDao.findToLog(dateFrom, dateTo);
	}
	
	

}
