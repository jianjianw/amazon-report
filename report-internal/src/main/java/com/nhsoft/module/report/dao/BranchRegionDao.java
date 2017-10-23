package com.nhsoft.module.report.dao;



import com.nhsoft.module.report.model.BranchRegion;

import java.util.List;

public interface BranchRegionDao {

	public List<BranchRegion> findAll(String systemBookCode);

}
