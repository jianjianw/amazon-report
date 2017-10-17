package com.nhsoft.report.rpc;

import com.nhsoft.report.dto.BranchMoneyReport;

import java.util.Date;
import java.util.List;

public interface PosOrderRpc  {

    /**
     * 按分店查询营业额,客单量,毛利
     * @param systemBookCode
     * @param branchNums
     * @param queryBy 统计类型 按营业额(AppConstants.BUSINESS_TREND_PAYMENT) or 按储值额 or 按发卡量
     * @param dateFrom
     * @param dateTo
     * @param isMember 是否会员
     * @return
     *
     */
    public List<BranchMoneyReport> findMoneyByBranch(String systemBookCode, List<Integer> branchNums, String queryBy, Date dateFrom, Date dateTo, Boolean isMember);

}
