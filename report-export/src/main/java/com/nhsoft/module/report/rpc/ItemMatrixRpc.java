package com.nhsoft.module.report.rpc;

import com.nhsoft.module.report.dto.ItemMatrixDTO;

import java.util.List;

public interface ItemMatrixRpc {

	/**
	 * 读取
	 * @param itemNum 商品主键号
	 * @param itemMatrixNum 多特性编号
	 * @return
	 */
	public ItemMatrixDTO read(String systemBookCode, Integer itemNum, Integer itemMatrixNum);

	/**
	 * 查询
	 * @param itemNum 商品主键号
	 * @return
	 */
	public List<ItemMatrixDTO> findByItem(String systemBookCode, Integer itemNum);

}
