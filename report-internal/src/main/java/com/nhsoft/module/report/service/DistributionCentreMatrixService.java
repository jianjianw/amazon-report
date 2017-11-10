package com.nhsoft.module.report.service;

import java.util.List;

/**
 * 中心商品特性
 * @author nhsoft
 *
 */
public interface DistributionCentreMatrixService {

	
	/**
	 * 查询中心推荐商品
	 * @param systemBookCode
	 * @param branchNum
	 * @return
	 */
	public List<Integer> findRecommend(String systemBookCode, Integer branchNum);
	
}
