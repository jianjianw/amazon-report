package com.nhsoft.report.dao;



import com.nhsoft.report.model.PosMachine;
import com.nhsoft.report.model.PosMachineId;

import java.util.List;

public interface PosMachineDao {

	public List<PosMachine> findByBranchs(String systemBookCode, List<Integer> branchNums, String queryField);

	public PosMachine readByTerminalId(String systemBookCode, Integer branchNum, String posMachineTerminalId);

	public void save(PosMachine posMachine);

	public void update(PosMachine posMachine);

	public void delete(PosMachine posMachine);
	
	public PosMachine readByName(String systemBookCode, Integer branchNum, String posMachineName);

	public PosMachine read(PosMachineId id);

	
}
