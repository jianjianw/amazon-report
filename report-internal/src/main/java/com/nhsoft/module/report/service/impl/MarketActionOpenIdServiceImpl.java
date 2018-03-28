package com.nhsoft.module.report.service.impl;

import com.nhsoft.module.report.dao.MarketActionOpenIdDao;
import com.nhsoft.module.report.model.SystemBook;
import com.nhsoft.module.report.service.MarketActionOpenIdService;
import com.nhsoft.module.report.util.AppConstants;
import com.nhsoft.report.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class MarketActionOpenIdServiceImpl implements MarketActionOpenIdService{


    @Autowired
    private MarketActionOpenIdDao marketActionOpenIdDao;
    @Override
    public BigDecimal findPayMoneyByBranch(String systemBookCode, Date dateFrom, Date dateTo) {
        return marketActionOpenIdDao.findPayMoneyByBranch(systemBookCode,dateFrom,dateTo);
    }

    @Override
    public List<Object[]> findPayMoneyByBranchBizday(String systemBookCode,Date dateFrom, Date dateTo) {
        return marketActionOpenIdDao.findPayMoneyByBranchBizday(systemBookCode,dateFrom,dateTo);
    }

}
