package com.nhsoft.module.report.service.impl;

import com.nhsoft.module.report.dao.CardUserDao;
import com.nhsoft.module.report.dao.ReplaceCardDao;
import com.nhsoft.module.report.model.CardUser;
import com.nhsoft.module.report.model.ReplaceCard;
import com.nhsoft.module.report.model.ShiftTable;
import com.nhsoft.module.report.queryBuilder.CardReportQuery;
import com.nhsoft.module.report.service.ReplaceCardService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class ReplaceCardServiceImpl implements ReplaceCardService {
	
	@Autowired
	private ReplaceCardDao replaceCardDao;

	@Override
	public List<ReplaceCard> findByCardUserNum(Integer cardUserNum) {
		return replaceCardDao.findByCardUserNum(cardUserNum);
	}

	@Override
	public List<ReplaceCard> findByCardReportQuery(
			CardReportQuery cardReportQuery, int offset, int limit) {
		return replaceCardDao.findByCardReportQuery(cardReportQuery, offset, limit);
	}

	@Override
	public int countByCardReportQuery(CardReportQuery cardReportQuery) {
		return replaceCardDao.countByCardReportQuery(cardReportQuery);
	}

	@Override
	public int count(String systemBookCode, Integer branchNum, String shiftTableBizday, Integer shiftTableNum) {
		return replaceCardDao.count(systemBookCode, branchNum, shiftTableBizday, shiftTableNum);
	}

	@Override
	public BigDecimal sumByByShiftTables(List<ShiftTable> shiftTables) {
		return replaceCardDao.sumByByShiftTables(shiftTables);
	}

	@Override
	public List<Object[]> findPaymentTypeByShiftTables(List<ShiftTable> shiftTables) {
		return replaceCardDao.findPaymentTypeByShiftTables(shiftTables);
	}

	@Override
	public ReplaceCard getMaxPK(String systemBookCode, Integer branchNum, String shiftTableBizday, String replaceCardMachine) {
		return replaceCardDao.getMaxPK(systemBookCode, branchNum, shiftTableBizday, replaceCardMachine);
	}

	@Override
	public void deleteByBook(String systemBookCode) {
		replaceCardDao.deleteByBook(systemBookCode);
	}

	@Override
	public List<ReplaceCard> findToLog(Date dateFrom, Date dateTo) {
		return replaceCardDao.findToLog(dateFrom, dateTo);
	}
	

}
