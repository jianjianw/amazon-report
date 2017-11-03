package com.nhsoft.module.report.rpc;

import com.nhsoft.module.report.dto.SaleMoneyGoals;

import java.util.Date;
import java.util.List;

public interface BranchTransferGoalsRpc {

    /**
     * 按分店查询营业额目标
     * @param systemBookCode
     * @param branchNums 分店号
     * @param dateFrom 时间起
     * @param dateTo 时间止
     * @param dateType 时间类型  年 AppConstants.BUSINESS_DATE_SOME_MONTH ，月，周，日
     */
    public List<SaleMoneyGoals> findSaleMoneyGoalsByBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String dateType);

    /**
     * 按时间（年，月，日）汇总营业额目标
     * @param systemBookCode
     * @param branchNums 分店号
     * @param dateFrom 时间起
     * @param dateTo 时间止
     * @param dateType 时间类型  年 AppConstants.BUSINESS_DATE_SOME_MONTH ，月，周，日
     */
    public List<SaleMoneyGoals> findSaleMoneyGoalsByDate(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo, String dateType);
}
