package com.nhsoft.report.service.impl;

import com.nhsoft.amazon.shared.AppConstants;
import com.nhsoft.report.dao.BranchResourceDao;
import com.nhsoft.module.report.dto.CustomerModelParam;
import com.nhsoft.report.model.BranchResource;
import com.nhsoft.report.service.BranchResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class BranchResourceServiceImpl implements BranchResourceService {

    @Autowired
    private BranchResourceDao branchResourceDao;
    @Override
    public List<CustomerModelParam> findCustomerModelParams(
            String systemBookCode, Integer branchNum) {
        BranchResource branchResource = branchResourceDao.read(systemBookCode, branchNum, AppConstants.PARAM_ANALYTICS_MODEL);
        if(branchResource == null){
            return new ArrayList<CustomerModelParam>();
        }
        List<CustomerModelParam> list = CustomerModelParam.fromXml(branchResource.getBranchResourceParam());
        return list;
    }
}
