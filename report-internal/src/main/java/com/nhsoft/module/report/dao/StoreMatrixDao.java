package com.nhsoft.module.report.dao;



import com.nhsoft.module.report.model.StoreMatrix;
import com.nhsoft.module.report.model.StoreMatrixDetail;

import java.util.List;

public interface StoreMatrixDao {

	public List<StoreMatrix> findByBranch(String systemBookCode, List<Integer> branchNums, List<Integer> itemNums);
	
	public List<StoreMatrix> find(String systemBookCode, Integer itemNum);

	/**
	 * 查询多特性明细
	 * @param systemBookCode
	 * @param branchNum 分店号
	 * @param itemNums 商品主键列表
	 * @return
	 */
	public List<StoreMatrixDetail> findDetails(String systemBookCode, Integer branchNum, List<Integer> itemNums);

	/**
	 * 查询分店商品ID 停售标记
	 * @param systemBookCode
	 * @param branchNum
	 * @return
	 */
	public List<Object[]> findItemSaleCeaseFlag(String systemBookCode, Integer branchNum);
}
