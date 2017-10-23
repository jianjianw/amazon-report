package com.nhsoft.module.report.dao;




import com.nhsoft.module.report.model.Branch;
import com.nhsoft.module.report.model.BranchRegion;

import java.util.List;

public interface BranchDao {

	//根据账套号查询所有门店
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