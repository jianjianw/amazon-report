package com.nhsoft.report.service;



import com.nhsoft.report.model.StoreItemSupplier;

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

}
