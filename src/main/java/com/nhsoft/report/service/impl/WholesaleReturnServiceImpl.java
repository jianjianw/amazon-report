package com.nhsoft.report.service.impl;

import com.nhsoft.report.dao.WholesaleReturnDao;
import com.nhsoft.report.service.WholesaleReturnService;

import java.util.*;

public class WholesaleReturnServiceImpl implements WholesaleReturnService {

	private WholesaleReturnDao wholesaleReturnDao;

	@Override
	public List<Object[]> findItemSum(String systemBookCode, Integer branchNum, List<String> cleintFids, Date dateFrom,
									  Date dateTo, List<Integer> itemNums, List<String> categoryCodes) {
		return wholesaleReturnDao.findItemSum(systemBookCode, branchNum, cleintFids, dateFrom, dateTo, itemNums,
				categoryCodes, null);
	}



	@Override
	public List<Object[]> findSupplierSum(String systemBookCode, List<Integer> branchNums, List<String> clientFids, Date dateFrom, Date dateTo, List<Integer> itemNums, List<String> categoryCodes, List<Integer> regionNums) {
		return wholesaleReturnDao.findSupplierSum(systemBookCode, branchNums, clientFids, dateFrom, dateTo, itemNums, categoryCodes, regionNums);
	}


	@Override
	public List<Object[]> findItemSupplierSum(String systemBookCode, List<Integer> branchNums, List<String> clientFids, Date dateFrom, Date dateTo, List<Integer> itemNums, List<String> categoryCodes, List<Integer> regionNums) {
		return wholesaleReturnDao.findItemSupplierSum(systemBookCode, branchNums, clientFids, dateFrom, dateTo, itemNums, categoryCodes, regionNums);
	}


	@Override
	public List<Object[]> findBranchItemSupplierSum(String systemBookCode, List<Integer> branchNums, List<String> clientFids, Date dateFrom, Date dateTo, List<Integer> itemNums, List<String> categoryCodes, List<Integer> regionNums) {
		return wholesaleReturnDao.findBranchItemSupplierSum(systemBookCode, branchNums, clientFids, dateFrom, dateTo, itemNums, categoryCodes, regionNums);
	}


	@Override
	public List<Object[]> findItemSupplierDetail(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return wholesaleReturnDao.findItemSupplierDetail(systemBookCode, branchNums, dateFrom, dateTo);
	}


}