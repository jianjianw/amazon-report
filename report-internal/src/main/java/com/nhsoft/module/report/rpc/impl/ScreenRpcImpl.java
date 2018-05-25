package com.nhsoft.module.report.rpc.impl;

import com.nhsoft.module.report.dto.*;
import com.nhsoft.module.report.model.*;
import com.nhsoft.module.report.rpc.ReportRpc;
import com.nhsoft.module.report.rpc.ScreenRpc;
import com.nhsoft.module.report.service.*;
import com.nhsoft.module.report.util.AppConstants;
import com.nhsoft.module.report.util.AppUtil;
import com.nhsoft.report.utils.DateUtil;
import com.nhsoft.report.utils.ReportUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
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
    @Autowired
    private StallPromotionService stallPromotionService;
    @Autowired
    private StallService stallService;
    @Autowired
    private PosItemService posItemService;
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private ReportRpc reportRpc;

    @Override
    @RequestMapping("/findItemSales")
    public List<ScreenItemSaleDTO> findItemSales(String systemBookCode, Integer branchNum) {
        return screenService.findItemSales(systemBookCode, branchNum);
    }

    @Override
    @RequestMapping("/findItemSaleCounts")
    public List<ScreenItemSaleDTO> findItemSaleCounts(String systemBookCode, Integer branchNum) {
        return screenService.findItemSaleCounts(systemBookCode, branchNum);
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

    @Override
    @RequestMapping("/readTotalOrderCounts")
    public List<Map<String, Integer>> readTotalOrderCounts(String systemBookCode, Integer branchNum) {
        Map<String, Integer> map = new HashMap<>();
        map.put("value", screenService.readOrderCounts(systemBookCode, branchNum)[0]);
        return Collections.singletonList(map);
    }

    @Override
    @RequestMapping("/readMemberOrderPercent")
    public List<Map<String, Integer>> readMemberOrderPercent(String systemBookCode, Integer branchNum) {
        Integer[] counts = screenService.readOrderCounts(systemBookCode, branchNum);
        Map<String, Integer> map = new HashMap<>();
        map.put("value", counts[0] == 0?0:BigDecimal.valueOf(counts[1]*100).divide(BigDecimal.valueOf(counts[0]), 0, BigDecimal.ROUND_HALF_UP).intValue());
        return Collections.singletonList(map);
    }

    @Override
    @RequestMapping("/findScreenPromotions")
    public List<ScreenPromotionDTO> findScreenPromotions(String systemBookCode, Integer branchNum) {
        StallPromotionCondition condition = new StallPromotionCondition();
        condition.setSystemBookCode(systemBookCode);
        condition.setBranchNum(branchNum);
        condition.setDateType(AppConstants.POLICY_ORDER_TIME);
        Date now = Calendar.getInstance().getTime();
        condition.setDateStart(now);
        condition.setDateEnd(now);
        condition.setPage(false);
        condition.setStates(Collections.singletonList(AppConstants.STATE_INIT_AUDIT_CODE));
        condition.setQueryDetail(true);
        List<StallPromotion> stallPromotions = stallPromotionService.find(condition);
        if(stallPromotions.size() == 0) {
            return new ArrayList<>(0);
        }
        List<Stall> stalls = stallService.find(stallPromotions.stream().map(StallPromotion::getStallNum).collect(Collectors.toList()));
        List<PosItem> posItems = posItemService.findByItemNumsInCache(systemBookCode, stallPromotions.stream().flatMap(s -> s.getDetails().stream()).map(StallPromotionDetail::getItemNum).collect(Collectors.toList()));
        return stallPromotions.stream().flatMap(s -> {
            ScreenPromotionDTO dto = new ScreenPromotionDTO();
            dto.setStallNum(s.getStallNum());
            stalls.stream().filter(t -> t.getStallNum().equals(s.getStallNum())).findAny().ifPresent(t -> {
                dto.setStallName(t.getStallName());
            });
            dto.setPolicyPromotionDate(DateUtil.getDateStr(s.getPolicyPromotionDateFrom())+"至"+DateUtil.getDateStr(s.getPolicyPromotionDateTo()));
            return s.getDetails().stream().map(d -> {
                ScreenPromotionDTO detailDTO = new ScreenPromotionDTO();
                BeanUtils.copyProperties(dto, detailDTO);
                detailDTO.setItemNum(d.getItemNum());
                posItems.stream().filter(p -> p.getItemNum().equals(d.getItemNum()) && "生鲜部".equals(p.getItemDepartment())).findAny().ifPresent(p -> detailDTO.setItemName(p.getItemName()));
                detailDTO.setPolicyPromotionDetailStdPrice(d.getPolicyPromotionDetailStdPrice());
                detailDTO.setPolicyPromotionDetailSpecialPrice(d.getPolicyPromotionDetailSpecialPrice());
                return detailDTO;
            });
        }).filter(d -> d.getItemName() != null).collect(Collectors.toList());
    }

    @Override
    @RequestMapping("/findScreenTraces")
    public List<ScreenTraceDTO> findScreenTraces(String systemBookCode, Integer branchNum) {
        List<ScreenTraceDTO> dtos = screenService.findScreenTraces(systemBookCode, branchNum);
        if(dtos.size() == 0) {
            return new ArrayList<>(0);
        }
        List<Supplier> suppliers = supplierService.findInCache(systemBookCode);
        List<PosItem> posItems = posItemService.findByItemNumsInCache(systemBookCode, dtos.stream().map(ScreenTraceDTO::getItemNum).collect(Collectors.toList()));
        List<Stall> stalls = stallService.find(dtos.stream().map(ScreenTraceDTO::getStallNum).collect(Collectors.toList()));
        dtos.forEach(d -> {
            suppliers.stream().filter(s -> s.getSupplierNum().equals(d.getSupplierNum())).findAny().ifPresent(s -> d.setSupplierName(s.getSupplierName()));
            posItems.stream().filter(p -> p.getItemNum().equals(d.getItemNum())).findAny().ifPresent(p -> d.setItemName(p.getItemName()));
            stalls.stream().filter(s -> s.getStallNum().equals(d.getStallNum())).findAny().ifPresent(s -> d.setStallName(s.getStallName()));
        });
        return dtos;
    }


    @Override
    @RequestMapping("/findScreenTraceTests")
    public List<ScreenTraceDTO> findScreenTraceTests(String systemBookCode, Integer branchNum) {
        List<ScreenTraceDTO> dtos = screenService.findScreenTraces(systemBookCode, branchNum);
        if(dtos.size() == 0) {
            return new ArrayList<>(0);
        }
        List<Merchant> merchants = merchantService.find(systemBookCode, branchNum);
        List<PosItem> posItems = posItemService.findByItemNumsInCache(systemBookCode, dtos.stream().map(ScreenTraceDTO::getItemNum).collect(Collectors.toList()));
        dtos = dtos.stream().filter(d -> StringUtils.isNotEmpty(d.getItemTraceLogCategory())).peek(d -> {
            merchants.stream().filter(s -> s.getMerchantNum().equals(d.getMerchantNum())).findAny().ifPresent(s -> d.setMerchantName(s.getMerchantName()));
            posItems.stream().filter(p -> p.getItemNum().equals(d.getItemNum())).findAny().ifPresent(p -> d.setItemName(p.getItemName()));
            d.setItemTraceLogResult("合格");
        }).collect(Collectors.toList());
        return dtos;
    }

    @Override
    @RequestMapping("/findScreenItems")
    public List<ScreenItemDTO> findScreenItems(String systemBookCode, Integer branchNum) {
        List<ScreenItemDTO> items = screenService.findScreenItems(systemBookCode, branchNum);
        if(items.size() == 0) {
            return new ArrayList<>(0);
        }
        List<Merchant> merchants = merchantService.find(systemBookCode, branchNum);
        List<Stall> stalls = stallService.find(items.stream().map(ScreenItemDTO::getStallNum).collect(Collectors.toList()));
        items.forEach(i -> {
            merchants.stream().filter(m -> m.getMerchantNum().equals(i.getMerchantNum())).findAny().ifPresent(m -> i.setMerchantName(m.getMerchantName()));
            stalls.stream().filter(s -> s.getStallNum().equals(i.getStallNum())).findAny().ifPresent(s -> i.setStallName(s.getStallName()));
        });
        return items;
    }

    @Override
    @RequestMapping("/findScreenMerchantStallInfos")
    public List<ScreenMerchantStallInfoDTO> findScreenMerchantStallInfos(String systemBookCode, Integer branchNum) {
        List<ScreenMerchantStallInfoDTO> infos = screenService.findScreenMerchantStallInfos(systemBookCode, branchNum);
        List<Merchant> merchants = merchantService.find(systemBookCode, branchNum);
        List<Stall> stalls = stallService.find(infos.stream().map(ScreenMerchantStallInfoDTO::getStallNum).collect(Collectors.toList()));
        infos.forEach(i -> {
            merchants.stream().filter(m -> m.getMerchantNum().equals(i.getMerchantNum())).findAny().ifPresent(m -> {
                i.setMerchantName(m.getMerchantName());
                i.setMerchantLinkman(m.getMerchantLinkman());
                i.setMerchantPhone(m.getMerchantPhone());
            });
            stalls.stream().filter(s -> s.getStallNum().equals(i.getStallNum())).findAny().ifPresent(s -> i.setStallName(s.getStallName()));
        });
        ReportUtil<ScreenMerchantStallInfoDTO> reportUtil = new ReportUtil<>(ScreenMerchantStallInfoDTO.class);
        infos.forEach(reportUtil::add);
        infos = screenService.findMerchantStallSaleMoney(systemBookCode, branchNum);
        String bizMonth = DateUtil.getDateStr(Calendar.getInstance().getTime(), "yyyyMM");
        infos.forEach(i -> {
            if(bizMonth.equals(i.getBizMonth())) {
                i.setThisMonthSaleMoney(i.getSaleMoney());
            } else {
                i.setLastMonthSaleMoney(i.getSaleMoney());
            }
            reportUtil.add(i);
        });
        infos = new ArrayList<>(reportUtil.toList());
        return infos.stream().filter(i -> i.getStallName() != null && i.getMerchantName() != null).collect(Collectors.toList());
    }

    @Override
    @RequestMapping("/findCustomerAnalysisTimePeriods")
    public List<CustomerAnalysisTimePeriod> findCustomerAnalysisTimePeriods(String systemBookCode, Integer branchNum) {
        Date now = Calendar.getInstance().getTime();
        return reportRpc.findCustomerAnalysisTimePeriods(systemBookCode, now, now, Collections.singletonList(branchNum), 60, null, null);
    }
}
