package com.nhsoft.module.report.service.impl;

import com.nhsoft.module.report.dao.BranchTransferGoalsDao;
import com.nhsoft.module.report.service.BranchTransferGoalsService;
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

	@Override
	public List<Object[]> findSaleMoneyGoalsByBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo,String dateType) {
		return branchTransferGoalsDao.findSaleMoneyGoalsByBranch(systemBookCode,branchNums,dateFrom,dateTo,dateType);
	}

	@Override
	public List<Object[]> findSaleMoneyGoalsByDate(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String dateType) {
		return branchTransferGoalsDao.findSaleMoneyGoalsByDate(systemBookCode,branchNums,dateFrom,dateTo,dateType);
	}

	@Override
	public List<Object[]> findGoalsByBranchBizday(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return branchTransferGoalsDao.findGoalsByBranchBizday(systemBookCode,branchNums,dateFrom,dateTo);
	}

	@Override
	public List<Object[]> findDepositGoalsByBizdayBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return branchTransferGoalsDao.findDepositGoalsByBizdayBranch(systemBookCode,branchNums,dateFrom,dateTo);
	}

	@Override
	public List<Object[]> findNewCardGoalsByBizdayBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return branchTransferGoalsDao.findNewCardGoalsByBizdayBranch(systemBookCode,branchNums,dateFrom,dateTo);
	}
}
