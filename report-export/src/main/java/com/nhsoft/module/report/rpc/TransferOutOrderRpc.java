package com.nhsoft.module.report.rpc;


import com.nhsoft.module.report.dto.TransferOutMoney;
import com.nhsoft.module.report.dto.TransterOutDTO;

import java.util.Date;
import java.util.List;

public interface TransferOutOrderRpc {

    /***
     * 按分店查询配送额
     * @param systemBookCode
     * @param branchNums 分店号
     * @param dateFrom 时间起
     * @param dateTo 时间止
     */
    public List<TransferOutMoney> findMoneyBranchSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);

    /***
     * 按营业日查询配送额
     * @param systemBookCode
     * @param branchNums 分店号
     * @param dateFrom 时间起
     * @param dateTo 时间止
     */
    public List<TransferOutMoney> findMoneyBizdaySummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);

    /***
     * 按月份查询配送额
     * @param systemBookCode
     * @param branchNums 分店号
     * @param dateFrom 时间起
     * @param dateTo 时间止
     */
    public List<TransferOutMoney> findMoneyBymonthSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);


    /**
     * 按商品主键汇总基本数量 金额 常用数量
     * @param systemBookCode
     * @param outBranchNums
     * @param branchNums
     * @param dateFrom
     * @param dateTo
     * @param itemNums
     * @return
     */
    public List<TransterOutDTO> findItemSummary(String systemBookCode, List<Integer> outBranchNums, List<Integer> branchNums,
                                                Date dateFrom, Date dateTo, List<Integer> itemNums);

}
