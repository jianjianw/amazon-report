package com.nhsoft.module.report.dao;


import com.nhsoft.module.report.model.Storehouse;

import java.util.List;

public interface StorehouseDao {

	public List<Storehouse> findByBranchs(String systemBookCode, List<Integer> branchNums);

	public List<Storehouse> findByBranch(String systemBookCode, Integer branchNum);
}