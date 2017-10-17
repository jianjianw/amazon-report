package com.nhsoft.report.dao;


import com.nhsoft.report.model.Branch;
import com.nhsoft.report.model.BranchRegion;

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

	/*
	 *	查询分店区域
	 * @param systemBookCode 帐套号
	 * @return
	 * */
	public List<BranchRegion> findBranchRegion(String systemBookCode);

	public List<Branch> findBranchByBranchRegionNum(String systemBookCode, Integer branchRegionNum);

	/**
	 * 查询分店面积
	 * @param systemBookCode
	 * @param branchNums 分店号
	 */

	public List<Object[]> findBranchArea(String systemBookCode,List<Integer> branchNums);


}