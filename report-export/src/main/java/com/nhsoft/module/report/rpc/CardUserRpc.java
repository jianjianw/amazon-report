package com.nhsoft.module.report.rpc;

import com.nhsoft.module.report.dto.CardUserCount;

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
}
