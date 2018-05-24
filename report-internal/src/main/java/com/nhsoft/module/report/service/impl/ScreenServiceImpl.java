package com.nhsoft.module.report.service.impl;

import com.nhsoft.module.report.dao.ScreenDao;
import com.nhsoft.module.report.dto.ScreenCategoryDTO;
import com.nhsoft.module.report.dto.ScreenItemSaleDTO;
import com.nhsoft.module.report.dto.ScreenPlatformSaleDTO;
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
}
