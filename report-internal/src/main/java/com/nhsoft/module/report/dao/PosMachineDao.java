package com.nhsoft.module.report.dao;


import com.nhsoft.module.report.model.PosMachine;

import java.util.List;

public interface PosMachineDao {

	public List<PosMachine> findByBranchs(String systemBookCode, List<Integer> branchNums, String queryField);


	
}
