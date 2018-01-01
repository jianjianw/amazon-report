package com.nhsoft.module.report.service.impl;


import com.nhsoft.module.report.dao.AdjustmentOrderDao;
import com.nhsoft.module.report.service.AdjustmentOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
@Service
public class AdjustmentOrderServiceImpl implements AdjustmentOrderService {
	@Autowired
	private AdjustmentOrderDao adjustmentOrderDao;

	@Override
	public List<Object[]> findBranchItemSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<Integer> itemNums, List<String> reasons) {
		return adjustmentOrderDao.findBranchItemSummary(systemBookCode, branchNums, dateFrom, dateTo, itemNums, reasons);
	}

	@Override
	public List<Object[]> findLossMoneyByBranch(String systemBookCode,List<Integer> branchNums,Date dateFrom, Date dateTo) {
		return adjustmentOrderDao.findLossMoneyByBranch(systemBookCode,branchNums,dateFrom,dateTo);
	}

	@Override
	public List<Object[]> findCheckMoneyByBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return adjustmentOrderDao.findCheckMoneyByBranch(systemBookCode,branchNums,dateFrom,dateTo);
	}



	@Override
	@Cacheable(value = "serviceCache")
	public List<Object[]> findAdjustmentCauseMoneyByBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return adjustmentOrderDao.findAdjustmentCauseMoneyByBranch(systemBookCode,branchNums,dateFrom,dateTo);
	}

	@Override
	public List<Object[]> findItemLoss(String systemBookCode, Date dateFrom, Date dateTo) {
		return adjustmentOrderDao.findItemLoss(systemBookCode,dateFrom,dateTo);
	}
}