package com.nhsoft.module.report.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface MarketActionOpenIdDao {


    /**
     * 根据分店汇总线上购券
     */
    public BigDecimal findPayMoneyByBranch(String systemBookCode, Date dateFrom, Date dateTo);


    /**
     * 根据营业日汇总线上购券
     * */
    public List<Object[]> findPayMoneyByBranchBizday(String systemBookCode,Date dateFrom, Date dateTo);


}
