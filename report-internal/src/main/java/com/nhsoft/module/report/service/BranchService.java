package com.nhsoft.module.report.service;


import com.nhsoft.module.report.dto.BranchDTO;
import com.nhsoft.module.report.model.Branch;
import com.nhsoft.module.report.model.BranchRegion;

import java.util.List;

/**
 * 分店
 * @author nhsoft
 *
 */
public interface BranchService {



	/**
	 * 查询分店
	 * @param systemBookCode 帐套号
	 * @return
	 */
	public List<Branch> findAll(String systemBookCode);
	/**
	 * 从缓存中读取分店
	 * @param systemBookCode
	 * @param branchNum
	 * @return
	 */
	public Branch readInCache(String systemBookCode, Integer branchNum);

	/**
	 * 从缓存中读取分店
	 * @param systemBookCode 帐套号
	 * @return
	 */
	public List<Branch> findInCache(String systemBookCode);

	/**
	 * 查询启用的配送中心分店
	 * @param systemBookCode 帐套号
	 * @return
	 */
	public List<Branch> findActivedRdc(String systemBookCode);

	/**
	 * 查询所有启用分店
	 * @param systemBookCode 帐套号
	 * @return
	 */
	public List<Branch> findAllActived(String systemBookCode);

	/*
	 *	查询分店区域
	 * @param systemBookCode 帐套号
	 * @return
	 * */
	public List<BranchRegion> findBranchRegion(String systemBookCode);


	/**
	 * 按照区域查询分店
	 * @param systemBookCode 帐套号
	 * @param  branchRegionNum   分店区域号
	 * @return
	 * */
	public List<Branch> findBranchByBranchRegionNum(String systemBookCode,Integer branchRegionNum);

	/**
	 * 查询分店面积
	 * @param systemBookCode
	 * @param branchNums 分店号
	 */
	public List<Object[]> findBranchArea(String systemBookCode,List<Integer> branchNums);

	/**
	 * 根据分店号查询分店
	 * @param systemBookCode
	 * @param branchNum 分店号
	 * */
	public Branch readWithNolock(String systemBookCode, Integer branchNum);

	/**
	 * bi 查询分店
	 *  @param systemBookCode
	 * */
	public List<Object[]> findBranch(String systemBookCode);



}