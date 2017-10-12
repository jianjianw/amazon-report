package com.nhsoft.report.dao;

import com.nhsoft.report.model.Branch;

/**
 * Created by yangqin on 2017/10/12.
 */
public interface BranchGroupDao {
	
	/**
	 * 读配送中心分店
	 * @param systemBookCode 帐套号
	 * @param detailBranchNum 分店号
	 * @return
	 */
	public Branch readTransferBranch(String systemBookCode, Integer detailBranchNum);
}
