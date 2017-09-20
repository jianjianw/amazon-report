package com.nhsoft.report.dao;



import com.nhsoft.report.model.StoreMatrix;

import java.util.List;

public interface StoreMatrixDao {

	public List<StoreMatrix> findByBranch(String systemBookCode, Integer branchNum, List<Integer> itemNums);
	
	public List<StoreMatrix> find(String systemBookCode, Integer itemNum);
}