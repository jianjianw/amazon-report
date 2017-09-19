package com.nhsoft.report.dao;


import com.nhsoft.report.model.*;
import com.nhsoft.report.shared.ServiceBizException;
import com.nhsoft.report.shared.queryBuilder.InventoryQuery;

import java.math.BigDecimal;
import java.util.List;

/**
 * 库存调整DAO
 * @author Administrator
 *
 */
public interface InventoryDao {
	
	public void updateInventory(PosItemLog posItemLog, PosItem posItem, Inventory inventory) throws ServiceBizException;
	
	public void updateInventory(PosItemLog posItemLog, PosItem posItem) throws ServiceBizException;
		
	public void updateAll(Inventory inventory);
	
	public void saveAll(Inventory inventory);
	
	public BigDecimal findCenterInventory(String systemBookCode, Integer branchNum, Integer itemNum);
	
	public BigDecimal findBranchInventory(String systemBookCode, Integer branchNum, Integer itemNum);
	
	public Inventory readByStorehouseAndItem(Integer itemNum, Integer storehouseNum);
	
	public List<Object[]> findItemAmount(String systemBookCode, Integer branchNum, List<Integer> itemNums);
	
	public List<Object[]> findItemAmount(String systemBookCode, List<Integer> branchNums, List<Integer> itemNums);
	
	public List<Object[]> findBranchItemSummary(String systemBookCode, List<Integer> branchNums, Integer storehouseNum);
	
	/**
	 * 按门店 汇总库存数量 金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param itemCategoryCode 商品类别
	 * @param itemNums 
	 * @return
	 */
	public List<Object[]> findBranchSummary(String systemBookCode, List<Integer> branchNums, String itemCategoryCode, List<Integer> itemNums, Integer storehouseNum);

	public List<Inventory> findByQuery(InventoryQuery inventoryQuery,
                                       int offset, int limit, String sortField, String sortType);

	public int findCount(InventoryQuery inventoryQuery);

	public Object[] sumStoreCount(InventoryQuery inventoryQuery);

	public List<Object[]> findExceptInventory(String systemBookCode,
                                              Integer branchNum, Integer storeHouse, List<String> categorys);

	public List<Object[]> findInventory(String systemBookCode,
                                        Integer branchNum, Integer storehouseNum, List<String> itemCategorys);

	/**
	 * 查询配送中心的商品库存
	 * @return
	 */
	public List<Object[]> findCenterStore(String systemBookCode, Integer branchNum, List<Integer> itemNums);

	/**
	 * 查询配送中心库存大于0的商品
	 * @return
	 */
	public List<Integer> findAmountGtZero(String systemBookCode, Integer branchNum);

	public List<Inventory> findByStorehouseNum(Integer storehouseNum);

	public List<Inventory> findByStorehouseNum(Integer storehouseNum, List<Integer> itemNums);


	/**
	 * 有库存的商品
	 * @param storeHouseNum
	 * @return
	 */
	public List<Integer> findItemNumByStorehouseNum(Integer storeHouseNum);

	public BigDecimal findInventoryCost(String systemBookCode,
                                        List<Integer> branchNums, Integer posItemNum);

	/**
	 * 查询加权平均多特性商品库存
	 * @param systemBookCode
	 * @param branchNum
	 * @return
	 */
	public BigDecimal getItemMatrixAmount(String systemBookCode, Integer branchNum, Integer itemNum, Integer itemMatrixNum);

	/**
	 * 查询先进先出多特性商品库存
	 * @param systemBookCode
	 * @param branchNum
	 * @return
	 */
	public BigDecimal getBatchItemMatrixAmount(String systemBookCode, Integer branchNum, Integer itemNum, Integer itemMatrixNum);

	/**
	 * 查询指定批次多特性商品库存
	 * @param systemBookCode
	 * @param branchNum
	 * @return
	 */
	public BigDecimal getLnItemMatrixAmount(String systemBookCode, Integer branchNum, Integer itemNum, Integer itemMatrixNum);

	/**
	 * 读取商品库存
	 * @param systemBookCode
	 * @param branchNum
	 * @param itemNums
	 * @return
	 */
	public List<Inventory> findByItemAndBranch(String systemBookCode, Integer branchNum, List<Integer> itemNums, Boolean centerFlag);

	/**
	 * 查询库存批次
	 * @param storehouseNum
	 * @param inventoryNum
	 * @return
	 */
	public List<InventoryLnDetail> findInventoryLnDetails(Integer storehouseNum, Integer inventoryNum);

	/**
	 * 删除非配送中心商品库存
	 * @param systemBookCode
	 * @param itemNum
	 */
	public void deleteUnRdcItemInventory(String systemBookCode, Integer itemNum);

	public void update(Inventory inventory);

	/**
	 * 按商品 分店汇总库存数量
	 * @param systemBookCode
	 * @param branchNum
	 * @param storehouseNum
	 * @return
	 */
	public List<Object[]> findItemBranchAmount(String systemBookCode, Integer branchNum, Integer storehouseNum);

	public List<Inventory> findByStorehouseNumWithNolock(Integer storehouseNum, List<Integer> itemNums);

	/**
	 * 统计分店 库存数量 辅助数量 金额 按仓库 商品汇总
	 * @param systemBookCode
	 * @param branchNum
	 * @return
	 */
	public List<Object[]> findSummaryByStorehouseAndItem(String systemBookCode, Integer branchNum);

	/**s
	 * 查询库存批次
	 * @param storehouseNum
	 * @param inventoryNum
	 * @return
	 */
	public List<InventoryLnDetail> findInventoryLnDetails(String systemBookCode, Integer branchNum, Integer storehouseNum);

	/**
	 * 查询需催销提醒的批次
	 * @param systemBookCode
	 * @param branchNum
	 * @param storehouseNum
	 * @return
	 */
	public List<InventoryLnDetail> findExpireLns(String systemBookCode, Integer branchNum, Integer storehouseNum, List<String> categoryCodes, List<Integer> itemNums);

	/**
	 * 更新前台销售库存
	 * @param posItemLog
	 * @param posItem
	 * @param inventory
	 */
	public void updatePosOrderInventory(PosItemLog posItemLog, PosItem posItem, Inventory inventory);

	/**
	 * 查询商品库存数量 金额
	 * @param systemBookCode
	 * @param branchNums
	 * @param posItemNum
	 * @return
	 */
	public List<Object[]> findItemSummary(String systemBookCode, List<Integer> branchNums, List<Integer> itemNums);

	/**
	 * 查询先进先出批次库存
	 * @param storehouseNum
	 * @return
	 */
	public List<InventoryBatchDetail> findInventoryBatchDetails(Integer storehouseNum);

	/**
	 * 查询多特性库存
	 * @param storehouseNum
	 * @return
	 */
	public List<InventoryMatrix> findInventoryMatrixs(Integer storehouseNum);

	/**
	 * 查询多特性商品库存
	 * @param systemBookCode
	 * @param branchNum 分店号
	 * @param itemNums 商品主键
	 * @parma isCenter 是否配送中心
	 * @return
	 */
	public List<Object[]> findItemMatrixAmount(String systemBookCode, Integer branchNum, List<Integer> itemNums, Boolean isCenter);


	/**
	 * 查询批次明细
	 * @param storehouseNums 仓库主键号列表
	 * @param itemNums 商品主键号列表
	 * @param fileterZero
	 * @return
	 */
	public List<InventoryLnDetail> findInventoryLnDetails(List<Integer> storehouseNums, List<Integer> itemNums, boolean filterZero);

	/**
	 * 查询待迁移的手工指定批次明细
	 * @return
	 */
	public List<InventoryLnDetail> findMoveInventoryLnDetails();


	/**
	 * 查询待迁移的手工指定批次明细
	 * @return
	 */
	public List<InventoryBatchDetail> findMoveInventoryBatchDetails();

	/**
	 * 删除手工指定批次明细
	 * @param inventoryLnDetail
	 */
	public void deleteLn(InventoryLnDetail inventoryLnDetail);

	/**
	 * 删除先进先出批次明细
	 * @param inventoryBatchDetail
	 */
	public void deleteBatch(InventoryBatchDetail inventoryBatchDetail);

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
	 * 读取手工指定商品批次基本数量、常用数量 常用单位
	 * @param systemBookCode
	 * @param branchNum
	 * @param itemNum
	 * @param centerFlag 是否配送仓库
	 * @return
	 */
	public Object[] getInventoryLnAmount(String systemBookCode, Integer branchNum, Integer itemNum, boolean centerFlag);

	public List<Object[]> findItemAmount(Integer storehouseNum,
                                         List<Integer> itemNums);

	/**
	 * 查询库存历史批次明细
	 * @param systemBookCode
	 * @param branchNum
	 * @param itemNums
	 * @return
	 */
	public List<InventoryLnHistory> findInventoryLnHistorys(String systemBookCode, Integer branchNum,
                                                            List<Integer> itemNums);

	/**
	 * 查询批次商品库存数量大于0的批次的最新入库日期
	 * @param systemBookCode
	 * @param branchNum
	 * @param storehouseNum
	 * @return
	 */
	public List<Object[]> findLnItemMaxDate(String systemBookCode, Integer branchNum, Integer storehouseNum);

	/**
	 * 查询库存小于0的商品数量
	 * @param systemBookCode
	 * @param branchNum 分店号
	 * @param storehouseNum 仓库主键
	 * @param itemCategorys 商品类别代码列表
	 * @return
	 */
	public int countExceptInventory(String systemBookCode,
                                    Integer branchNum, Integer storehouseNum, List<String> itemCategorys);
	
	/**
	 * 带行锁读取库存
	 * @param storehouseNum
	 * @param itemNums
	 * @return
	 */
	public List<Inventory> findByStorehouseNumWithRowLock(Integer storehouseNum, List<Integer> itemNums);

	/**
	 * 只调整库存金额
	 * @param posItemLog
	 * @param posItem 
	 * @param inventory
	 */
	public void updateInventoryMoney(PosItemLog posItemLog, PosItem posItem, Inventory inventory);

	public List<InventoryBatchDetail> findInventoryBatchDetails(List<Integer> storehouseNums);

	public List<InventoryMatrix> findInventoryMatrixs(List<Integer> storehouseNums);

	/**
	 * 查询商品库存数量、辅助数量
	 * @param systemBookCode
	 * @param branchNum 分店号
	 * @param itemNum 商品主键
	 * @param isCenter 是否配送中心
	 * @return
	 */
	public Object[] getItemInventory(String systemBookCode, Integer branchNum, Integer itemNum, boolean isCenter);

	/**
	 * 带行锁读库存
	 * @param itemNum
	 * @param storehouseNum
	 * @return
	 */
	public Inventory readByStorehouseAndItemWithRowLock(Integer itemNum, Integer storehouseNum);
	
	/**
	 * 查询分店有效的商品库存
	 * @param systemBookCode
	 * @param branchNum
	 * @return
	 */
	public List<Inventory> findValidItemInventories(String systemBookCode, Integer branchNum);
	
	/**
	 * 查询库存批次
	 * @param storehouseNum
	 * @param itemNum
	 * @return
	 */
	public List<InventoryLnDetail> findInventoryLnDetailsByItem(Integer storehouseNum, Integer itemNum);

	/**
	 * 删除库存
	 * @param inventory
	 */
	public void delete(Inventory inventory);

	/**
	 * 库存删除
	 * @param systemBookCode
	 * @param itemNum
	 */
	public void deleteByItemNum(String systemBookCode, Integer itemNum);
	
	/**
	 * 按门店 汇总库存数量 金额 
	 * @param systemBookCode
	 * @param branchNums 分店号列表
	 * @param itemNums
	 * @return
	 */
	public List<Object[]> findBranchSummary(String systemBookCode, List<Integer> branchNums, List<Integer> itemNums);
}
