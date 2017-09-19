package com.nhsoft.report.service.impl;


import com.nhsoft.report.dao.ReceiveOrderDao;
import com.nhsoft.report.service.ReceiveOrderService;
import com.nhsoft.report.util.BaseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class ReceiveOrderServiceImpl extends BaseManager implements ReceiveOrderService {

	private static final Logger logger = LoggerFactory.getLogger(ReceiveOrderServiceImpl.class);
	private ReceiveOrderDao receiveOrderDao;

	public void setReceiveOrderDao(ReceiveOrderDao receiveOrderDao) {
		this.receiveOrderDao = receiveOrderDao;
	}

	@Override
	public List<Object[]> findDetailBySupplierNum(String systemBookCode, List<Integer> branchNums, Integer supplierNum,
												  Date dateFrom, Date dateTo, List<Integer> selectItemNums) {
		return receiveOrderDao.findDetailBySupplierNum(systemBookCode, branchNums, supplierNum, dateFrom, dateTo,
				selectItemNums);
	}

	@Override
	public List<Object[]> findBranchItemSupplierAmountAndMoney(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> categoryCodes, List<Integer> itemNums) {
		return receiveOrderDao.findBranchItemSupplierAmountAndMoney(systemBookCode, branchNums, dateFrom, dateTo, categoryCodes, itemNums);
	}


	@Override
	public List<Object[]> findItemSupplierAmountAndMoney(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> categoryCodes, List<Integer> itemNums) {
		return receiveOrderDao.findItemSupplierAmountAndMoney(systemBookCode, branchNums, dateFrom, dateTo, categoryCodes, itemNums);
	}

	@Override
	public List<Object[]> findSupplierAmountAndMoney(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> categoryCodes, List<Integer> itemNums) {
		return receiveOrderDao.findSupplierAmountAndMoney(systemBookCode, branchNums, dateFrom, dateTo, categoryCodes, itemNums);
	}


	@Override
	public List<Object[]> findBizSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return receiveOrderDao.findBizSummary(systemBookCode, branchNums, dateFrom, dateTo);
	}

	@Override
	public List<Object[]> findBranchSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom,
											Date dateTo, List<Integer> itemNums) {
		return receiveOrderDao.findBranchSummary(systemBookCode, branchNums, dateFrom, dateTo, itemNums);
	}

	@Override
	public List<Object[]> findBranchItemSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom,
												Date dateTo, List<Integer> itemNums) {
		return receiveOrderDao.findBranchItemSummary(systemBookCode, branchNums, dateFrom, dateTo, itemNums);
	}

	@Override
	public List<Object[]> findItemSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<Integer> itemNums) {

		return receiveOrderDao.findItemSummary(systemBookCode, branchNums, dateFrom, dateTo, itemNums);
	}


}