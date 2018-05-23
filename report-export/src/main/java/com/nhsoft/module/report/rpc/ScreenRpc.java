package com.nhsoft.module.report.rpc;

import com.nhsoft.module.report.dto.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ScreenRpc {

    List<ScreenItemSaleDTO> findItemSales(String systemBookCode, Integer branchNum);

    List<ScreenCategoryDTO> findCategorySales(String systemBookCode, Integer branchNum);

    List<ScreenMerchantDTO> findMerchantSales(String systemBookCode, Integer branchNum);

    List<Map<String, BigDecimal>> readSaleMoney(String systemBookCode, Integer branchNum);

    List<Map<String, Integer>> readCardUsers(String systemBookCode, Integer branchNum, Boolean isNew);

    List<ScreenPieData> readOrderCounts(String systemBookCode, Integer branchNum);

    List<ScreenPieData> readOrderMoney(String systemBookCode, Integer branchNum);

    List<ScreenPlatformSaleDTO> findPlatformSales(String systemBookCode, Integer branchNum);

    List<Map<String, Integer>> readOnlineOrderCounts(String systemBookCode, Integer branchNum);

}
