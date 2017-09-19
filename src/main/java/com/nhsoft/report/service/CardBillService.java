package com.nhsoft.report.service;

import java.util.Date;
import java.util.List;

/**
 * Created by yangqin on 2017/9/19.
 */
public interface CardBillService {
	
	public List<Object[]> findBranchBalance(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);
	
	
	
}
