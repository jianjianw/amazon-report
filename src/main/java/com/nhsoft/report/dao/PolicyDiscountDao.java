package com.nhsoft.report.dao;


import java.util.Date;
import java.util.List;

public interface PolicyDiscountDao {
	public int count(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String dateType);
}
