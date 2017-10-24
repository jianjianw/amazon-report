package com.nhsoft.module.report.service.impl;

import com.nhsoft.module.report.dao.TransferInOrderDao;
import com.nhsoft.module.report.service.TransferInOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class TransferInOrderServiceImpl implements TransferInOrderService {
	private static final Logger logger = LoggerFactory.getLogger(TransferInOrderServiceImpl.class);
	@Autowired
	private TransferInOrderDao transferInOrderDao;

	@Override
	public List<Object[]> findProfitGroupByItem(String systemBookCode, List<Integer> transferBranchNums,
												List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> categoryCodeList, List<Integer> itemNums) {
		return transferInOrderDao.findProfitGroupByItem(systemBookCode, transferBranchNums, branchNums, dateFrom,
				dateTo, categoryCodeList, itemNums);
	}

	@Override
	public List<Object[]> findMoneyByBranch(String systemBookCode, List<Integer> centerBranchNums, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return transferInOrderDao.findMoneyByBranch(systemBookCode, centerBranchNums, branchNums, dateFrom, dateTo);
	}
	@Override
	public List<Object[]> findMoneyByBizday(String systemBookCode, List<Integer> centerBranchNums, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return transferInOrderDao.findMoneyByBizday(systemBookCode, centerBranchNums, branchNums, dateFrom, dateTo);
	}
}