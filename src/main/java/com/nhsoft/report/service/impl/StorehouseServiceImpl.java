package com.nhsoft.report.service.impl;


import com.nhsoft.report.dao.StorehouseDao;
import com.nhsoft.report.model.Storehouse;
import com.nhsoft.report.service.StorehouseService;

import java.util.ArrayList;
import java.util.List;

public class StorehouseServiceImpl implements StorehouseService {

	private StorehouseDao storehouseDao;

	@Override
	public List<Storehouse> findByBranchs(String systemBookCode, List<Integer> branchNums) {
		return storehouseDao.findByBranchs(systemBookCode, branchNums);
	}

}