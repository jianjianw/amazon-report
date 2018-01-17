package com.nhsoft.module.report.rpc;

import com.nhsoft.module.report.dto.ReceiveOrderInfoDTO;

import java.util.Date;
import java.util.List;

public interface ReceiveOrderRpc {

    /**
     * 按商品汇总收货数量 金额
     *
     * @param systemBookCode
     * @param branchNums     分店
     * @param dateFrom       审核时间起
     * @param dateTo         审核时间止
     * @param itemNums       商品主键列表
     * @return
     */
    public List<ReceiveOrderInfoDTO> findItemSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, List<Integer> itemNums);

}
