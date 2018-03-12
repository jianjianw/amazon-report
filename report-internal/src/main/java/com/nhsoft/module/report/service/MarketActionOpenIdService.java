package com.nhsoft.module.report.service;

import com.nhsoft.module.report.model.SystemBook;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface MarketActionOpenIdService {

    /**
     * 根据分店汇总线上购券
     */
    public Object findPayMoneyByBranch(String systemBookCode, Date dateFrom, Date dateTo);


    /**
     * 根据营业日汇总线上购券
     * */
    public List<Object[]> findPayMoneyByBranchBizday(String systemBookCode,Date dateFrom, Date dateTo);


    public BigDecimal findInCacheByBranch(String systemBookCode, Date dateFrom, Date dateTo);


    public List<Object[]> findInCacheByBranchBizday(String systemBookCode,Date dateFrom, Date dateTo);

}
