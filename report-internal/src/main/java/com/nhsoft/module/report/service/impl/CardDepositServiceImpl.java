package com.nhsoft.module.report.service.impl;

import com.nhsoft.module.report.dao.CardDepositDao;
import com.nhsoft.module.report.service.CardDepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by yangqin on 2017/9/19.
 */
@Service
public class CardDepositServiceImpl implements CardDepositService {
	
	@Autowired
	private CardDepositDao cardDepositDao;
	
	
	@Override
	public List<Object[]> findBranchSum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return cardDepositDao.findBranchSum(systemBookCode, branchNums, dateFrom, dateTo, null);
	}
	
	@Override
	public List<Object[]> findSumByBizday(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return cardDepositDao.findSumByBizday(systemBookCode, branchNums, dateFrom, dateTo);
	}

}
