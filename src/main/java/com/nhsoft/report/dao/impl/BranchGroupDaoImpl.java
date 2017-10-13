package com.nhsoft.report.dao.impl;

import com.nhsoft.report.dao.BranchGroupDao;
import com.nhsoft.report.model.Branch;
import org.springframework.stereotype.Repository;

/**
 * Created by yangqin on 2017/10/12.
 */
@Repository
public class BranchGroupDaoImpl extends DaoImpl implements BranchGroupDao {
	
	
	@Override
	public Branch readTransferBranch(String systemBookCode, Integer detailBranchNum) {
		return null;
	}
}
