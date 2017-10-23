package com.nhsoft.module.report.dao;


import com.nhsoft.module.report.model.BranchResource;

public interface BranchResourceDao {

    public BranchResource read(String systemBookCode, Integer branchNum, String name);
}
