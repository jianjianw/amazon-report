package com.nhsoft.report.service;

import com.nhsoft.report.model.Branch;
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
}