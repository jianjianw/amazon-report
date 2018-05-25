package com.nhsoft.module.report.service.impl;

import com.nhsoft.module.report.dao.ScreenDao;
import com.nhsoft.module.report.dto.*;
import com.nhsoft.module.report.service.ScreenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ScreenServiceImpl implements ScreenService {

    @Autowired
    private ScreenDao screenDao;

    @Override
    public List<ScreenItemSaleDTO> findItemSales(String systemBookCode, Integer branchNum) {
        return screenDao.findItemSales(systemBookCode, branchNum);
    }

    @Override
    public List<ScreenItemSaleDTO> findItemSaleCounts(String systemBookCode, Integer branchNum) {
        return screenDao.findItemSaleCounts(systemBookCode, branchNum);
    }

    @Override
    public List<ScreenCategoryDTO> findCategorySales(String systemBookCode, Integer branchNum) {
        return screenDao.findCategorySales(systemBookCode, branchNum);
    }

    @Override
    public List<Object[]> findMerchantSales(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo) {
        return screenDao.findMerchantSales(systemBookCode, branchNum, dateFrom, dateTo);
    }

    @Override
    public Integer readCardUsers(String systemBookCode, Integer branchNum, Boolean isNew) {
        return screenDao.readCardUsers(systemBookCode, branchNum, isNew);
    }

    @Override
    public Integer[] readOrderCounts(String systemBookCode, Integer branchNum) {
        return screenDao.readOrderCounts(systemBookCode, branchNum);
    }

    @Override
    public BigDecimal[] readOrderMoney(String systemBookCode, Integer branchNum) {
        return screenDao.readOrderMoney(systemBookCode, branchNum);
    }

    @Override
    public List<ScreenPlatformSaleDTO> findPlatformSales(String systemBookCode, Integer branchNum) {
        return screenDao.findPlatformSales(systemBookCode, branchNum);
    }

    @Override
    public List<ScreenTraceDTO> findScreenTraces(String systemBookCode, Integer branchNum) {
        return screenDao.findScreenTraces(systemBookCode, branchNum);
    }

    @Override
    public List<ScreenItemDTO> findScreenItems(String systemBookCode, Integer branchNum) {
        return screenDao.findScreenItems(systemBookCode, branchNum);
    }

    @Override
    public List<ScreenMerchantStallInfoDTO> findScreenMerchantStallInfos(String systemBookCode, Integer branchNum) {
        return screenDao.findScreenMerchantStallInfos(systemBookCode, branchNum);
    }

    @Override
    public List<ScreenMerchantStallInfoDTO> findMerchantStallSaleMoney(String systemBookCode, Integer branchNum) {
        return screenDao.findMerchantStallSaleMoney(systemBookCode, branchNum);
    }
}
