package com.nhsoft.module.report.rpc;

import com.nhsoft.module.report.dto.BranchBizdayDepositSummary;
import com.nhsoft.module.report.dto.BranchDepositReport;

import java.util.Date;
import java.util.List;

public interface CardDepositRpc {

    /**
     * 按门店汇总收款金额 存款金额
     * @param systemBookCode
     * @param branchNums 分店列表
     * @param dateFrom 营业日起
     * @param dateTo 营业日止
     * @return
     */
    public List<BranchDepositReport> findBranchSum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);





}
