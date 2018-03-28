package com.nhsoft.module.report.service.impl;

import com.nhsoft.module.report.dao.ReturnOrderDao;
import com.nhsoft.module.report.service.ReturnOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class ReturnOrderServiceImpl implements ReturnOrderService {

	private static final Logger logger = LoggerFactory.getLogger(ReturnOrderServiceImpl.class);
	@Autowired
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

	@Override
	public List<Object[]> findReturnMonth(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo,String strDate) {
		return returnOrderDao.findReturnMonth(systemBookCode,branchNum,dateFrom,dateTo,strDate);
	}


}