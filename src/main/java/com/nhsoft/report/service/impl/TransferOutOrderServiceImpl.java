package com.nhsoft.report.service.impl;

import com.nhsoft.report.dao.TransferOutOrderDao;
import com.nhsoft.report.service.TransferOutOrderService;
import com.nhsoft.report.util.BaseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class TransferOutOrderServiceImpl extends BaseManager implements TransferOutOrderService {



	private static Logger logger = LoggerFactory.getLogger(TransferOutOrderServiceImpl.class);
	@Autowired
	private TransferOutOrderDao transferOutOrderDao;

	@Override
	public List<Object[]> findMoneyByBizday(String systemBookCode, List<Integer> centerBranchNums, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return transferOutOrderDao.findMoneyByBizday(systemBookCode, centerBranchNums, branchNums, dateFrom, dateTo);
	}

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

	@Override
	public List<Object[]> findUnTransferedItems(String systemBookCode, Integer outBranchNum, List<Integer> branchNums, List<Integer> storehouseNums) {
		return transferOutOrderDao.findUnTransferedItems(systemBookCode, outBranchNum, branchNums, storehouseNums);
	}

	@Override
	public List<Object[]> findMoneyByBranch(String systemBookCode, List<Integer> centerBranchNums, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return transferOutOrderDao.findMoneyByBranch(systemBookCode, centerBranchNums, branchNums, dateFrom, dateTo);
	}
}