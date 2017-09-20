package com.nhsoft.report.dao;



import java.util.Date;
import java.util.List;

public interface MarketActionDao {
	public int count(String systemBookCode, Integer branchNum, Date dateFrom,
					 Date dateTo, Integer stateCode, String actionType);

}
