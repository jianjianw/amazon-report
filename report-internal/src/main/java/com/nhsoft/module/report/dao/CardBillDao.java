package com.nhsoft.module.report.dao;

import java.util.Date;
import java.util.List;

public interface CardBillDao {

	
	public List<Object[]> findBranchBalance(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);
	
	public int getBalance(String systemBookCode, Integer branchNum);

	
}
