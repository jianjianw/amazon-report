package com.nhsoft.report.service.impl;

import com.nhsoft.report.dao.ReportDao;
import com.nhsoft.report.dto.BusinessCollection;
import com.nhsoft.report.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by yangqin on 2017/9/5.
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED, isolation = Isolation.READ_UNCOMMITTED)
public class ReportServiceImpl implements ReportService{
	
	@Autowired
	private ReportDao reportDao;
	
	@Override
	public Object excuteSql(String sql) {
		return reportDao.excuteSql(sql);
	}
	
	@Override
	public List<Object[]> findDayWholes(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, boolean isMember) {
		return reportDao.findDayWholes(systemBookCode, branchNums, dateFrom, dateTo, isMember);
	}
	
	@Override
	public List<Object[]> findMonthWholes(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, boolean isMember) {
		return reportDao.findMonthWholes(systemBookCode, branchNums, dateFrom, dateTo, isMember);
	}
	
	@Override
	public List<BusinessCollection> findBusinessCollectionByBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return reportDao.findBusinessCollectionByBranch(systemBookCode, branchNums, dateFrom, dateTo);
	}
	
	@Override
	public List<BusinessCollection> findBusinessCollectionByTerminal(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return reportDao.findBusinessCollectionByTerminal(systemBookCode, branchNums, dateFrom, dateTo);
	}
	
	@Override
	public List<BusinessCollection> findBusinessCollectionByShiftTable(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String casher) {
		return reportDao.findBusinessCollectionByShiftTable(systemBookCode, branchNums, dateFrom, dateTo, casher);
	}
	
	@Override
	public List<BusinessCollection> findBusinessCollectionByBranchDay(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return reportDao.findBusinessCollectionByBranchDay(systemBookCode, branchNums, dateFrom, dateTo);
	}
}
