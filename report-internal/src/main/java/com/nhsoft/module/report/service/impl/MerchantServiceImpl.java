package com.nhsoft.module.report.service.impl;

import com.nhsoft.module.report.dao.MerchantDao;
import com.nhsoft.module.report.model.Merchant;
import com.nhsoft.module.report.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MerchantServiceImpl implements MerchantService {

    @Autowired
    private MerchantDao merchantDao;

    @Override
    public List<Merchant> find(String systemBookCode, Integer branchNum) {
        return merchantDao.find(systemBookCode, branchNum);
    }
}
