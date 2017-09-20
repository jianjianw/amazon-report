package com.nhsoft.report.service.impl;

import com.nhsoft.report.dao.TransferInOrderDao;
import com.nhsoft.report.service.TransferInOrderService;
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


}