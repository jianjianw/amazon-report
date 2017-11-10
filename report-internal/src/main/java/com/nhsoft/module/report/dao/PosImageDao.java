package com.nhsoft.module.report.dao;


import com.nhsoft.module.report.model.PosImage;

import java.util.List;

public interface PosImageDao {

	
	
	public List<PosImage> find(String systemBookCode, List<Integer> itemNums);
	

}
