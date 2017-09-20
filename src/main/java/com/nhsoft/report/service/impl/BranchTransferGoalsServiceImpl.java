package com.nhsoft.report.service.impl;

import com.nhsoft.report.dao.BranchTransferGoalsDao;
import com.nhsoft.report.service.BranchTransferGoalsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by yangqin on 2017/9/20.
 */
@Service
public class BranchTransferGoalsServiceImpl implements BranchTransferGoalsService {
	
	@Autowired
	private BranchTransferGoalsDao branchTransferGoalsDao;
	
	
	@Override
	public List<Object[]> findSummaryByDate(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String dateType) {
		
		
		return branchTransferGoalsDao.findSummaryByDate(systemBookCode, branchNums, dateFrom, dateTo, dateType);
	}
}
