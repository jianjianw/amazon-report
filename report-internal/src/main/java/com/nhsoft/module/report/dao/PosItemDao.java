package com.nhsoft.module.report.dao;



import com.nhsoft.module.report.model.ItemBar;
import com.nhsoft.module.report.model.ItemMatrix;
import com.nhsoft.module.report.model.PosItem;
import com.nhsoft.module.report.model.PosItemKit;
import com.nhsoft.module.report.queryBuilder.PosItemQuery;

import java.util.Date;
import java.util.List;

public interface PosItemDao {

	public PosItem read(Integer itemNum);

	public List<Integer> findItemNumsByPosItemQuery(PosItemQuery posItemQuery, int offset, int limit);

	/**
	 * 按类别查询商品主键
	 * @param systemBookCode
	 * @param categoryCodes
	 * @return
	 */
	public List<Integer> findItemNums(String systemBookCode, String categoryCodes);


	public List<PosItem> findByItemNums(List<Integer> itemNums);

	/**
	 * 查询所有商品
	 * @param systemBookCode
	 * @return
	 */
	public List<PosItem> findAll(String systemBookCode);

	/**
	 * 查询库存商品
	 * @param systemBookCode
	 * @return
	 */
	public List<PosItem> find(String systemBookCode);



	/**
	 * 按条件查询商品
	 * @param systemBookCode
	 * @param categoryCodes
	 * @param itemNums
	 * @return
	 */
	public List<PosItem> find(String systemBookCode, List<String> categoryCodes, List<Integer> itemNums, String itemMethod);

	public List<PosItemKit> findPosItemKits(List<Integer> itemNums);

	public List<PosItemKit> findPosItemKits(String systemBookCode);

	public List<PosItemKit> findPosItemKits(Integer itemNum);

	public List<ItemMatrix> findItemMatrixs(List<Integer> itemNums);


	public List<ItemBar> findItemBars(List<Integer> itemNums);

	public List<ItemMatrix> findItemMatrixs(String systemBookCode);



	/**
	 * 按账套查询条码
	 * @param systemBookCode
	 * @return
	 */
	public List<ItemBar> findItemBars(String systemBookCode);

	public List<ItemBar> findItemBars(Integer itemNum);

	/**
	 * 查询多特性明细
	 * @param itemNum
	 * @return
	 */
	public List<ItemMatrix> findItemMatrixs(Integer itemNum);

	/**
	 * 有供货关系的商品
	 * @param supplierNums
	 * @param itemNums
	 * @param categoryCodes
	 * @param branchNum
	 * @return
	 */
	public List<Object[]> findBySuppliers(List<Integer> supplierNums, List<Integer> itemNums, List<String> categoryCodes, Integer branchNum, String systemBookCode);

	/**
	 * 查询停售商品
	 * @param systemBookCode
	 * @param branchNum
	 * @return
	 */
	public List<Integer> findUnSaleItemNums(String systemBookCode);

	public List<PosItemKit> findPosItemKitsWithPosItem(List<Integer> itemNums);

	public List<PosItem> findByPosItemQuery(PosItemQuery posItemQuery, String sortField, String sortName, int first, int count);

	/**
	 * bi 商品维度
	 * */
	public List<Object[]> findItemLat(String systemBookCode);

	/**
	 * 根据分店查询商品信息
	 * @param branchNum 分店号
	 * @return
	 */
	public List<PosItem> findItemByBranch(String systemBookCode, Integer branchNum);

}