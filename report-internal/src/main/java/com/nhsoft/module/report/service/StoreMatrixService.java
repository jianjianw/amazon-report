package com.nhsoft.module.report.service;


import com.nhsoft.module.report.model.StoreMatrix;
import com.nhsoft.module.report.model.StoreMatrixDetail;

import java.util.List;

/**
 * Created by yangqin on 2017/9/20.
 */
public interface StoreMatrixService {
	
	public List<StoreMatrix> findByBranch(String systemBookCode, Integer branchNum, List<Integer> itemNums);

	public List<StoreMatrix> findByBranch(String systemBookCode, List<Integer> branchNums, List<Integer> itemNums);

	/**
	 * 查询多特性明细
	 * @param systemBookCode
	 * @param branchNum 分店号
	 * @param itemNums 商品主键列表
	 * @return
	 */
	public List<StoreMatrixDetail> findDetails(String systemBookCode, Integer branchNum, List<Integer> itemNums);


}
