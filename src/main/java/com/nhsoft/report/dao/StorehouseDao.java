package com.nhsoft.report.dao;



import com.nhsoft.report.model.Storehouse;

import java.util.List;

public interface StorehouseDao {

	public List<Storehouse> findByBranchs(String systemBookCode, List<Integer> branchNums);
}