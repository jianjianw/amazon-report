package com.nhsoft.module.report.service;


import com.nhsoft.module.report.model.PosItem;
import com.nhsoft.module.report.model.PosItemKit;
import com.nhsoft.module.report.shared.queryBuilder.PosItemQuery;

import java.util.List;

/**
 * 商品资料
 * @author nhsoft
 *
 */
public interface PosItemService {

	/**
	 * 查询成分商品
	 * @param itemNum 商品主键
	 * @return
	 */
	public List<PosItemKit> findPosItemKits(Integer itemNum);

	public List<Integer> findItemNumsByPosItemQuery(PosItemQuery posItemQuery, int offset, int limit);

	/**
	 * 按类别查询商品主键
	 * @param systemBookCode
	 * @param categoryCodes 多个类别用逗号隔开
	 * @return
	 */
	public List<Integer> findItemNums(String systemBookCode, String categoryCodes);

	/**
	 * 从缓存中读取帐套下所有商品
	 * @param systemBookCode
	 * @return
	 */
	public List<PosItem> findShortItems(String systemBookCode);


	/**
	 * 根据主键查询 不带明细
	 * @param itemNums 商品主键列表
	 * @return
	 */
	public List<PosItem> findByItemNumsWithoutDetails(List<Integer> itemNums);


	@Deprecated
	public List<PosItem> find(String systemBookCode);


	/**
	 * 按条件查询商品
	 * @param systemBookCode
	 * @param categoryCodes
	 * @param itemNums
	 * @return
	 */
	public List<PosItem> find(String systemBookCode, List<String> categoryCodes, List<Integer> itemNums, String itemMethod);

	/**
	 * 查询成分商品
	 * @param systemBookCode
	 * @return
	 */
	public List<PosItemKit> findPosItemKits(String systemBookCode);

	/**
	 * 查询并组装itemMatrix itemBars
	 * @param posItems
	 */
	public void findItemDetails(List<PosItem> posItems);

	/**
	 * 按条件查询明细
	 * @param systemBookCode
	 * @param branchNum 分店
	 * @param supplierNums 供应商主键列表
	 * @param itemNums 商品主键列表
	 * @param categoryCodes 商品类别代码列表
	 * @return
	 */
	public List<Object[]> findBySuppliers(List<Integer> supplierNums, List<Integer> itemNums, List<String> categoryCodes, Integer branchNum, String systemBookCode);

	/**
	 * 查询分店停售商品主键 商品资料中停售 分店售价中不停售 则不算停售
	 * @param systemBookCode
	 * @param branchNum 分店号
	 * @return
	 */
	public List<Integer> findUnSaleItemNums(String systemBookCode, Integer branchNum);

	/**
	 * 根据主键查询
	 * @param itemNums 商品主键列表
	 * @return
	 */
	public List<PosItem> findByItemNums(List<Integer> itemNums);

	/**
	 * 查询带明细的组合商品
	 * @param itemNums
	 * @return
	 */
	public List<PosItemKit> findPosItemKitsWithDetails(List<Integer> itemNums);


	/**
	 * 按条件查询
	 * @param posItemQuery
	 * @param sortField 排序字段
	 * @param sortType ASC or DESC
	 * @param offset 查询起始位
	 * @param limit 查询数量
	 * @return
	 */
	public List<PosItem> findByPosItemQuery(PosItemQuery posItemQuery, String sortField, String sortType, int offset, int limit);

	/**
	 * 从缓存中查询 缓存中没有从数据库中查
	 * @param systemBookCode
	 * @param itemNums 商品主键列表
	 * @return
	 */
	public List<PosItem> findByItemNumsInCache(String systemBookCode, List<Integer> itemNums);

	/**
	 * 读取 不带明细
	 * @param itemNum
	 * @return
	 */
	public PosItem readWithoutDetails(Integer itemNum);

	/**
	 * bi 商品维度
	 * */
	public List<Object[]> findItemLat(String systemBookCode);
}