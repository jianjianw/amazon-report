package com.nhsoft.module.report.service;

import com.nhsoft.module.report.model.ItemMatrix;

import java.util.List;

/**
 * 多特性
 * @author nhsoft
 *
 */
public interface ItemMatrixService {

	/**
	 * 读取
	 * @param itemNum 商品主键号
	 * @param itemMatrixNum 多特性编号
	 * @return
	 */
	public ItemMatrix read(Integer itemNum, Integer itemMatrixNum);
	
	/**
	 * 查询
	 * @param itemNum 商品主键号
	 * @return
	 */
	public List<ItemMatrix> findByItem(Integer itemNum);
}
