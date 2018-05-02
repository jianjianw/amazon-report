package com.nhsoft.module.report.service.impl;

import com.nhsoft.module.report.dao.CardUserRegisterDao;
import com.nhsoft.module.report.model.Branch;
import com.nhsoft.module.report.model.CardUserLog;
import com.nhsoft.module.report.queryBuilder.CardReportQuery;
import com.nhsoft.module.report.service.BranchService;
import com.nhsoft.module.report.service.CardUserRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CardUserRegisterServiceImpl implements CardUserRegisterService {
	
	@Autowired
	private CardUserRegisterDao cardUserRegisterDao;
	@Autowired
	private BranchService branchService;


	@Override
	public List<CardUserLog> findByCardReportQuery(CardReportQuery cardReportQuery, int offset, int limit) {
		List<Branch> branchs = branchService.findInCache(cardReportQuery.getSystemBookCode());
		List<CardUserLog> cardUserLogs = cardUserRegisterDao.findByCardReportQuery(cardReportQuery, offset, limit);
		for(int i = 0;i < cardUserLogs.size();i++){
			CardUserLog cardUserLog = cardUserLogs.get(i);
			Branch branch = Branch.get(branchs, cardUserLog.getBranchNum());
			if(branch != null){
				cardUserLog.setCardUserLogBranchName(branch.getBranchName());
			}
		}
		return cardUserLogs;
	}
	

}
