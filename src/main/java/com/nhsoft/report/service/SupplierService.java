package com.nhsoft.report.service;

import com.nhsoft.report.model.Supplier;
import java.util.List;

/**
 * 供应商
 * @author nhsoft
 *
 */
public interface SupplierService {
	/**
	 *
	 * @param systemBookCode
	 * @param branchNums
	 * @param supplierCategory 供应商类别
	 * @param queryName 关键字 拼音、代码、名称模糊匹配
	 * @param isCenterShared 是否中心共享
	 * @param isEnable 是否启用
	 * @return
	 */
	public List<Supplier> find(String systemBookCode,
							   List<Integer> branchNums, String supplierCategory, String queryName, Boolean isCenterShared, Boolean isEnable);



	/**
	 *
	 * @param systemBookCode
	 * @param branchNum
	 * @param supplierCategory 供应商类别
	 * @param queryName 关键字 拼音、代码、名称模糊匹配
	 * @param isCenterShared 是否中心共享
	 * @param isEnable 是否启用
	 * @param itemNums 商品主键列表 查询有供货关系的供应商
	 * @return
	 */
	public List<Supplier> find(String systemBookCode,
							   Integer branchNum, String supplierCategory, String queryName, Boolean isCenterShared, Boolean isEnable, List<Integer> itemNums);


	/**
	 * 按帐套查询
	 * @param systemBookCode
	 * @return
	 */
	public List<Supplier> findAll(String systemBookCode);


	/**
	 * 从缓存中读取
	 * @param systemBookCode
	 * @return
	 */
	public List<Supplier> findInCache(String systemBookCode);

}