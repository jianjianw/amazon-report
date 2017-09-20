package com.nhsoft.report.service.impl;

import com.nhsoft.report.dao.CardUserDao;
import com.nhsoft.report.service.CardUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by yangqin on 2017/9/19.
 */
@Service
public class CardUserServiceImpl implements CardUserService{
	
	@Autowired
	private CardUserDao cardUserDao;
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
}
