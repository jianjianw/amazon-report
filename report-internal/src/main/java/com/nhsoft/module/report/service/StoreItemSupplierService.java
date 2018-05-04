package com.nhsoft.module.report.service;


import com.nhsoft.module.report.model.StoreItemSupplier;

import java.util.List;

/**
 * 供应商明细
 * @author nhsoft
 *
 */
public interface StoreItemSupplierService {
	/**
	 * 查询主供应商
	 * @param systemBookCode
	 * @param branchNums
	 * @param itemNums
	 * @return
	 */
	public List<StoreItemSupplier> findDefaults(String systemBookCode, List<Integer> branchNums, List<Integer> itemNums);

	public List<StoreItemSupplier> find(String systemBookCode, Integer branchNum,
										List<Integer> supplierNums, boolean orderFlag, List<Integer> itemNums);

}
