package com.nhsoft.report.service;

import com.nhsoft.report.model.Branch;

/**
 * 分店分组
 * @author nhsoft
 *
 */
public interface BranchGroupService {


	/**
	 * 读配送中心分店
	 * @param systemBookCode 帐套号
	 * @param detailBranchNum 分店号
	 * @return
	 */
	public Branch readTransferBranch(String systemBookCode, Integer detailBranchNum);

	
}
