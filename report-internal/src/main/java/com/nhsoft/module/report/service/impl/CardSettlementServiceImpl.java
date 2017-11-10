package com.nhsoft.module.report.service.impl;


import com.nhsoft.module.report.dao.CardSettlementDao;
import com.nhsoft.module.report.model.CardSettlement;
import com.nhsoft.module.report.service.CardSettlementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class CardSettlementServiceImpl implements CardSettlementService {
	
	@Autowired
	private CardSettlementDao cardSettlementDao;
	
	@Override
	public List<CardSettlement> findBySettleBranch(String systemBookCode,
			Integer branchNum, Integer settleBranchNum, Date dateFrom,
			Date dateTo) {
		return cardSettlementDao.findBySettleBranch(systemBookCode, branchNum, settleBranchNum, dateFrom, dateTo);
	}

	@Override
	public List<Object[]> findMoneyBySettleBranch(String systemBookCode,
			Integer branchNum, Integer settleBranchNum, Date dateFrom,
			Date dateTo) {
		return cardSettlementDao.findMoneyBySettleBranch(systemBookCode, branchNum, settleBranchNum, dateFrom, dateTo);
	}
	
	@Override
	public BigDecimal readBranchUnPaidMoney(String systemBookCode,
			Integer branchNum, Integer centerBranchNum) {
		return cardSettlementDao.readBranchUnPaidMoney(systemBookCode, branchNum, centerBranchNum);
	}

	@Override
	public List<Object[]> findBranchsMoney(String systemBookCode,
			Integer branchNum, List<Integer> branchNums, Date dateFrom,
			Date dateTo) {
		return cardSettlementDao.findBranchsMoney(systemBookCode, branchNum, branchNums, dateFrom, dateTo);
	}
	
	

}
