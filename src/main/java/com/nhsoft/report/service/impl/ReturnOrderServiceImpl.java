package com.nhsoft.report.service.impl;

import com.nhsoft.report.dao.ReturnOrderDao;
import com.nhsoft.report.service.ReturnOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

public class ReturnOrderServiceImpl implements ReturnOrderService {

	private static final Logger logger = LoggerFactory.getLogger(ReturnOrderServiceImpl.class);
	private ReturnOrderDao returnOrderDao;

	public ReturnOrderDao getReturnOrderDao() {
		return returnOrderDao;
	}


	@Override
	public List<Object[]> findSupplierAmountAndMoney(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> categoryCodes, List<Integer> itemNums) {
		return returnOrderDao.findSupplierAmountAndMoney(systemBookCode, branchNums, dateFrom, dateTo, categoryCodes, itemNums);
	}


	@Override
	public List<Object[]> findItemSupplierAmountAndMoney(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> categoryCodes, List<Integer> itemNums) {
		return returnOrderDao.findItemSupplierAmountAndMoney(systemBookCode, branchNums, dateFrom, dateTo, categoryCodes, itemNums);
	}

	@Override
	public List<Object[]> findBranchItemSupplierAmountAndMoney(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> categoryCodes, List<Integer> itemNums) {
		return returnOrderDao.findBranchItemSupplierAmountAndMoney(systemBookCode, branchNums, dateFrom, dateTo, categoryCodes, itemNums);
	}


	@Override
	public List<Object[]> findDetailBySupplierNum(String systemBookCode,
												  List<Integer> branchNums, Integer supplierNum, Date dateFrom, Date dateTo,
												  List<Integer> selectItemNums) {
		return returnOrderDao.findDetailBySupplierNum(systemBookCode, branchNums, supplierNum, dateFrom, dateTo, selectItemNums);
	}


}