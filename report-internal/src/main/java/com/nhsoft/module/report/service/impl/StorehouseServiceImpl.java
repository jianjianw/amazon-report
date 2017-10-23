package com.nhsoft.module.report.service.impl;



import com.nhsoft.module.report.dao.StorehouseDao;
import com.nhsoft.module.report.model.Storehouse;
import com.nhsoft.module.report.service.StorehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class StorehouseServiceImpl implements StorehouseService {
	@Autowired
	private StorehouseDao storehouseDao;

	@Override
	public List<Storehouse> findByBranchs(String systemBookCode, List<Integer> branchNums) {
		return storehouseDao.findByBranchs(systemBookCode, branchNums);
	}

	@Override
	public List<Storehouse> findByBranch(String systemBookCode,
										 Integer branchNum) {
		return storehouseDao.findByBranch(systemBookCode, branchNum);
	}

}