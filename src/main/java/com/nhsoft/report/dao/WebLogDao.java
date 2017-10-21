package com.nhsoft.report.dao;


import com.nhsoft.module.report.query.LogQuery;


public interface WebLogDao {
	public Integer countByQuery(String systemBookCode, Integer branchNum,
								LogQuery queryCondition);
}
