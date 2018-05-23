package com.nhsoft.module.report.service;

import com.nhsoft.module.report.dto.ScreenCategoryDTO;
import com.nhsoft.module.report.dto.ScreenItemSaleDTO;
import com.nhsoft.module.report.dto.ScreenMerchantDTO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ScreenService {

    List<ScreenItemSaleDTO> findItemSales(String systemBookCode, Integer branchNum);

    List<ScreenCategoryDTO> findCategorySales(String systemBookCode, Integer branchNum);

    List<Object[]> findMerchantSales(String systemBookCode, Integer branchNum, Date dateFrom, Date dateTo);


}
