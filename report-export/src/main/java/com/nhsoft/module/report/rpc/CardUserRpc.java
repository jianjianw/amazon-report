package com.nhsoft.module.report.rpc;

import com.nhsoft.module.report.dto.BranchBizdayCardCountSummary;
import com.nhsoft.module.report.dto.BranchBizdayCardReturnSummary;
import com.nhsoft.module.report.dto.CardUserCount;
import com.nhsoft.module.report.dto.CardUserDTO;
import com.nhsoft.module.report.queryBuilder.CardReportQuery;

import java.util.Date;
import java.util.List;

public interface CardUserRpc  {

    /**
     * 按分店查询新增会员数
     * @param systemBookCode
     * @param branchNums 分店号
     * @param dateFrom 时间起
     * @param dateTo 时间止
     * */
    public List<CardUserCount> findCardUserCountByBranch(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo);

    /**
     * 按分店、营业日查找发卡数
     * @param systemBookCode
     * @param branchNums
     * @param dateFrom
     * @param dateTo
     * @param cardUserCardType
     * @return
     */
    public List<BranchBizdayCardCountSummary> findCardCountByBranchBizday(String systemBookCode, List<Integer> branchNums, Date dateFrom,
                                                                          Date dateTo, Integer cardUserCardType);

    /**
     * 按分店、营业日查找退卡数
     * @param systemBookCode
     * @param branchNums
     * @param dateFrom
     * @param dateTo
     * @param cardUserCardType
     * @return
     */
    public List<BranchBizdayCardReturnSummary> findRevokeCardCountByBranchBizday(String systemBookCode, List<Integer> branchNums,
                                                                                 Date dateFrom, Date dateTo, Integer cardUserCardType);


    public List<CardUserDTO> findByCardReportQuery(String systemBookCode, CardReportQuery cardReportQuery, int offset, int limit);

}
