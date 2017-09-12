package com.nhsoft.report.rpc.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.nhsoft.report.dto.BusinessCollection;
import com.nhsoft.report.rpc.ReportRpc;
import com.nhsoft.report.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by yangqin on 2017/9/5.
 */
@Service
@Component
public class ReportRpcImpl implements ReportRpc{

	
	@Autowired
	private ReportService reportService;
	
	
	@Override
	public Object excuteSql(String systemBookCode, String sql) {
		return reportService.excuteSql(sql);
	}
	
	@Override
	public List<Object[]> findDayWholes(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, boolean isMember) {
		return reportService.findDayWholes(systemBookCode, branchNums, dateFrom, dateTo, isMember);
	}
	
	@Override
	public List<Object[]> findMonthWholes(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, boolean isMember) {
		return reportService.findMonthWholes(systemBookCode, branchNums, dateFrom, dateTo, isMember);
	}
	
	@Override
	public List<BusinessCollection> findBusinessCollectionByBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return reportService.findBusinessCollectionByBranch(systemBookCode, branchNums, dateFrom, dateTo);
	}
	
	@Override
	public List<BusinessCollection> findBusinessCollectionByTerminal(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return reportService.findBusinessCollectionByTerminal(systemBookCode, branchNums, dateFrom, dateTo);
	}
	
	@Override
	public List<BusinessCollection> findBusinessCollectionByShiftTable(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String casher) {
		return reportService.findBusinessCollectionByShiftTable(systemBookCode, branchNums, dateFrom, dateTo, casher);
	}
	
	@Override
	public List<BusinessCollection> findBusinessCollectionByBranchDay(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return reportService.findBusinessCollectionByBranchDay(systemBookCode, branchNums, dateFrom, dateTo);
	}
}
