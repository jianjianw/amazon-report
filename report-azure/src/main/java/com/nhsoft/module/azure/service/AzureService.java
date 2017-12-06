package com.nhsoft.module.azure.service;


import com.nhsoft.module.azure.model.*;

import java.util.List;

public interface AzureService {

    /**
     *商品日销售汇总
     * */
    public void insertItemDaily(String systemBookCode, List<ItemDaily> itemDailys);

    /**
     *商品日时段销售汇总
     * */
    public void insertItemDailyDetail(String systemBookCode, List<ItemDailyDetail> itemDailyDetails);

    /**
     *商品纬度
     * */
    public void insertPosItemLat(String systemBookCode, List<PosItemLat> posItemLats);

    /**
     *分店纬度
     **/
    public void insertBranchLat(String systemBookCode, List<BranchLat> branchLats);

    /**
     * 分店表
     * */
    public void insertBranch(String systemBookCode, List<Branch> branchs);

    /**
     *分店日销售汇总表
     * */
    public void insertBranchDaily(String systemBookCode, List<BranchDaily> branchDailys);
}
