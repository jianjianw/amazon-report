package com.nhsoft.report.service.impl;


import com.nhsoft.report.dao.StorehouseDao;
import com.nhsoft.report.model.Storehouse;
import com.nhsoft.report.service.StorehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class StorehouseServiceImpl implements StorehouseService {
	@Autowired
	private StorehouseDao storehouseDao;

	@Override
	public List<Storehouse> findByBranchs(String systemBookCode, List<Integer> branchNums) {
		return storehouseDao.findByBranchs(systemBookCode, branchNums);
	}

}