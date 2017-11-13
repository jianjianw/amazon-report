package com.nhsoft.module.report.service.impl;

import com.nhsoft.module.report.dao.BranchItemDao;
import com.nhsoft.module.report.service.BranchItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchItemServiceImpl implements BranchItemService {

	@Autowired
	private BranchItemDao branchItemDao;
	
	@Override
	public List<Integer> findItemNums(String systemBookCode, Integer branchNum) {
		return branchItemDao.findItemNums(systemBookCode, branchNum);
	}

}
