package com.nhsoft.module.report.service.impl;

import com.nhsoft.module.report.dao.InnerSettlementDao;
import com.nhsoft.module.report.model.InnerSettlement;
import com.nhsoft.module.report.service.InnerSettlementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by yangqin on 2017/11/9.
 */
@Service
public class InnerSettlementServiceImpl implements InnerSettlementService {
	
	@Autowired
	private InnerSettlementDao innerSettlementDao;
	@Override
	public List<InnerSettlement> findBySettleBranchNum(String systemBookCode, Integer centerBranchNum, Integer branchNum, Date dateFrom, Date dateTo) {
		return innerSettlementDao.findBySettleBranchNum(systemBookCode, centerBranchNum, branchNum, dateFrom, dateTo);
	}
	
	@Override
	public List<Object[]> findMoneyByBranchNums(String systemBookCode, Integer centerBranchNum, Date dateFrom, Date dateTo, List<Integer> branchNums, boolean flag) {
		return innerSettlementDao.findMoneyByBranchNums(systemBookCode, centerBranchNum, dateFrom, dateTo, branchNums, flag);
	}
}
