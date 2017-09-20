package com.nhsoft.report.dao;


import com.nhsoft.report.model.BranchTransferGoals;

import java.util.Date;
import java.util.List;

public interface BranchTransferGoalsDao {

	public List<BranchTransferGoals> find(String systemBookCode, Integer branchNum);
	
	public List<BranchTransferGoals> findByDate(String systemBookCode,
                                                List<Integer> branchNums, Date dateFrom, Date dateTo, String dateType);
	
	public List<Object[]> findSummaryByDate(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String dateType);
	
}
