package com.nhsoft.module.report.service.impl;

import com.nhsoft.module.report.dao.OtherInoutDao;
import com.nhsoft.module.report.model.OtherInout;
import com.nhsoft.module.report.service.OtherInoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by yangqin on 2017/9/20.
 */
@Service
public class OtherInoutServiceImpl implements OtherInoutService {
	
	@Autowired
	private OtherInoutDao otherInoutDao;
	
	
	@Override
	public List<Object[]> findPosBranchFlagKindSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return otherInoutDao.findPosBranchFlagKindSummary(systemBookCode, branchNums, dateFrom, dateTo);
	}
	
	@Override
	public List<Object[]> findBranchsMoney(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, List<Integer> innerBranchNums) {
		return otherInoutDao.findBranchsMoney(systemBookCode, branchNum, dateFrom, dateTo, innerBranchNums);
	}
	
	@Override
	public BigDecimal getUnPaidMoney(String systemBookCode, Integer branchNum, Integer innerBranchNum, Integer supplierNum, String clientFid, int type) {
		return otherInoutDao.getUnPaidMoney(systemBookCode, branchNum, innerBranchNum, supplierNum, clientFid, type);
	}
	
	@Override
	public List<OtherInout> findByBranch(String systemBookCode, Integer branchNum, Integer innerBranchNum, Date dateFrom, Date dateTo) {
		return otherInoutDao.findByBranch(systemBookCode, branchNum, innerBranchNum, dateFrom, dateTo);
	}
	
	@Override
	public List<Object[]> findMoneybyBranch(String systemBookCode, Integer branchNum, Integer innerBranchNum, Date dateFrom, Date dateTo) {
		return otherInoutDao.findMoneybyBranch(systemBookCode, branchNum, innerBranchNum, dateFrom, dateTo);
	}
}
