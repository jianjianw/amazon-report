package com.nhsoft.module.report.dao;



import com.nhsoft.module.report.model.ItemExtendAttribute;

import java.util.List;

public interface ItemExtendAttributeDao {


	public List<ItemExtendAttribute> find(String systemBookCode);
	
	public List<ItemExtendAttribute> findByItem(Integer itemNum);
	
	public List<ItemExtendAttribute> findByItems(List<Integer> itemNums);



}
