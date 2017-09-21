package com.nhsoft.report.dao;


import com.nhsoft.report.model.PosClient;

import java.util.List;

public interface PosClientDao {
	public List<PosClient> findAll(String systemBookCode, Integer branchNum);
}
