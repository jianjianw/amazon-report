package com.nhsoft.module.report.dao;



import com.nhsoft.module.report.model.PosClient;

import java.util.List;

public interface PosClientDao {
	public List<PosClient> findAll(String systemBookCode, Integer branchNum);
}
