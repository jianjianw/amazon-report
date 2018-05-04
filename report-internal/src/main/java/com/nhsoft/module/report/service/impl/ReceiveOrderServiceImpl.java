package com.nhsoft.module.report.service.impl;


import com.nhsoft.module.report.dao.ReceiveOrderDao;
import com.nhsoft.module.report.service.ReceiveOrderService;
import com.nhsoft.report.utils.BaseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class ReceiveOrderServiceImpl extends BaseManager implements ReceiveOrderService {

	private static final Logger logger = LoggerFactory.getLogger(ReceiveOrderServiceImpl.class);
	@Autowired
	private ReceiveOrderDao receiveOrderDao;


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

	@Override
	public List<Object[]> findPurchaseMonth(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo,String dateType,String strDate) {
		return receiveOrderDao.findPurchaseMonth(systemBookCode,branchNum,dateFrom,dateTo,dateType,strDate);
	}

	@Override
	public List<Object[]> findPurchaseByBiz(String systemBookCode,Date dateFrom, Date dateTo,List<Integer> itemNums) {
		return receiveOrderDao.findPurchaseByBiz(systemBookCode,dateFrom,dateTo,itemNums);
	}

	@Override
	public List<Object[]> findItemMatrixMaxProducingDates(String systemBookCode, Integer branchNum, List<Integer> itemNums) {
		return receiveOrderDao.findItemMatrixMaxProducingDates(systemBookCode,branchNum,itemNums);
	}


}