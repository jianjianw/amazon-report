package com.nhsoft.report.service.impl;

import com.nhsoft.report.dao.TransferOutOrderDao;
import com.nhsoft.report.service.TransferOutOrderService;
import com.nhsoft.report.util.BaseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class TransferOutOrderServiceImpl extends BaseManager implements TransferOutOrderService {

	private static Logger logger = LoggerFactory.getLogger(TransferOutOrderServiceImpl.class);
	private TransferOutOrderDao transferOutOrderDao;

	@Override
	public List<Object[]> findProfitGroupByItem(String systemBookCode, List<Integer> transferBranchNums,
												List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> categoryLists, List<Integer> itemNums) {
		return transferOutOrderDao.findProfitGroupByItem(systemBookCode, transferBranchNums, branchNums, dateFrom,
				dateTo, categoryLists, itemNums);
	}


	@Override
	public List<Object[]> findBranchItemMatrixSummary(String systemBookCode, Integer centerBranchNum, List<Integer> branchNums, Date dateFrom, Date dateTo, List<Integer> itemNums) {
		return transferOutOrderDao.findBranchItemMatrixSummary(systemBookCode, centerBranchNum, branchNums, dateFrom, dateTo, itemNums);
	}

}