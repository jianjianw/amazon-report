package com.nhsoft.report.service;


import com.nhsoft.report.model.Storehouse;

import java.util.List;

/**
 * 仓库
 * @author nhsoft
 *
 */
public interface StorehouseService {

	/**
	 * 查询分店仓库
	 * @param systemBookCode
	 * @param branchNums 分店号列表
	 * @return
	 */
	public List<Storehouse> findByBranchs(String systemBookCode, List<Integer> branchNums);


	/**
	 * 查询分店仓库
	 * @param systemBookCode
	 * @param branchNum 分店号
	 * @return
	 */
	public List<Storehouse> findByBranch(String systemBookCode, Integer branchNum);

}