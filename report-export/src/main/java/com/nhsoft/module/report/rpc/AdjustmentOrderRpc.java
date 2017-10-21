package com.nhsoft.module.report.rpc;

import com.nhsoft.module.report.dto.AdjustmentCauseMoney;
import com.nhsoft.module.report.dto.CheckMoney;
import com.nhsoft.module.report.dto.LossMoneyReport;

import java.util.Date;
import java.util.List;

public interface AdjustmentOrderRpc {

    /**
     * 按分店查询报损金额
     * @param systemBookCode
     * @param branchNums 分店号
     * @param dateFrom 时间起
     * @param dateTo 时间止
     */
    public List<LossMoneyReport> findLossMoneyByBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);

    /**
     * 按分店查询盘损金额
     * @param systemBookCode
     * @param branchNums 分店号
     * @param dateFrom 时间起
     * @param dateTo 时间止
     * */
    public List<CheckMoney> findCheckMoneyByBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);

    /**
     *按分店查询损耗金额		试吃，去皮，报损，其它
     * @param systemBookCode
     * @param branchNums 分店号
     * @param dateFrom 时间起
     * @param dateTo 时间止
     * */
    public List<AdjustmentCauseMoney> findAdjustmentCauseMoneyByBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);


}
