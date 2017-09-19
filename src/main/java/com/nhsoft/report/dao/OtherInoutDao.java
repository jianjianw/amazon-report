package com.nhsoft.report.dao;



import java.util.Date;
import java.util.List;

public interface OtherInoutDao {
	public List<Object[]> findClientsMoney(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<String> clientFids);
}
