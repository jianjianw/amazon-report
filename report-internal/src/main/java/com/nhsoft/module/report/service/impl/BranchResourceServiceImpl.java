package com.nhsoft.module.report.service.impl;

import com.nhsoft.module.report.dao.BranchResourceDao;
import com.nhsoft.module.report.dto.CustomerModelParam;
import com.nhsoft.module.report.model.BranchResource;
import com.nhsoft.module.report.param.ExpressCompany;
import com.nhsoft.module.report.service.BranchResourceService;
import com.nhsoft.module.report.util.AppConstants;
import com.nhsoft.report.utils.RedisUtil;
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
    
    public List<ExpressCompany> findExpressCompanies(String systemBookCode,
                                                     Integer branchNum) {
        BranchResource branchResource = branchResourceDao.read(systemBookCode, branchNum, AppConstants.BRANCH_EXPRESEE_COMPANY);
        if(branchResource == null){
            return new ArrayList<ExpressCompany>();
        }
        List<ExpressCompany> list = ExpressCompany.readFromXml(branchResource.getBranchResourceParam());
        return list;
    }
    
    @Override
    public List<ExpressCompany> findExpressCompaniesInCache(String systemBookCode, Integer branchNum) {
        String key = AppConstants.BRANCH_EXPRESEE_COMPANY + systemBookCode + "|" + branchNum;
    
        Object object = RedisUtil.get(AppConstants.REDIS_PRE_BRANCH_RESOURCE + key);
        if(object == null){
            List<ExpressCompany> expressCompanies = findExpressCompanies(systemBookCode, branchNum);
            RedisUtil.put(key, expressCompanies, AppConstants.REDIS_CACHE_LIVE_SECOND);
            return expressCompanies;
        } else {
            return (List<ExpressCompany>) object;
        }
       
    }
}
