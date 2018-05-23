package com.nhsoft.module.report.service.impl;

import com.nhsoft.module.report.dao.MerchantContractDao;
import com.nhsoft.module.report.model.MerchantContract;
import com.nhsoft.module.report.service.MerchantContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MerchantContractServiceImpl implements MerchantContractService {

    @Autowired
    private MerchantContractDao merchantContractDao;

    @Override
    public List<MerchantContract> findByMerchantNum(String systemBookCode, Integer branchNum, List<Integer> merchantNums) {
        return merchantContractDao.findByMerchantNum(systemBookCode, branchNum, merchantNums);
    }
}
