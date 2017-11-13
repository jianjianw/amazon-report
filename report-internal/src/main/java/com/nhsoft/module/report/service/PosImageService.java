package com.nhsoft.module.report.service;


import com.nhsoft.module.report.model.PosImage;

import java.util.List;

/**
 * 商品图片
 * @author nhsoft
 *
 */
public interface PosImageService {
	
	/**
	 * 查询 不带图片内容
	 * @param systemBookCode
	 * @param itemNums 商品主键列表
	 * @return
	 */
	public List<PosImage> find(String systemBookCode, List<Integer> itemNums);
	
}
