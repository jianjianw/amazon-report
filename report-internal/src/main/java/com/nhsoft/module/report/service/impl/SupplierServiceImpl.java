package com.nhsoft.module.report.service.impl;

import com.google.gson.Gson;
import com.nhsoft.module.report.dao.SupplierDao;
import com.nhsoft.module.report.model.Supplier;
import com.nhsoft.module.report.service.SupplierService;
import com.nhsoft.module.report.util.AppConstants;
import com.nhsoft.module.report.util.AppUtil;
import com.nhsoft.report.utils.BaseManager;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
@Service
@SuppressWarnings("unchecked")
public class SupplierServiceImpl extends BaseManager implements SupplierService {

	@Autowired
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

	@Override
	public List<Supplier> findInCache(String systemBookCode) {


		String key = AppConstants.CACHE_NAME_SUPPLIER + systemBookCode;
		Element element = getElementFromCache(key);
		if (element == null) {
			List<Supplier> suppliers = supplierDao.findAll(systemBookCode);
			element = new Element(AppConstants.CACHE_NAME_SUPPLIER + systemBookCode, suppliers);
			element.setTimeToLive(AppConstants.CACHE_LIVE_SECOND);
			putElementToCache(element);
		}

		List<Supplier> suppliers = (List<Supplier>) element.getObjectValue();
		return suppliers;

	}
}