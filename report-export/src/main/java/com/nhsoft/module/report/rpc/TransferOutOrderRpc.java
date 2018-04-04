package com.nhsoft.module.report.rpc;


import com.nhsoft.module.report.dto.TransferOutMoney;
import com.nhsoft.module.report.dto.TransferOutMoneyAndAmountDTO;
import com.nhsoft.module.report.dto.TransferOutMoneyDateDTO;
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


    /**
     * 按营业日 汇总金额
     * @param systemBookCode
     * @param centerBranchNum
     * @param branchNums
     * @param dateFrom 审核时间起
     * @param dateTo 审核时间止
     * @return
     */
    public List<TransferOutMoneyDateDTO> findDateSummary(String systemBookCode, Integer centerBranchNum,
                                                         List<Integer> branchNums, Date dateFrom, Date dateTo, String strDate);

    /**
     * 按营业日 汇总金额和调出数量
     * */
    public List<TransferOutMoneyAndAmountDTO> findMoneyAndAmountByBiz(String systemBookCode, Date dateFrom, Date dateTo,List<Integer> itemNums);

    /**
     * 按商品汇总 调出金额和数量
     */
    public List<TransterOutDTO> findMoneyAndAmountByItemNum(String systemBookCode,Integer branchNum,List<Integer> storehouseNums,Date dateFrom, Date dateTo,List<Integer> itemNums,String sortField);

}
