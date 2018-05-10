package com.nhsoft.module.report.service.impl;


import com.nhsoft.module.report.dao.InventoryDao;
import com.nhsoft.module.report.model.Inventory;
import com.nhsoft.module.report.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class InventoryServiceImpl implements InventoryService {
	@Autowired
	private InventoryDao inventoryDao;

	@Override
	public List<Object[]> findItemAmount(String systemBookCode, List<Integer> branchNums, List<Integer> itemNums, Integer storehouseNum) {
		return inventoryDao.findItemAmount(systemBookCode, branchNums, itemNums, storehouseNum);
	}


	@Override
	public List<Object[]> findBranchItemSummary(String systemBookCode, List<Integer> branchNums, List<Integer> itemNums) {
		return inventoryDao.findBranchItemSummary(systemBookCode, branchNums, null, itemNums);
	}
	
	@Override
	public List<Object[]> findCenterStore(String systemBookCode, Integer branchNum, List<Integer> itemNums) {
		return inventoryDao.findCenterStore(systemBookCode, branchNum, itemNums);
	}

	@Override
	public List<Inventory> findByItemAndBranch(String systemBookCode, Integer branchNum, List<Integer> itemNums, Boolean centerFlag) {
		List<Inventory> inventoryList = inventoryDao.findByItemAndBranch(systemBookCode, branchNum, itemNums, centerFlag);
		for (int i = 0; i <inventoryList.size() ; i++) {
			Inventory inventory = inventoryList.get(i);
			inventory.getInventoryMatrixs().size();
			inventory.getInventoryBatchDetails().size();
			inventory.getInventoryLnDetails().size();
		}
		return inventoryList;
	}

	@Override
	public List<Inventory> findByStorehouseNum(Integer storehouseNum, List<Integer> itemNums) {
		List<Inventory> inventoryList = inventoryDao.findByStorehouseNum(storehouseNum, itemNums);
		for (int i = 0; i <inventoryList.size() ; i++) {
			Inventory inventory = inventoryList.get(i);
			inventory.getInventoryMatrixs().size();
			inventory.getInventoryBatchDetails().size();
			inventory.getInventoryLnDetails().size();
		}
		return inventoryList;
	}


}