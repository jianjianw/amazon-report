package com.nhsoft.module.report.rpc;

import com.nhsoft.module.report.dto.InventoryDTO;
import com.nhsoft.module.report.dto.ItemInventoryDTO;

import java.util.List;

public interface InventoryRpc {

    /**
     * 按商品主键汇总配送仓库库存数量 金额
     * @param systemBookCode
     * @param branchNum 分店号
     * @param itemNums 商品主键列表
     * @return
     */
    public List<InventoryDTO> findCenterStore(String systemBookCode, Integer branchNum, List<Integer> itemNums);


    /**
     * 按商品主键汇总库存数量 金额
     * @param systemBookCode
     * @param branchNums 分店号
     * @param itemNums 商品主键列表
     * @param storehouseNum
     * @return
     */
    public List<ItemInventoryDTO> findItemAmount(String systemBookCode, List<Integer> branchNums, List<Integer> itemNums, Integer storehouseNum);

}
