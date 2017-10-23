package com.nhsoft.module.report.service.impl;


import com.nhsoft.module.report.dao.StoreItemSupplierDao;
import com.nhsoft.module.report.model.StoreItemSupplier;
import com.nhsoft.module.report.service.StoreItemSupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class StoreItemSupplierServiceImpl implements StoreItemSupplierService {

	@Autowired
	private StoreItemSupplierDao storeItemSupplierDao;

	@Override
	public List<StoreItemSupplier> findDefaults(String systemBookCode, List<Integer> branchNums, List<Integer> itemNums) {
		return storeItemSupplierDao.findDefaults(systemBookCode, branchNums, itemNums);
	}

}
