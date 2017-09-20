package com.nhsoft.report.dao;


import com.nhsoft.report.model.Branch;

import java.util.List;

public interface BranchDao {

	public List<Branch> findAll(String systemBookCode);

	/**
	 * 查询启用的配送中心
	 * @param systemBookCode
	 * @return
	 */
	public List<Branch> findActivedRdc(String systemBookCode);


	public Branch readWithNolock(String systemBookCode, Integer branchNum);

	public List<Branch> findAllActived(String systemBookCode);


}