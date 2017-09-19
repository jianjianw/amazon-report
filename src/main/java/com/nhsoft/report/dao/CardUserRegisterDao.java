package com.nhsoft.report.dao;


import java.util.Date;
import java.util.List;

public interface CardUserRegisterDao {
	public List<Object[]> findSalerSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> salerNames);

}
