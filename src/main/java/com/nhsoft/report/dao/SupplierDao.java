package com.nhsoft.report.dao;


import com.nhsoft.report.model.Supplier;

import java.util.List;

public interface SupplierDao {

	public List<Supplier> findAll(String systemBookCode);

	
	public List<Supplier> find(String systemBookCode,
                               Integer branchNum, String supplierCategory, String queryName, Boolean isCenterShared, Boolean isEnable, List<Integer> itemNums);

	public List<Supplier> find(String systemBookCode,
                               List<Integer> branchNums, String supplierCategory, String queryName, Boolean isCenterShared, Boolean isEnable, List<Integer> itemNums);



}
