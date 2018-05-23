package com.nhsoft.module.report.rpc.impl;

import com.nhsoft.module.report.dto.ScreenCategoryDTO;
import com.nhsoft.module.report.dto.ScreenItemSaleDTO;
import com.nhsoft.module.report.dto.ScreenMerchantDTO;
import com.nhsoft.module.report.model.Merchant;
import com.nhsoft.module.report.model.MerchantContract;
import com.nhsoft.module.report.rpc.PosOrderRpc;
import com.nhsoft.module.report.rpc.ReportRpc;
import com.nhsoft.module.report.rpc.ScreenRpc;
import com.nhsoft.module.report.service.MerchantContractService;
import com.nhsoft.module.report.service.MerchantService;
import com.nhsoft.module.report.service.ScreenService;
import com.nhsoft.module.report.util.AppUtil;
import com.nhsoft.report.utils.DateUtil;
import com.nhsoft.report.utils.ReportUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class ScreenRpcImpl implements ScreenRpc {

    @Autowired
    private ScreenService screenService;
    @Autowired
    private MerchantContractService merchantContractService;
    @Autowired
    private MerchantService merchantService;

    @Override
    @RequestMapping("/findItemSales")
    public List<ScreenItemSaleDTO> findItemSales(@RequestParam("systemBookCode") String systemBookCode, @RequestParam("branchNum") Integer branchNum) {
        return screenService.findItemSales(systemBookCode, branchNum);
    }

    @Override
    @RequestMapping("/findCategorySales")
    public List<ScreenCategoryDTO> findCategorySales(String systemBookCode, Integer branchNum) {
        return screenService.findCategorySales(systemBookCode, branchNum);
    }

    @Override
    @RequestMapping("/findMerchantSales")
    public List<ScreenMerchantDTO> findMerchantSales(String systemBookCode, Integer branchNum) {
        Date now = Calendar.getInstance().getTime();
        ReportUtil<ScreenMerchantDTO> reportUtil = new ReportUtil<>(ScreenMerchantDTO.class);
        List<Object[]> list = screenService.findMerchantSales(systemBookCode, branchNum, now, now);
        list.forEach(m -> {
            ScreenMerchantDTO dto = reportUtil.getInstance();
            dto.setMerchantNum((Integer) m[0]);
            dto.setTodaySaleMoney(AppUtil.getValue(m[1], BigDecimal.class));
            reportUtil.add(dto);
        });
        list = screenService.findMerchantSales(systemBookCode, branchNum, DateUtil.getFirstDayOfMonth(now), DateUtil.getLastDayOfMonth(now));
        list.forEach(m -> {
            ScreenMerchantDTO dto = reportUtil.getInstance();
            dto.setMerchantNum((Integer) m[0]);
            dto.setThisMonthSaleMoney(AppUtil.getValue(m[1], BigDecimal.class));
            reportUtil.add(dto);
        });
        list = screenService.findMerchantSales(systemBookCode, branchNum, DateUtil.getFirstDayOfQuarter(now), DateUtil.getLastDayOfQuarter(now));
        list.forEach(m -> {
            ScreenMerchantDTO dto = reportUtil.getInstance();
            dto.setMerchantNum((Integer) m[0]);
            dto.setThisQuarterSaleMoney(AppUtil.getValue(m[1], BigDecimal.class));
            reportUtil.add(dto);
        });
        list = screenService.findMerchantSales(systemBookCode, branchNum, DateUtil.getFirstDayOfYear(now), DateUtil.getLastDayOfYear(now));
        list.forEach(m -> {
            ScreenMerchantDTO dto = reportUtil.getInstance();
            dto.setMerchantNum((Integer) m[0]);
            dto.setThisYearSaleMoney(AppUtil.getValue(m[1], BigDecimal.class));
            reportUtil.add(dto);
        });
        List<ScreenMerchantDTO> dtos = reportUtil.toList();
        List<Integer> merchantNums = merchantContractService.findByMerchantNum(systemBookCode, branchNum, null).stream().map(MerchantContract::getMerchantNum).collect(Collectors.toList());
        List<Merchant> merchants = merchantService.find(systemBookCode, branchNum);
        dtos = dtos.stream().filter(m -> merchantNums.contains(m.getMerchantNum())).peek(d -> merchants.stream().filter(m -> d.getMerchantNum().equals(m.getMerchantNum())).findAny().ifPresent(m -> d.setMerchantName(m.getMerchantName()))).collect(Collectors.toList());
        dtos.sort(Comparator.comparing(ScreenMerchantDTO::getTodaySaleMoney).reversed());
        return dtos;
    }
}
