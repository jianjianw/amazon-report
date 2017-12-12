package com.nhsoft.module.azure.service.impl;

import com.nhsoft.module.azure.dao.AzureDao;
import com.nhsoft.module.azure.model.*;
import com.nhsoft.module.azure.service.AzureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class AzureServiceImpl implements AzureService {

    @Autowired
    private AzureDao azureDao;

    public void batchSaveItemDailies(String systemBookCode, List<ItemDaily> itemDailys) {
        azureDao.batchSaveItemDailies(systemBookCode,itemDailys);
    }

    public void batchSaveItemDailyDetails(String systemBookCode, List<ItemDailyDetail> itemDailyDetails) {
        azureDao.batchSaveItemDailyDetails(systemBookCode,itemDailyDetails);
    }

    public void batchSavePosItemLats(String systemBookCode, List<PosItemLat> posItemLats) {
        azureDao.batchSavePosItemLats(systemBookCode,posItemLats);
    }

    public void batchSaveBranchLats(String systemBookCode, List<BranchLat> branchLats) {
        azureDao.batchSaveBranchLats(systemBookCode,branchLats);
    }

    public void batchSaveBranchs(String systemBookCode, List<Branch> branchs) {
        azureDao.batchSaveBranchs(systemBookCode,branchs);
    }

    public void batchSaveBranchDailies(String systemBookCode, List<BranchDaily> branchDailys) {
        azureDao.batchSaveBranchDailies(branchDailys);
    }

    public void batchDeleteBranchDailies(String systemBookCode, Date dateFrom, Date dateTo) {
        azureDao.batchDeleteBranchDailies(systemBookCode,dateFrom,dateTo);
    }

    public void batchDeleteItemDetailDailies(String systemBookCode, Date dateFrom, Date dateTo) {
        azureDao.batchDeleteItemDetailDailies(systemBookCode,dateFrom,dateTo);
    }
}
