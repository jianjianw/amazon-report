package com.nhsoft.module.report.rpc;

import com.nhsoft.module.report.dto.BranchBizdayConsumeSummary;
import com.nhsoft.module.report.dto.BranchConsumeReport;
import com.nhsoft.module.report.dto.CardConsumeDTO;
import com.nhsoft.module.report.queryBuilder.CardReportQuery;

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


    /**
     * 根据分店、营业日汇总金额
     * @param systemBookCode
     * @param branchNums
     * @param dateFrom
     * @param dateTo
     * @param cardUserCardType
     * @return
     */
    public List<BranchBizdayConsumeSummary> findBranchBizdaySum(String systemBookCode, List<Integer> branchNums, Date dateFrom,
                                                                Date dateTo, Integer cardUserCardType);


    public List<CardConsumeDTO> findByCardReportQuery(CardReportQuery cardReportQuery, int offset, int limit);


}
