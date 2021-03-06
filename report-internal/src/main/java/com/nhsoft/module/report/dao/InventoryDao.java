package com.nhsoft.module.report.dao;

import com.nhsoft.module.report.model.Inventory;
import com.nhsoft.module.report.model.InventoryLnDetail;

import java.util.Date;
import java.util.List;

/**
 * 库存调整DAO
 * @author Administrator
 *
 */
public interface InventoryDao {

	
	public List<Object[]> findItemAmount(String systemBookCode, List<Integer> branchNums, List<Integer> itemNums, Integer storehouseNum);

	public List<Object[]> findItemAmountByStorehouse(String systemBookCode, Integer branchNum, List<Integer> itemNums,List<Integer> storehouseNums);

	public List<Object[]> findBranchItemSummary(String systemBookCode, List<Integer> branchNums, Integer storehouseNum, List<Integer> itemNums);
	
	/**
	 * 按门店 汇总库存数量 金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param itemCategoryCode 商品类别
	 * @param itemNums 
	 * @return
	 */
	public List<Object[]> findBranchSummary(String systemBookCode, List<Integer> branchNums, String itemCategoryCode, List<Integer> itemNums, Integer storehouseNum);


	public List<Object[]> findInventory(String systemBookCode,
                                        Integer branchNum, Integer storehouseNum, List<String> itemCategorys);

	/**
	 * 查询配送中心的商品库存
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


	/**
	 * 查询需催销提醒的批次
	 * @param systemBookCode
	 * @param branchNum
	 * @param storehouseNum
	 * @return
	 */
	public List<InventoryLnDetail> findExpireLns(String systemBookCode, Integer branchNum, Integer storehouseNum, List<String> categoryCodes, List<Integer> itemNums);

	/**
	 * 查询商品库存数量 金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param itemNums
	 * @return
	 */
	public List<Object[]> findItemSummary(String systemBookCode, List<Integer> branchNums, List<Integer> itemNums);


	/**
	 * 查询库存批次明细
	 * @param systemBookCode
	 * @param branchNum
	 * @param itemNums
	 * @return
	 */
	public List<InventoryLnDetail> findInventoryLnDetails(String systemBookCode, Integer branchNum,
                                                          List<Integer> itemNums);

	/**
	 * 按门店 汇总库存数量 金额 
	 * @param systemBookCode
	 * @param branchNums 分店号列表
	 * @param itemNums
	 * @return
	 */
	public List<Object[]> findBranchSummary(String systemBookCode, List<Integer> branchNums, List<Integer> itemNums);


	/**
	 * 查询断货天数 商品库存为0的天数
	 * @param systemBookCode
	 * @param branchNum 分店号
	 * @param itemNums
	 * @return
	 * */
	 public List<Object[]> findInventoryLostDayCount(String systemBookCode, Integer branchNum, List<Integer> itemNums);


	/**
	 * 查询断货次数 		时间范围内从 有库存到无库存的 次数
	 * @param systemBookCode
	 * @param branchNum 分店号
	 * @param itemNums
	 * @return
	 * */
	public List<Object[]> findInventoryLostCount(String systemBookCode, Integer branchNum, List<Integer> itemNums);


	public List<Inventory> findByStorehouseNum(Integer storehouseNum, List<Integer> itemNums);
}
