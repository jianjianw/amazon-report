package com.nhsoft.module.report.dao;

import java.util.List;

public interface BranchItemDao {
	
	public List<Integer> findItemNums(String systemBookCode, Integer branchNum);



}
