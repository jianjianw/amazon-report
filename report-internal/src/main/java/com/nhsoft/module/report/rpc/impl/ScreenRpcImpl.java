package com.nhsoft.module.report.rpc.impl;

import com.nhsoft.module.report.dto.*;
import com.nhsoft.module.report.model.Merchant;
import com.nhsoft.module.report.model.MerchantContract;
import com.nhsoft.module.report.rpc.ScreenRpc;
import com.nhsoft.module.report.service.MerchantContractService;
import com.nhsoft.module.report.service.MerchantService;
import com.nhsoft.module.report.service.ScreenService;
import com.nhsoft.module.report.util.AppUtil;
import com.nhsoft.report.utils.DateUtil;
import com.nhsoft.report.utils.ReportUtil;
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
    public List<ScreenItemSaleDTO> findItemSales(String systemBookCode, Integer branchNum) {
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

    @Override
    @RequestMapping("/readSaleMoney")
    public List<Map<String, BigDecimal>> readSaleMoney(String systemBookCode, Integer branchNum) {
        Map<String, BigDecimal> map = new HashMap<>();
        map.put("value", screenService.readOrderMoney(systemBookCode, branchNum)[0]);
        return Collections.singletonList(map);
    }

    @Override
    @RequestMapping("/readCardUsers")
    public List<Map<String, Integer>> readCardUsers(String systemBookCode, Integer branchNum, @RequestParam(value = "isNew", required = false) Boolean isNew) {
        Map<String, Integer> map = new HashMap<>();
        map.put("value", screenService.readCardUsers(systemBookCode, branchNum, isNew));
        return Collections.singletonList(map);
    }

    @Override
    @RequestMapping("/readOrderCounts")
    public List<ScreenPieData> readOrderCounts(String systemBookCode, Integer branchNum) {
        Integer[] orderCounts = screenService.readOrderCounts(systemBookCode, branchNum);
        List<ScreenPieData> datas = new ArrayList<>(2);
        datas.add(new ScreenPieData("member", orderCounts[1]));
        datas.add(new ScreenPieData("normal", orderCounts[0]-orderCounts[1]));
        return datas;
    }

    @Override
    @RequestMapping("/readOrderMoney")
    public List<ScreenPieData> readOrderMoney(String systemBookCode, Integer branchNum) {
        BigDecimal[] orderMoney = screenService.readOrderMoney(systemBookCode, branchNum);
        List<ScreenPieData> datas = new ArrayList<>(2);
        datas.add(new ScreenPieData("member", orderMoney[1]));
        datas.add(new ScreenPieData("normal", orderMoney[0].subtract(orderMoney[1])));
        return datas;
    }

    @Override
    @RequestMapping("/findPlatformSales")
    public List<ScreenPlatformSaleDTO> findPlatformSales(String systemBookCode, Integer branchNum) {
        return screenService.findPlatformSales(systemBookCode, branchNum);
    }

    @Override
    @RequestMapping("/readOnlineOrderCounts")
    public List<Map<String, Integer>> readOnlineOrderCounts(String systemBookCode, Integer branchNum) {
        Map<String, Integer> map = new HashMap<>();
        map.put("value", screenService.readOrderCounts(systemBookCode, branchNum)[2]);
        return Collections.singletonList(map);
    }
}
