package com.nhsoft.report.dao;

import com.nhsoft.report.model.BranchResource;

public interface BranchResourceDao {

    public BranchResource read(String systemBookCode, Integer branchNum, String name);
}
