package com.nhsoft.module.report.dao;

import java.util.List;

public interface DistributionCentreMatrixDao {
	
	/**
	 * 查询配送中心推荐商品编号
	 * @param systemBookCode
	 * @param branchNum
	 * @return
	 */
	public List<Integer> findRecommend(String systemBookCode, Integer branchNum);

}
