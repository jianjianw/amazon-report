package com.nhsoft.module.azure.service.impl;

import com.nhsoft.module.azure.dao.YeShiBIDao;
import com.nhsoft.module.azure.model.*;
import com.nhsoft.module.azure.service.YeShiBIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YeShiBIServiceImpl implements YeShiBIService {

    @Autowired
    private YeShiBIDao yeShiBIDao;

    public void insertItemDaily(List<ItemDaily> itemDailys) {
        yeShiBIDao.insertItemDaily(itemDailys);
    }

    public void insertItemDailyDetail(List<ItemDailyDetail> itemDailyDetails) {
        yeShiBIDao.insertItemDailyDetail(itemDailyDetails);
    }

    public void insertPosItemLat(List<PosItemLat> posItemLats) {
        yeShiBIDao.insertPosItemLat(posItemLats);
    }

    public void insertBranchLat(List<BranchLat> branchLats) {
        yeShiBIDao.insertBranchLat(branchLats);
    }

    public void insertBranch(List<Branch> branchs) {
        yeShiBIDao.insertBranch(branchs);
    }


    public void insertBranchDaily(List<BranchDaily> branchDailys) {
        yeShiBIDao.insertBranchDaily(branchDailys);
    }
}
