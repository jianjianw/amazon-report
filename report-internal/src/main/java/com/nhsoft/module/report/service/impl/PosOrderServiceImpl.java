package com.nhsoft.module.report.service.impl;


import com.nhsoft.module.report.dao.PosOrderDao;
import com.nhsoft.module.report.dto.ItemQueryDTO;
import com.nhsoft.module.report.model.PosOrderDetail;
import com.nhsoft.module.report.service.PosOrderService;
import com.nhsoft.module.report.shared.queryBuilder.CardReportQuery;
import com.nhsoft.module.report.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class PosOrderServiceImpl implements PosOrderService {
	private static final Logger logger = LoggerFactory.getLogger(PosOrderServiceImpl.class);
	@Autowired
	private PosOrderDao posOrderDao;

	@Override
	public List<Object[]> findCustomReportByBizday(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return posOrderDao.findCustomReportByBizday(systemBookCode, branchNums, dateFrom, dateTo);
	}


	@Override
	public List<Object[]> findSummaryByBizday(CardReportQuery cardReportQuery) {
		return posOrderDao.findSummaryByBizday(cardReportQuery);
	}

	@Override
	public List<Object[]> findSummaryByBranch(CardReportQuery cardReportQuery) {
		return posOrderDao.findSummaryByBranch(cardReportQuery);
	}

	@Override
	public List<Object[]> findBranchDetailSum(String systemBookCode, List<Integer> branchNums, Date dateFrom,
											  Date dateTo, List<Integer> itemNums, boolean queryKit) {
		return posOrderDao.findBranchDetailSum(systemBookCode, branchNums, dateFrom, dateTo, itemNums, queryKit);
	}

	@Override
	public List<Object[]> findBranchItemSum(String systemBookCode, List<Integer> branchNums, Date dateFrom,
											Date dateTo, List<Integer> itemNums, boolean queryKit) {
		return posOrderDao.findBranchItemSummary(systemBookCode, branchNums, dateFrom, dateTo, itemNums, queryKit);
	}

	@Override
	public List<Object[]> findItemSum(ItemQueryDTO itemQueryDTO) {
		return posOrderDao.findItemSum(itemQueryDTO);
	}


	@Override
	public List<Object[]> findBranchItemSum(ItemQueryDTO itemQueryDTO) {
		return posOrderDao.findBranchItemSum(itemQueryDTO);
	}

	@Override
	public List<Object[]> findBizmonthItemSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom,
												  Date dateTo, List<Integer> itemNums) {
		return posOrderDao.findBizmonthItemSummary(systemBookCode, branchNums, dateFrom, dateTo, itemNums);
	}


	@Override
	public List<Object[]> findItemSum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return posOrderDao.findItemMatrixSum(systemBookCode, branchNums, dateFrom, dateTo, false);
	}


	@Override
	public List<Object[]> findSupplierSum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> categoryCodes, List<Integer> itemNums, boolean queryKit) {
		return posOrderDao.findSupplierSum(systemBookCode, branchNums, dateFrom, dateTo, categoryCodes, itemNums, queryKit);
	}


	@Override
	public List<Object[]> findItemSupplierMatrixSum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, boolean queryKit) {
		return posOrderDao.findItemSupplierMatrixSum(systemBookCode, branchNums, dateFrom, dateTo, queryKit);
	}


	@Override
	public List<Object[]> findItemSupplierSumByCategory(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> categoryCodes, boolean queryKit, List<Integer> itemNums) {
		return posOrderDao.findItemSupplierSumByCategory(systemBookCode, branchNums, dateFrom, dateTo, categoryCodes, queryKit, itemNums);
	}

	@Override
	public List<Object[]> findOrderDetailWithSupplier(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> categoryCodes, boolean queryKit) {
		return posOrderDao.findOrderDetailWithSupplier(systemBookCode, branchNums, dateFrom, dateTo, categoryCodes, queryKit);
	}

	@Override
	public List<Object[]> findItemSum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo,
									  List<Integer> itemNums, boolean queryKit) {
		return posOrderDao.findItemSum(systemBookCode, branchNums, dateFrom, dateTo, itemNums, queryKit);
	}

	@Override
	public List<Object[]> findCustomReportByBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String dateType) {
		return posOrderDao.findCustomReportByBranch(systemBookCode, branchNums, dateFrom, dateTo, dateType);
	}

	@Override
	@Cacheable(value = "serviceCache")
	public List<Object[]> findMoneyBranchSummary(String systemBookCode, List<Integer> branchNums, String queryBy, Date dateFrom, Date dateTo,Boolean isMember) {

		List<Object[]> objects = null;
		if(queryBy.equals(AppConstants.BUSINESS_TREND_PAYMENT)){
			objects = posOrderDao.findMoneyBranchSummary(systemBookCode, branchNums, dateFrom, dateTo, isMember);
		}
		return objects;

	}

	@Override
	@Cacheable(value = "serviceCache")
	public List<Object[]> findMoneyBizdaySummary(String systemBookCode, List<Integer> branchNums, String queryBy, Date dateFrom, Date dateTo, Boolean isMember) {

		List<Object[]> objects = null;
		if(queryBy.equals(AppConstants.BUSINESS_TREND_PAYMENT)){
			objects = posOrderDao.findMoneyBizdaySummary(systemBookCode, branchNums, dateFrom, dateTo, isMember);
		}
		return objects;
	}

	@Override
	@Cacheable(value = "serviceCache")
	public List<Object[]> findMoneyBizmonthSummary(String systemBookCode, List<Integer> branchNums, String queryBy, Date dateFrom, Date dateTo, Boolean isMember) {


		List<Object[]> objects = null;
		if (queryBy.equals(AppConstants.BUSINESS_TREND_PAYMENT)) {
			objects = posOrderDao.findMoneyBizmonthSummary(systemBookCode, branchNums, dateFrom, dateTo, isMember);
		}
		return objects;

	}
	////////////////////////////一下都是测试
	@Override
	public List<Object[]> findDetails(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo,
									  List<String> categoryCodes, Integer offset, Integer limit, String sortField, String sortType) {
		return posOrderDao.findDetails(systemBookCode,branchNums,dateFrom,dateTo,categoryCodes,offset,limit,sortField,sortType);
	}


}

