package com.nhsoft.report.service.impl;

import com.nhsoft.report.dao.CardConsumeDao;
import com.nhsoft.report.service.CardConsumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by yangqin on 2017/9/19.
 */
@Service
public class CardConsumeServiceImpl implements CardConsumeService {
	@Autowired
	private CardConsumeDao cardConsumeDao;
	
	
	@Override
	public List<Object[]> findBranchSum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return cardConsumeDao.findBranchSum(systemBookCode, branchNums, dateFrom, dateTo, null);
	}
	
	@Override
	public List<Object[]> findSumByBizday(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return cardConsumeDao.findSumByBizday(systemBookCode, branchNums, dateFrom, dateTo);
	}
}
