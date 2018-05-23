package com.nhsoft.module.report.rpc;

import com.nhsoft.module.report.dto.ScreenCategoryDTO;
import com.nhsoft.module.report.dto.ScreenItemSaleDTO;
import com.nhsoft.module.report.dto.ScreenMerchantDTO;

import java.util.List;

public interface ScreenRpc {

    List<ScreenItemSaleDTO> findItemSales(String systemBookCode, Integer branchNum);

    List<ScreenCategoryDTO> findCategorySales(String systemBookCode, Integer branchNum);

    List<ScreenMerchantDTO> findMerchantSales(String systemBookCode, Integer branchNum);



}
