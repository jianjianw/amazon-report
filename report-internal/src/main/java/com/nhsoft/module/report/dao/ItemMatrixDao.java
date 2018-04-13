package com.nhsoft.module.report.dao;


import com.nhsoft.module.report.model.ItemMatrix;

import java.util.List;

public interface ItemMatrixDao {
	
	public ItemMatrix read(Integer itemNum, Integer itemMatrixNum);

	public List<ItemMatrix> findByItemNum(Integer itemNum);

}
