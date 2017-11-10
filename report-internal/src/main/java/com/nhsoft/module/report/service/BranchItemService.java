package com.nhsoft.module.report.service;

import java.util.List;

public interface BranchItemService {
	
	public List<Integer> findItemNums(String systemBookCode, Integer branchNum);
	

}
