package com.nhsoft.module.report.service.impl;

import com.nhsoft.module.report.dao.BranchItemRecoredDao;
import com.nhsoft.module.report.service.BranchItemRecoredService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BranchItemRecoredServiceImpl implements BranchItemRecoredService {

    @Autowired
    private BranchItemRecoredDao branchItemRecoredDao;


    @Override
    public List<Object[]> findItemAuditDate(String systemBookCode, Integer branchNum, Integer storehouseNum, List<Integer> itemNums, List<String> branchItemRecoredTypes) {
        return branchItemRecoredDao.findItemAuditDate(systemBookCode,branchNum,storehouseNum,itemNums,branchItemRecoredTypes);
    }

}
