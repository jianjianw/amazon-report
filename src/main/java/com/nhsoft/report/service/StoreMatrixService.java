package com.nhsoft.report.service;

import com.nhsoft.report.model.StoreMatrix;

import java.util.List;

/**
 * Created by yangqin on 2017/9/20.
 */
public interface StoreMatrixService {
	
	public List<StoreMatrix> findByBranch(String systemBookCode, Integer branchNum, List<Integer> itemNums);
	
}
