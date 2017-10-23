package com.nhsoft.module.report.dao;



import java.util.Date;

public interface MarketActionDao {
	public int count(String systemBookCode, Integer branchNum, Date dateFrom,
					 Date dateTo, Integer stateCode, String actionType);

}
