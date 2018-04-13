package com.nhsoft.module.report.service;


import com.nhsoft.module.report.model.ItemFlagDetail;
import com.nhsoft.module.report.model.PosItemFlag;

import java.util.List;

public interface PosItemFlagService {


	public List<PosItemFlag> find(String systemBookCode, String itemFlagType);

	public List<ItemFlagDetail> findDetails(int itemFlagNum);

	public PosItemFlag readWithoutDetails(Integer itemFlagNum);
	
	public List<Integer> findItemNums(int itemFlagNum);
	
}
