package com.nhsoft.report.service.impl;

import com.nhsoft.report.dao.WholesaleOrderDao;
import com.nhsoft.report.service.WholesaleOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.*;

public class WholesaleOrderServiceImpl implements WholesaleOrderService {

	private static final Logger logger = LoggerFactory.getLogger(WholesaleOrderServiceImpl.class);

	private WholesaleOrderDao wholesaleOrderDao;

	@Override
	public List<Object[]> findItemSum(String systemBookCode, Integer branchNum, List<String> clientFids, Date dateFrom,
									  Date dateTo, List<Integer> itemNums, List<String> categoryCodes, List<Integer> regionNums) {
		return wholesaleOrderDao.findItemSum(systemBookCode, branchNum, clientFids, dateFrom, dateTo, itemNums,
				categoryCodes, regionNums);
	}


	@Override
	public List<Object[]> findSupplierSum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo,
										  List<String> categoryCodes, List<Integer> itemNums, List<Integer> regionNums) {
		return wholesaleOrderDao.findSupplierSum(systemBookCode, branchNums, dateFrom, dateTo, categoryCodes, itemNums,
				regionNums);
	}


	@Override
	public List<Object[]> findItemSupplierSum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo,
											  List<Integer> itemNums, List<Integer> regionNums) {
		return wholesaleOrderDao.findItemSupplierSum(systemBookCode, branchNums, dateFrom, dateTo, itemNums, regionNums);
	}


	@Override
	public List<Object[]> findBranchItemSupplierSum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<Integer> itemNums, List<Integer> regionNums) {
		return wholesaleOrderDao.findBranchItemSupplierSum(systemBookCode, branchNums, dateFrom, dateTo, itemNums, regionNums);
	}

	@Override
	public List<Object[]> findItemSupplierDetail(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo,
												 List<Integer> regionNums) {
		return wholesaleOrderDao.findItemSupplierDetail(systemBookCode, branchNums, dateFrom, dateTo, regionNums);
	}


}