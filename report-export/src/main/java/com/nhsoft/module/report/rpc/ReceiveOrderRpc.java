package com.nhsoft.module.report.rpc;

import com.nhsoft.module.report.dto.MonthPurchaseDTO;
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


    /**
     * 查询月采购汇总
     * @param systemBookCode
     * @param branchNum
     * @param dateFrom
     * @param dateTo
     * @return
     */
    public List<MonthPurchaseDTO> findPurchaseMonth(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo, String dateType);

    /**
     * 查询月采购汇总
     * @param systemBookCode
     * @param branchNum
     * @param dateFrom
     * @param dateTo
       @param dateType  收获期日 or 审核日期
     * @param strDate 按时间汇总   按日 or  按月
     * @return
     */
    public List<MonthPurchaseDTO> findPurchaseMonth(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo,String dateType,String strDate);

}
