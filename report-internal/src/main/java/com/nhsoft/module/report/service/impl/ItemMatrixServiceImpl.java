package com.nhsoft.module.report.service.impl;

import com.nhsoft.module.report.dao.ItemMatrixDao;
import com.nhsoft.module.report.model.ItemMatrix;
import com.nhsoft.module.report.service.ItemMatrixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemMatrixServiceImpl implements ItemMatrixService {
	
	@Autowired
	private ItemMatrixDao itemMatrixDao;

	@Override
	public ItemMatrix read(Integer itemNum, Integer itemMatrixNum) {
		return itemMatrixDao.read(itemNum, itemMatrixNum);
	}

	@Override
	public List<ItemMatrix> findByItem(Integer itemNum) {	
		return itemMatrixDao.findByItemNum(itemNum);
	}
}
