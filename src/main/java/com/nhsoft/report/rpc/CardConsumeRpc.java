package com.nhsoft.report.rpc;

import com.nhsoft.report.dto.BranchConsumeReport;

import java.util.Date;
import java.util.List;

public interface CardConsumeRpc {
    /**
     * 按门店汇总消费金额
     * @param systemBookCode
     * @param branchNums 分店列表
     * @param dateFrom 营业日起
     * @param dateTo 营业日止
     * @return
     */
    public List<BranchConsumeReport> findBranchSum(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);

}
