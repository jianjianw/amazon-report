package com.nhsoft.module.report.service.impl;

import com.nhsoft.module.report.dao.InnerPreSettlementDao;
import com.nhsoft.module.report.model.InnerPreSettlement;
import com.nhsoft.module.report.service.InnerPreSettlementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by yangqin on 2017/11/9.
 */
@Service
public class InnerPreSettlementServiceImpl implements InnerPreSettlementService {
	
	@Autowired
	private InnerPreSettlementDao innerPreSettlementDao;
	
	@Override
	public List<InnerPreSettlement> findBySettleBranch(String systemBookCode, Integer branchNum, Integer centerBranchNum, Date dateFrom, Date dateTo) {
		return innerPreSettlementDao.findBySettleBranch(systemBookCode, branchNum, centerBranchNum, dateFrom, dateTo);
	}
	
	@Override
	public BigDecimal readBranchUnPaidMoney(String systemBookCode, Integer branchNum, Integer centerBranchNum) {
		return innerPreSettlementDao.readBranchUnPaidMoney(systemBookCode, branchNum, centerBranchNum);
	}
	
	@Override
	public List<Object[]> findMoneyByBranchNums(String systemBookCode, Integer centerBranchNum, Date dateFrom, Date dateTo, List<Integer> branchNums) {
		return innerPreSettlementDao.findMoneyByBranchNums(systemBookCode, centerBranchNum, dateFrom, dateTo, branchNums);
	}
	
	@Override
	public List<Object[]> findDueMoney(String systemBookCode, Integer centerBranchNum, List<Integer> branchNums, Date dateFrom, Date dateTo) {
		return innerPreSettlementDao.findDueMoney(systemBookCode, centerBranchNum, branchNums, dateFrom, dateTo);
	}
}
