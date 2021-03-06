package com.nhsoft.module.report.rpc;

import com.nhsoft.module.report.dto.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ScreenRpc {

    List<ScreenItemSaleDTO> findItemSales(String systemBookCode, Integer branchNum);

    List<ScreenItemSaleDTO> findItemSaleCounts(String systemBookCode, Integer branchNum);

    List<ScreenCategoryDTO> findCategorySales(String systemBookCode, Integer branchNum);

    List<ScreenMerchantDTO> findMerchantSales(String systemBookCode, Integer branchNum);

    List<Map<String, BigDecimal>> readSaleMoney(String systemBookCode, Integer branchNum);

    List<Map<String, Integer>> readCardUsers(String systemBookCode, Integer branchNum, Boolean isNew);

    List<ScreenPieData> readOrderCounts(String systemBookCode, Integer branchNum);

    List<ScreenPieData> readOrderMoney(String systemBookCode, Integer branchNum);

    List<ScreenPlatformSaleDTO> findPlatformSales(String systemBookCode, Integer branchNum);

    List<Map<String, Integer>> readOnlineOrderCounts(String systemBookCode, Integer branchNum);

    List<Map<String, Integer>> readTotalOrderCounts(String systemBookCode, Integer branchNum);

    List<Map<String, Integer>> readMemberOrderPercent(String systemBookCode, Integer branchNum);

    List<ScreenPromotionDTO> findScreenPromotions(String systemBookCode, Integer branchNum);

    List<ScreenTraceDTO> findScreenTraces(String systemBookCode, Integer branchNum);

    List<ScreenTraceDTO> findScreenTraceTests(String systemBookCode, Integer branchNum);

    List<ScreenItemDTO> findScreenItems(String systemBookCode, Integer branchNum);

    List<ScreenMerchantStallInfoDTO> findScreenMerchantStallInfos(String systemBookCode, Integer branchNum);

    List<CustomerAnalysisTimePeriod> findCustomerAnalysisTimePeriods(String systemBookCode, Integer branchNum);
}
