package com.nhsoft.report.dao;



import com.nhsoft.report.model.PosMachine;
import com.nhsoft.report.model.PosMachineId;

import java.util.List;

public interface PosMachineDao {

	public List<PosMachine> findByBranchs(String systemBookCode, List<Integer> branchNums, String queryField);
	

	public PosMachine read(PosMachineId id);

	
}
