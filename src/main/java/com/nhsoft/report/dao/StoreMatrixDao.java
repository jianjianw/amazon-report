package com.nhsoft.report.dao;



import com.nhsoft.report.model.StoreMatrix;
import com.nhsoft.report.model.StoreMatrixDetail;
import com.nhsoft.report.model.StoreMatrixId;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface StoreMatrixDao {

	public List<StoreMatrix> findByBranch(String systemBookCode, Integer branchNum, List<Integer> itemNums);
	
	public StoreMatrix read(StoreMatrixId id);

	public List<StoreMatrix> find(String systemBookCode, Integer itemNum);

	public void update(StoreMatrix storeMatrix);
	
	/**
	 * 批量更新价格
	 * @param systemBookCode
	 * @param branchNums
	 * @param itemNum
	 * @param regelarPrice
	 * @param level2Price
	 * @param level3Price
	 * @param level4Price
	 * @param costPrice
	 * @param minPrice
	 */
	public void batchAdjustPrice(String systemBookCode, List<Integer> branchNums, Integer itemNum, BigDecimal regelarPrice, BigDecimal level2Price, BigDecimal level3Price, BigDecimal level4Price, BigDecimal costPrice, BigDecimal minPrice);

	public void save(StoreMatrix storeMatrix);
	
	/**
	 * 查询分店商品ID 停售标记
	 * @param systemBookCode
	 * @param branchNum
	 * @return
	 */
	public List<Object[]> findItemSaleCeaseFlag(String systemBookCode, Integer branchNum);

	public void flush();

	public StoreMatrixDetail readDetail(StoreMatrixDetail.StoreMatrixDetailId id);

	public void saveDetail(StoreMatrixDetail storeMatrixDetail);

	public void updateDetail(StoreMatrixDetail storeMatrixDetail);
	
	/**
	 * 查询多特性明细
	 * @param systemBookCode
	 * @param branchNum 分店号
	 * @param itemNums 商品主键列表
	 * @return
	 */
	public List<StoreMatrixDetail> findDetails(String systemBookCode, Integer branchNum, List<Integer> itemNums);
	
	/**
	 * 根据最后修改时间查询
	 * @param systemBookCode
	 * @param branchNum
	 * @param storeMatrixLastEditTime 最后修改时间
	 * @return
	 */
	public List<StoreMatrix> findByLastEditTime(String systemBookCode, Integer branchNum, Date storeMatrixLastEditTime);
	
	/**
	 * 查询启用分店价格的记录
	 * @param systemBookCode 
	 * @param branchNum 分店号
	 * @param itemNums 商品主键列表
	 * @return
	 */
	public List<StoreMatrix> findPriceEnabledByBranch(String systemBookCode, Integer branchNum, List<Integer> itemNums);
	
	/**
	 * 查询分店停售或停购的记录
	 * @param systemBookCode
	 * @param branchNums
	 * @return
	 */
	public List<Object[]> findCeaseItems(String systemBookCode, List<Integer> branchNums);
	
	/**
	 * 更新是否启用停售标记
	 * @param systemBookCode
	 * @param branchNums
	 * @param storeMatrixSaleEnabled
	 */
	public void updateSaleEnabled(String systemBookCode, List<Integer> branchNums, boolean storeMatrixSaleEnabled);
}
