package com.nhsoft.module.report.service.impl;

import com.nhsoft.module.report.dao.CardUserDao;
import com.nhsoft.module.report.model.CardUser;
import com.nhsoft.module.report.queryBuilder.CardReportQuery;
import com.nhsoft.module.report.service.CardUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by yangqin on 2017/9/19.
 */
@Service
public class CardUserServiceImpl implements CardUserService {
	
	@Autowired
	private CardUserDao cardUserDao;

	@Override
	public List<Object[]> findCardCountByBizday(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return cardUserDao.findCardCountByBizday(systemBookCode, branchNums, dateFrom, dateTo, null);
	}
	@Override
	public List<Object[]> findCardCount(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer cardUserCardType) {
		return cardUserDao.findCardCount(systemBookCode, branchNums, dateFrom, dateTo, cardUserCardType);
	}
	
	@Override
	public List<Object[]> findRevokeCardCount(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer cardUserCardType) {
		return cardUserDao.findRevokeCardCount(systemBookCode, branchNums, dateFrom, dateTo, cardUserCardType);
	}
	
	@Override
	public List<Object[]> findRevokeCardCountByBizday(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer cardUserCardType) {
		return cardUserDao.findRevokeCardCountByBizday(systemBookCode, branchNums, dateFrom, dateTo, cardUserCardType);
	}

	@Override
	public List<Object[]> findCardUserCountByBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return cardUserDao.findCardUserCountByBranch(systemBookCode, branchNums, dateFrom, dateTo);
	}

	@Override
	public List<Object[]> findCardCountByBranchBizday(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer cardUserCardType) {
		return cardUserDao.findCardCountByBranchBizday(systemBookCode,branchNums,dateFrom,dateTo,cardUserCardType);
	}

	@Override
	public List<Object[]> findRevokeCardCountByBranchBizday(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, Integer cardUserCardType) {
		return cardUserDao.findRevokeCardCountByBranchBizday(systemBookCode,branchNums,dateFrom,dateTo,cardUserCardType);
	}

	@Override
	public List<CardUser> findByCardReportQuery(CardReportQuery cardReportQuery, int offset, int limit) {
		return cardUserDao.findByCardReportQuery(cardReportQuery,offset,limit);
	}
}
