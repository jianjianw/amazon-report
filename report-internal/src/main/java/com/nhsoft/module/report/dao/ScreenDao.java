package com.nhsoft.module.report.dao;

import com.nhsoft.module.report.dto.ScreenCategoryDTO;
import com.nhsoft.module.report.dto.ScreenItemSaleDTO;
import com.nhsoft.module.report.dto.ScreenMerchantDTO;
import com.nhsoft.module.report.dto.ScreenPlatformSaleDTO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ScreenDao {

    List<ScreenItemSaleDTO> findItemSales(String systemBookCode, Integer branchNum);

    List<ScreenCategoryDTO> findCategorySales(String systemBookCode, Integer branchNum);

    List<Object[]> findMerchantSales(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo);

    Integer readCardUsers(String systemBookCode, Integer branchNum, Boolean isNew);

    Integer[] readOrderCounts(String systemBookCode, Integer branchNum);

    BigDecimal[] readOrderMoney(String systemBookCode, Integer branchNum);

    List<ScreenPlatformSaleDTO> findPlatformSales(String systemBookCode, Integer branchNum);


}
