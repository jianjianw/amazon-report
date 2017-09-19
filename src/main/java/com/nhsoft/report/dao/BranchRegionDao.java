package com.nhsoft.report.dao;


import com.nhsoft.report.model.BranchRegion;

import java.util.List;

public interface BranchRegionDao {

	public List<BranchRegion> findAll(String systemBookCode);

}
