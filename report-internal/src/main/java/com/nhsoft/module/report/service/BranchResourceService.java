package com.nhsoft.module.report.service;

import com.nhsoft.module.report.dto.CustomerModelParam;

import java.util.List;

public interface BranchResourceService {

    /**
     * 查询自定义模型参数
     * @param systemBookCode
     * @param branchNum
     * @return
     */
    public List<CustomerModelParam> findCustomerModelParams(String systemBookCode, Integer branchNum);
}
