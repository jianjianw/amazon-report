package com.nhsoft.report.service.impl;


import com.nhsoft.report.dao.InventoryDao;
import com.nhsoft.report.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class InventoryServiceImpl implements InventoryService {
	@Autowired
	private InventoryDao inventoryDao;

	@Override
	public List<Object[]> findItemAmount(String systemBookCode, List<Integer> branchNums, List<Integer> itemNums) {
		return inventoryDao.findItemAmount(systemBookCode, branchNums, itemNums);
	}


	@Override
	public List<Object[]> findBranchItemSummary(String systemBookCode, List<Integer> branchNums) {
		return inventoryDao.findBranchItemSummary(systemBookCode, branchNums, null);
	}


}