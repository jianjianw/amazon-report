package com.nhsoft.report.service.impl;

import com.nhsoft.report.dao.AdjustmentOrderDao;
import com.nhsoft.report.service.AdjustmentOrderService;

import java.util.*;

public class AdjustmentOrderServiceImpl implements AdjustmentOrderService {

	private AdjustmentOrderDao adjustmentOrderDao;

	@Override
	public List<Object[]> findBranchItemSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<Integer> itemNums, List<String> reasons) {
		return adjustmentOrderDao.findBranchItemSummary(systemBookCode, branchNums, dateFrom, dateTo, itemNums, reasons);
	}
}