package com.nhsoft.report.service.impl;

import com.google.gson.Gson;
import com.nhsoft.report.dao.SupplierDao;
import com.nhsoft.report.model.Supplier;
import com.nhsoft.report.service.SupplierService;
import com.nhsoft.report.util.AppUtil;
import com.nhsoft.report.util.BaseManager;


import java.util.List;

@SuppressWarnings("unchecked")
public class SupplierServiceImpl extends BaseManager implements SupplierService {

	private SupplierDao supplierDao;
	private Gson gson;
	public SupplierServiceImpl() {
		gson = AppUtil.toBuilderGson();
	}


	@Override
	public List<Supplier> find(String systemBookCode, Integer branchNum, String supplierCategory, String queryName,
							   Boolean isCenterShared, Boolean isEnable, List<Integer> itemNums) {
		return supplierDao.find(systemBookCode, branchNum, supplierCategory, queryName, isCenterShared, isEnable,
				itemNums);
	}

	@Override
	public List<Supplier> find(String systemBookCode, List<Integer> branchNums, String supplierCategory, String queryName, Boolean isCenterShared, Boolean isEnable) {
		return supplierDao.find(systemBookCode, branchNums, supplierCategory, queryName, isCenterShared, isEnable, null);
	}

	@Override
	public List<Supplier> findAll(String systemBookCode) {
		return supplierDao.findAll(systemBookCode);
	}

}