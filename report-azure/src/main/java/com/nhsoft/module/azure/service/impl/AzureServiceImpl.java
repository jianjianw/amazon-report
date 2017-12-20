package com.nhsoft.module.azure.service.impl;

import com.nhsoft.module.azure.dao.AzureDao;
import com.nhsoft.module.azure.model.*;
import com.nhsoft.module.azure.service.AzureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AzureServiceImpl implements AzureService {

    @Autowired
    private AzureDao azureDao;

    public void batchSaveItemDailies(String systemBookCode, List<ItemDaily> itemDailys) {
        azureDao.batchSaveItemDailies(systemBookCode,itemDailys);
    }

    public void batchSaveItemDailyDetails(String systemBookCode, List<ItemDailyDetail> itemDailyDetails,Date dateFrom,Date dateTo) {
        azureDao.batchSaveItemDailyDetails(systemBookCode,itemDailyDetails,dateFrom,dateTo);
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

    public void batchSaveBranchDailies(String systemBookCode, List<BranchDaily> branchDailys,Date dateFrom,Date dateTo) {
        azureDao.batchSaveBranchDailies(systemBookCode,branchDailys,dateFrom,dateTo);
    }

    public void batchDeleteBranchDailies(String systemBookCode, Date dateFrom, Date dateTo) {
        azureDao.batchDeleteBranchDailies(systemBookCode,dateFrom,dateTo);
    }

    public void batchDeleteItemDetailDailies(String systemBookCode, Date dateFrom, Date dateTo) {
        azureDao.batchDeleteItemDetailDailies(systemBookCode,dateFrom,dateTo);
    }

    public List<Integer> findPosItemNums(String systemBookCode) {
        List<Object> objects = azureDao.findPosItemNums(systemBookCode);
        List<Integer> list = new ArrayList<Integer>();
        if(objects.isEmpty()){
            return list;
        }
        for (int i = 0; i <objects.size() ; i++) {
            Integer integer = (Integer)objects.get(i);
            list.add(integer);
        }
        return list;
    }

    public void batchSaveBranchDailyDirects(String systemBookCode, List<BranchDailyDirect> branchDailyDirects,Date dateFrom,Date dateTo) {
        azureDao.batchSaveBranchDailyDirects(systemBookCode,branchDailyDirects,dateFrom,dateTo);
    }

    public void batchDeleteBranchDailyDirects(String systemBookCode, Date dateFrom, Date dateTo) {
        azureDao.batchDeleteBranchDailyDirects(systemBookCode,dateFrom,dateTo);
    }

    public void batchSaveBizdays(String systemBookCode, List<Bizday> bizdays) {
        azureDao.batchSaveBizdays(systemBookCode,bizdays);
    }

    public void batchSaveItemSaleDailies(String systemBookCode, List<ItemSaleDaily> itemSaleDailies,Date dateFrom, Date dateTo) {
        azureDao.batchSaveItemSaleDailies(systemBookCode,itemSaleDailies,dateFrom,dateTo);
    }

    public void batchDeleteItemSaleDailies(String systemBookCode,Date dateFrom, Date dateTo) {
        azureDao.batchDeleteItemSaleDailies(systemBookCode,dateFrom,dateTo);
    }

    public void batchSaveItemLossDailies(String systemBookCode, List<ItemLossDaily> itemLossDailies, Date dateFrom, Date dateTo) {
        azureDao.batchSaveItemLossDailies(systemBookCode,itemLossDailies,dateFrom,dateTo);
    }

    public void batchDeleteItemLossDailies(String systemBookCode, Date dateFrom, Date dateTo) {
        azureDao.batchDeleteItemLossDailies(systemBookCode,dateFrom,dateTo);
    }

    public void batchSaveCardDailies(String systemBookCode, List<CardDaily> CardDailies, Date dateFrom, Date dateTo) {
        azureDao.batchSaveCardDailies(systemBookCode,CardDailies,dateFrom,dateTo);
    }


    public void batchDeleteCardDailies(String systemBookCode, Date dateFrom, Date dateTo) {
        azureDao.batchDeleteCardDailies(systemBookCode,dateFrom,dateTo);
    }


}
