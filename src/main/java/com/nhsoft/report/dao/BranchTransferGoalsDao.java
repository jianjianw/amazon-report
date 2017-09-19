package com.nhsoft.report.dao;


import com.nhsoft.report.model.BranchTransferGoals;
import com.nhsoft.report.model.BranchTransferGoalsId;

import java.util.Date;
import java.util.List;

public interface BranchTransferGoalsDao {

	public List<BranchTransferGoals> find(String systemBookCode, Integer branchNum);
	
	public void saveOrUpdate(BranchTransferGoals branchTransferGoals);
	
	public List<BranchTransferGoals> findByDate(String systemBookCode,
                                                List<Integer> branchNums, Date dateFrom, Date dateTo, String dateType);
	
	/**
	 * 查询未同步的记录
	 * @param systemBookCode
	 * @return
	 */
	public List<BranchTransferGoals> findUnSynch(String systemBookCode);
	
	/**
	 * 更新同步标记
	 * @param systemBookCode
	 */
	public void updateSynchFlag(String systemBookCode);

	public BranchTransferGoals read(BranchTransferGoalsId id);

	public List<Object[]> findSummaryByDate(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String dateType);
	
}
