package com.nhsoft.report.service.impl;

import com.nhsoft.report.dao.BranchGroupDao;
import com.nhsoft.report.model.Branch;
import com.nhsoft.report.service.BranchGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by yangqin on 2017/10/12.
 */
@Service
public class BranchGroupServiceImpl implements BranchGroupService {
	
	@Autowired
	private BranchGroupDao branchGroupDao;
	
	@Override
	public Branch readTransferBranch(String systemBookCode, Integer detailBranchNum) {
		return null;
	}
}
