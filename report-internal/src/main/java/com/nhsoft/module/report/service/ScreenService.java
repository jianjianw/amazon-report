package com.nhsoft.module.report.service;

import com.nhsoft.module.report.dto.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ScreenService {

    List<ScreenItemSaleDTO> findItemSales(String systemBookCode, Integer branchNum);

    List<ScreenItemSaleDTO> findItemSaleCounts(String systemBookCode, Integer branchNum);

    List<ScreenCategoryDTO> findCategorySales(String systemBookCode, Integer branchNum);

    List<Object[]> findMerchantSales(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo);

    Integer readCardUsers(String systemBookCode, Integer branchNum, Boolean isNew);

    Integer[] readOrderCounts(String systemBookCode, Integer branchNum);

    BigDecimal[] readOrderMoney(String systemBookCode, Integer branchNum);

    List<ScreenPlatformSaleDTO> findPlatformSales(String systemBookCode, Integer branchNum);

    List<ScreenTraceDTO> findScreenTraces(String systemBookCode, Integer branchNum);

    List<ScreenItemDTO> findScreenItems(String systemBookCode, Integer branchNum);

    List<ScreenMerchantStallInfoDTO> findScreenMerchantStallInfos(String systemBookCode, Integer branchNum);

    List<ScreenMerchantStallInfoDTO> findMerchantStallSaleMoney(String systemBookCode, Integer branchNum);


}
