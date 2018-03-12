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

    @Override
    public BigDecimal findInCacheByBranch(String systemBookCode,Date dateFrom, Date dateTo) {

        String key = AppConstants.REDIS_PRE_BOOK_FUNCTION + systemBookCode;
        Object object = RedisUtil.get(key);
        if(object != null){
            Object obj = RedisUtil.hashGet(key, AppConstants.MARKETACTION_SELF_ACTION);
            if(obj == null){
                BigDecimal payMoney = marketActionOpenIdDao.findPayMoneyByBranch(systemBookCode, dateFrom, dateTo);
                RedisUtil.hashPut(key,AppConstants.MARKETACTION_SELF_ACTION,payMoney, AppConstants.CACHE_LIVE_THREE_MINUTES);
                return payMoney;
            }else {
                return (BigDecimal) obj;
            }
        }else{
            return marketActionOpenIdDao.findPayMoneyByBranch(systemBookCode, dateFrom, dateTo);
        }


    }

    @Override
    public List<Object[]> findInCacheByBranchBizday(String systemBookCode,Date dateFrom, Date dateTo) {
        String key = AppConstants.REDIS_PRE_BOOK_FUNCTION + systemBookCode;
        Object object = RedisUtil.get(key);
        if(object != null){
            Object obj = RedisUtil.hashGet(key, AppConstants.MARKETACTION_SELF_ACTION);
            if(obj == null){
                List<Object[]> payMoneyList = marketActionOpenIdDao.findPayMoneyByBranchBizday(systemBookCode, dateFrom, dateTo);
                RedisUtil.hashPut(key,AppConstants.MARKETACTION_SELF_ACTION,payMoneyList, AppConstants.CACHE_LIVE_THREE_MINUTES);
                return payMoneyList;
            }else {
                return (List<Object[]>) obj;
            }
        }else{
            return marketActionOpenIdDao.findPayMoneyByBranchBizday(systemBookCode, dateFrom, dateTo);
        }
    }

}
