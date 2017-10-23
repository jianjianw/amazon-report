package com.nhsoft.module.report.dao;


import com.nhsoft.module.report.model.StoreItemSupplier;

import java.util.List;


public interface StoreItemSupplierDao {

	public List<StoreItemSupplier> find(String systemBookCode, Integer branchNum,
										List<Integer> supplierNums, boolean orderFlag, List<Integer> itemNums);


	public List<StoreItemSupplier> findByItemNums(String systemBookCode, Integer branchNum, List<Integer> itemNums);

	public List<StoreItemSupplier> find(String systemBookCode, Integer branchNum, List<Integer> itemNums, List<Integer> supplierNums);

	public List<StoreItemSupplier> find(String systemBookCode, List<Integer> branchNums, List<Integer> itemNums, List<Integer> supplierNums);

	/**
	 * 查询主供应商
	 * @param systemBookCode
	 * @param branchNums
	 * @param itemNums
	 * @return
	 */
	public List<StoreItemSupplier> findDefaults(String systemBookCode, List<Integer> branchNums, List<Integer> itemNums);

}
