package com.nhsoft.module.report.service.impl;

import com.nhsoft.module.report.dao.StoreMatrixDao;
import com.nhsoft.module.report.model.StoreMatrix;
import com.nhsoft.module.report.model.StoreMatrixDetail;
import com.nhsoft.module.report.service.StoreMatrixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
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
		return storeMatrixDao.findByBranch(systemBookCode, Collections.singletonList(branchNum), itemNums);
	}

	@Override
	public List<StoreMatrix> findByBranch(String systemBookCode, List<Integer> branchNums, List<Integer> itemNums) {
		return storeMatrixDao.findByBranch(systemBookCode, branchNums, itemNums);
	}

	@Override
	public List<StoreMatrixDetail> findDetails(String systemBookCode, Integer branchNum, List<Integer> itemNums) {
		return storeMatrixDao.findDetails(systemBookCode,branchNum,itemNums);
	}
}
