package com.nhsoft.module.azure.service.impl;

import com.nhsoft.module.azure.dao.AzureDao;
import com.nhsoft.module.azure.model.*;
import com.nhsoft.module.azure.service.AzureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AzureServiceImpl implements AzureService {

    @Autowired
    private AzureDao azureDao;

    public void insertItemDaily(String systemBookCode, List<ItemDaily> itemDailys) {
        azureDao.insertItemDaily(systemBookCode,itemDailys);
    }

    public void insertItemDailyDetail(String systemBookCode, List<ItemDailyDetail> itemDailyDetails) {
        azureDao.insertItemDailyDetail(systemBookCode,itemDailyDetails);
    }

    public void insertPosItemLat(String systemBookCode, List<PosItemLat> posItemLats) {
        azureDao.insertPosItemLat(systemBookCode,posItemLats);
    }

    public void insertBranchLat(String systemBookCode, List<BranchLat> branchLats) {
        azureDao.insertBranchLat(systemBookCode,branchLats);
    }

    public void insertBranch(String systemBookCode, List<Branch> branchs) {
        azureDao.insertBranch(systemBookCode,branchs);
    }

    public void insertBranchDaily(String systemBookCode, List<BranchDaily> branchDailys) {
        azureDao.insertBranchDaily(branchDailys);
    }
}
