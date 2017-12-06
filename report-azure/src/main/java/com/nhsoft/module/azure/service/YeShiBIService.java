package com.nhsoft.module.azure.service;


import com.nhsoft.module.azure.model.*;

import java.util.List;

public interface YeShiBIService {

    /**
     *商品日销售汇总
     * */
    public void insertItemDaily(List<ItemDaily> itemDailys);

    /**
     *商品日时段销售汇总
     * */
    public void insertItemDailyDetail(List<ItemDailyDetail> itemDailyDetails);

    /**
     *商品纬度
     * */
    public void insertPosItemLat(List<PosItemLat> posItemLats);

    /**
     *分店纬度
     **/
    public void insertBranchLat(List<BranchLat> branchLats);

    /**
     * 分店表
     * */
    public void insertBranch(List<Branch> branchs);

    /**
     *分店日销售汇总表
     * */
    public void insertBranchDaily(List<BranchDaily> branchDailys);
}
