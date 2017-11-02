package com.nhsoft.module.report.service.impl;


import com.nhsoft.module.report.dao.PosItemLogDao;
import com.nhsoft.module.report.query.StoreQueryCondition;
import com.nhsoft.module.report.service.PosItemLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by yangqin on 2017/9/20.
 */
@Service
public class PosItemLogServiceImpl implements PosItemLogService {
	
	@Autowired
	private PosItemLogDao posItemLogDao;
	
	
	@Override
	public List<Object[]> findBranchItemFlagSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String summaries, List<Integer> itemNums, Integer storehouseNum) {
		return posItemLogDao.findBranchItemFlagSummary(systemBookCode, branchNums, dateFrom, dateTo, summaries, itemNums, storehouseNum);
	}
	
	@Override
	public List<Object[]> findSumByBranchDateItemFlag(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String summaries, List<Integer> itemNums, Integer storehouseNum, List<String> memos) {
		return posItemLogDao.findSumByBranchDateItemFlag(systemBookCode, branchNums, dateFrom, dateTo, summaries, itemNums, storehouseNum, memos);
	}
	
	@Override
	public List<Object[]> findSumByItemFlag(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String summaries, List<Integer> itemNums, Integer storehouseNum, List<String> memos) {
		return posItemLogDao.findSumByItemFlag(systemBookCode, branchNums, dateFrom, dateTo, summaries, itemNums, storehouseNum, memos);
	}
	
	@Override
	public List<Object[]> findItemBizTypeFlagSummary(StoreQueryCondition storeQueryCondition) {
		return posItemLogDao.findItemBizTypeFlagSummary(storeQueryCondition);
	}
	
	@Override
	public List<Object[]> findItemFlagSummary(StoreQueryCondition storeQueryCondition) {
		return posItemLogDao.findItemFlagSummary(storeQueryCondition);
	}
	
	@Override
	public List<Object[]> findBranchFlagSummary(StoreQueryCondition storeQueryCondition) {
		return posItemLogDao.findBranchFlagSummary(storeQueryCondition);
	}
}
