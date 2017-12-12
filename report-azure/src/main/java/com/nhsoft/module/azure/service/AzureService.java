package com.nhsoft.module.azure.service;


import com.nhsoft.module.azure.model.*;

import java.util.Date;
import java.util.List;

public interface AzureService {

    /**
     *商品日销售汇总
     * */
    public void batchSaveItemDailies(String systemBookCode, List<ItemDaily> itemDailys);

    /**
     *商品日时段销售汇总
     * */
    public void batchSaveItemDailyDetails(String systemBookCode, List<ItemDailyDetail> itemDailyDetails);

    /**
     *商品纬度
     * */
    public void batchSavePosItemLats(String systemBookCode, List<PosItemLat> posItemLats);

    /**
     *分店纬度
     **/
    public void batchSaveBranchLats(String systemBookCode, List<BranchLat> branchLats);

    /**
     * 分店表
     * */
    public void batchSaveBranchs(String systemBookCode, List<Branch> branches);

    /**
     *分店日销售汇总表
     * */
    public void batchSaveBranchDailies(String systemBookCode,List<BranchDaily> branchDailys);

    /**
     *删除分店日销售汇总
     * */
    public void batchDeleteBranchDailies(String systemBookCode, Date dateFrom, Date dateTo);

    /**
     *删除商品日分段汇总
     * */
    public void batchDeleteItemDetailDailies(String systemBookCode, Date dateFrom,Date dateTo);
}
