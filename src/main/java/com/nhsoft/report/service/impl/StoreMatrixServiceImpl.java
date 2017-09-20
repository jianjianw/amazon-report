package com.nhsoft.report.service.impl;

import com.nhsoft.report.dao.StoreMatrixDao;
import com.nhsoft.report.model.StoreMatrix;
import com.nhsoft.report.service.StoreMatrixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yangqin on 2017/9/20.
 */
@Service
public class StoreMatrixServiceImpl implements StoreMatrixService {
	
	@Autowired
	private StoreMatrixDao storeMatrixDao;
	
	@Override
	public List<StoreMatrix> findByBranch(String systemBookCode, Integer branchNum, List<Integer> itemNums) {
		return storeMatrixDao.findByBranch(systemBookCode, branchNum, itemNums);
	}
}
