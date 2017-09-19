package com.nhsoft.report.dao;


import com.nhsoft.report.model.StoreItemSupplier;

import java.util.Date;
import java.util.List;


public interface StoreItemSupplierDao {

	public List<StoreItemSupplier> find(String systemBookCode, Integer branchNum,
										List<Integer> supplierNums, boolean orderFlag, List<Integer> itemNums);


	public List<StoreItemSupplier> findByItemNums(String systemBookCode, Integer branchNum, List<Integer> itemNums);

	public List<StoreItemSupplier> find(String systemBookCode, Integer branchNum, List<Integer> itemNums, List<Integer> supplierNums);

	public List<StoreItemSupplier> find(String systemBookCode, List<Integer> branchNums, List<Integer> itemNums, List<Integer> supplierNums);


}
