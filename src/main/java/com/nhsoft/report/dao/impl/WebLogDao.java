package com.nhsoft.report.dao.impl;


import com.nhsoft.report.shared.queryBuilder.LogQuery;


public interface WebLogDao {
	public Integer countByQuery(String systemBookCode, Integer branchNum,
								LogQuery queryCondition);
}
