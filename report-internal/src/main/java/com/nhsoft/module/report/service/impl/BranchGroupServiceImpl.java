package com.nhsoft.module.report.service.impl;

import com.nhsoft.module.report.dao.BranchGroupDao;
import com.nhsoft.module.report.model.Branch;
import com.nhsoft.module.report.service.BranchGroupService;
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
