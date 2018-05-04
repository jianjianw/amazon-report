package com.nhsoft.module.report.service;

import com.nhsoft.module.report.model.Inventory;

import java.util.List;

/**
 * 库存
 * @author nhsoft
 *
 */
public interface InventoryService {
	/**
	 * 按商品主键汇总库存数量 金额
	 * @param systemBookCode
	 * @param branchNums 分店号
	 * @param itemNums 商品主键列表
	 * @param storehouseNum
	 * @return
	 */
	public List<Object[]> findItemAmount(String systemBookCode, List<Integer> branchNums, List<Integer> itemNums, Integer storehouseNum);

	/**
	 * 按分店 商品主键汇总 库存数量 金额
	 * @param systemBookCode
	 * @param branchNums 分店号列表
	 * @param itemNums
     * @return
	 */
	public List<Object[]> findBranchItemSummary(String systemBookCode, List<Integer> branchNums, List<Integer> itemNums);
	
	/**
	 * 按商品主键汇总配送仓库库存数量 金额
	 * @param systemBookCode
	 * @param branchNum 分店号
	 * @param itemNums 商品主键列表
	 * @return
	 */
	public List<Object[]> findCenterStore(String systemBookCode, Integer branchNum, List<Integer> itemNums);


	/**
	 * 读取商品库存
	 * @param systemBookCode
	 * @param branchNum
	 * @param itemNums
	 * @return
	 */
	public List<Inventory> findByItemAndBranch(String systemBookCode, Integer branchNum, List<Integer> itemNums, Boolean centerFlag);

	public List<Inventory> findByStorehouseNum(Integer storehouseNum, List<Integer> itemNums);

}