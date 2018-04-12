package com.nhsoft.module.report.rpc.impl;


import com.nhsoft.module.report.dto.TransferOutMoney;
import com.nhsoft.module.report.dto.TransferOutMoneyAndAmountDTO;
import com.nhsoft.module.report.dto.TransferOutMoneyDateDTO;
import com.nhsoft.module.report.dto.TransterOutDTO;
import com.nhsoft.module.report.rpc.TransferOutOrderRpc;
import com.nhsoft.module.report.service.TransferOutOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Component
public class TransferOutOrderRpcImpl implements TransferOutOrderRpc {

    @Autowired
    private TransferOutOrderService transferOutOrderService;
    @Override
    public List<TransferOutMoney> findMoneyBranchSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {

        List<Object[]> objects = transferOutOrderService.findMoneyBranchSummary(systemBookCode, branchNums, dateFrom, dateTo);
        int size = objects.size();
        List<TransferOutMoney> list = new ArrayList<TransferOutMoney>(size);
        if(objects.isEmpty()){
            return list;
        }
        for (int i = 0; i <size ; i++) {
            Object[] object = objects.get(i);
            TransferOutMoney transferOutMoney = new TransferOutMoney();
            transferOutMoney.setBranchNum((Integer) object[0]);
            transferOutMoney.setOutMoney((BigDecimal) object[1]);
            list.add(transferOutMoney);
        }
        return list;
    }

    @Override
    public List<TransferOutMoney> findMoneyBizdaySummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
        List<Object[]> objects = transferOutOrderService.findMoneyBizdaySummary(systemBookCode, branchNums, dateFrom, dateTo);
        int size = objects.size();
        List<TransferOutMoney> list = new ArrayList<>(size);
        if(objects.isEmpty()){
            return list;
        }
        for (int i = 0; i <size ; i++) {
            Object[] object = objects.get(i);
            TransferOutMoney transferOutMoney = new TransferOutMoney();
            transferOutMoney.setBiz((String) object[0]);
            transferOutMoney.setOutMoney((BigDecimal) object[1]);
            list.add(transferOutMoney);
        }
        return list;
    }

    @Override
    public List<TransferOutMoney> findMoneyBymonthSummary(String systemBookCode, List<Integer> branchNums, Date dateFrom, Date dateTo) {
        List<Object[]> objects = transferOutOrderService.findMoneyBymonthSummary(systemBookCode, branchNums, dateFrom, dateTo);
        int size = objects.size();
        List<TransferOutMoney> list = new ArrayList<>(size);
        if(objects.isEmpty()){
            return list;
        }
        for (int i = 0; i <size ; i++) {
            Object[] object = objects.get(i);
            TransferOutMoney transferOutMoney = new TransferOutMoney();
            transferOutMoney.setBiz((String) object[0]);
            transferOutMoney.setOutMoney((BigDecimal) object[1]);
            list.add(transferOutMoney);
        }
        return list;
    }

    @Override
    public List<TransterOutDTO> findItemSummary(String systemBookCode, List<Integer> outBranchNums, List<Integer> branchNums, Date dateFrom, Date dateTo, List<Integer> itemNums) {

        List<Object[]> objects = transferOutOrderService.findItemSummary(systemBookCode, outBranchNums, branchNums, dateFrom, dateTo, itemNums);
        int size = objects.size();
        List<TransterOutDTO> list = new ArrayList<>(size);
        for (int i = 0; i <size ; i++) {
            Object[] object = objects.get(i);
            TransterOutDTO dto = new TransterOutDTO();
            dto.setItemNum((Integer) object[0]);
            dto.setQty((BigDecimal) object[1]);
            dto.setMoney((BigDecimal) object[2]);
            dto.setUseQty((BigDecimal) object[3]);
            list.add(dto);
        }
        return list;
    }

    @Override
    public List<TransferOutMoneyDateDTO> findDateSummary(String systemBookCode, Integer centerBranchNum, List<Integer> branchNums, Date dateFrom, Date dateTo, String strDate) {

        List<Object[]> objects = transferOutOrderService.findDateSummary(systemBookCode, centerBranchNum, branchNums, dateFrom, dateTo, strDate);
        int size = objects.size();
        List<TransferOutMoneyDateDTO> list = new ArrayList<>(size);
        for (int i = 0; i < size ; i++) {
            Object[] object = objects.get(i);
            TransferOutMoneyDateDTO dto = new TransferOutMoneyDateDTO();
            dto.setBizday((String) object[0]);
            dto.setTotalMoney((BigDecimal) object[1]);
            dto.setFeeMoney((BigDecimal) object[2]);
            list.add(dto);
        }
        return list;
    }

    @Override
    public List<TransferOutMoneyAndAmountDTO> findMoneyAndAmountByBiz(String systemBookCode, Date dateFrom, Date dateTo,List<Integer> itemNums) {

        List<Object[]> objects = transferOutOrderService.findMoneyAndAmountByBiz(systemBookCode, dateFrom, dateTo,itemNums);
        int size = objects.size();
        List<TransferOutMoneyAndAmountDTO> list = new ArrayList<>(size);
        for (int i = 0; i < size ; i++) {
            Object[] object = objects.get(i);
            TransferOutMoneyAndAmountDTO dto = new TransferOutMoneyAndAmountDTO();
            dto.setBiz((String) object[0]);
            dto.setOutQty(object[1] == null ? BigDecimal.ZERO : (BigDecimal) object[1] );
            dto.setOutMoney(object[2] == null ? BigDecimal.ZERO : (BigDecimal) object[2]);
            list.add(dto);
        }
        return list;
    }

    @Override
    public List<TransterOutDTO> findMoneyAndAmountByItemNum(String systemBookCode, Integer branchNum,List<Integer> storehouseNums, Date dateFrom, Date dateTo, List<Integer> itemNums, String sortField) {
        List<Object[]> objects = transferOutOrderService.findMoneyAndAmountByItemNum(systemBookCode, branchNum,storehouseNums, dateFrom, dateTo, itemNums, sortField);
        int size = objects.size();
        List<TransterOutDTO> list = new ArrayList<>(size);
        for (int i = 0; i < size ; i++) {
            Object[] object = objects.get(i);
            TransterOutDTO dto = new TransterOutDTO();
            dto.setItemNum((Integer) object[0]);
            dto.setQty((BigDecimal) object[1]);
            dto.setMoney((BigDecimal) object[2]);
            list.add(dto);
        }
        return list;
    }


    //AMA-23175  性能优化-直调查询-门店商品汇总
    /*@Override
    public PagingLoadResult<TransferProfitByPosItemData> findTransferProfitByPosItemBranch(TransferProfitQuery queryData,
                                                                                           Integer itemFlagNum, String exportUuid, PagingLoadConfig loadConfig) throws ErrorException {
        try {
            int offset = loadConfig.getOffset();
            int limit = loadConfig.getLimit();
            String sortField = null;
            String sortType = null;
            if (loadConfig.getSortInfo().size() > 0) {
                SortInfo sort = loadConfig.getSortInfo().get(0);
                sortField = sort.getSortField();
                sortType = sort.getSortDir().name();
            }
            queryData.setCategoryCodes(queryData.getItemTypeNums());
            TransferOutOrderRpc transferOutOrderService = (TransferOutOrderRpc) AppUtil.createCenterObject(TransferOutOrderRpc.class);
            TransferInOrderRpc transferInOrderService = (TransferInOrderRpc) AppUtil.createCenterObject(TransferInOrderRpc.class);
            BranchRpc branchRpc = (BranchRpc) AppUtil.createCenterObject(BranchRpc.class);
            PosItemRpc posItemRpc = (PosItemRpc) AppUtil.createCenterObject(PosItemRpc.class);

            String unit = queryData.getUnitType();
            if (unit == null) {
                unit = AppConstants.UNIT_TRANFER;
                queryData.setUnitType(unit);
            }
            List<Integer> itemNums = queryData.getItemNums();
            List<String> categoryCodes = queryData.getCategoryCodes();
            List<PosItemDTO> posItems = new ArrayList<PosItemDTO>();
            if (itemFlagNum != null && itemFlagNum > 0) {
                PosItemFlagRpc posItemFlagRpc = (PosItemFlagRpc) AppUtil.createCenterObject(PosItemFlagRpc.class);
                List<ItemFlagDetailDTO> itemDetails = posItemFlagRpc.findDetails(queryData.getSystemBookCode(), itemFlagNum);
                for (int i = 0; i < itemDetails.size(); i++) {
                    posItems.add(itemDetails.get(i).getPosItem());
                }
                if ((itemNums != null && itemNums.size() > 0)
                        || (categoryCodes != null && categoryCodes.size() > 0)) {
                    for (int i = 0; i < posItems.size(); i++) {
                        if (itemNums != null && itemNums.size() > 0) {
                            if (!itemNums.contains(posItems.get(i).getItemNum())) {
                                posItems.remove(i);
                                i--;
                                continue;
                            }
                        }
                        if (categoryCodes != null && categoryCodes.size() > 0) {
                            if (!categoryCodes.contains(posItems.get(i).getItemCategoryCode())) {
                                posItems.remove(i);
                                i--;
                                continue;
                            }
                        }
                    }
                }
                if (posItems.size() == 0) {
                    return new PagingLoadResultBean<TransferProfitByPosItemData>(new ArrayList<TransferProfitByPosItemData>(), 0, 0);
                } else {
                    itemNums = new ArrayList<Integer>();
                    for (int i = 0; i < posItems.size(); i++) {
                        itemNums.add(posItems.get(i).getItemNum());
                    }
                    queryData.setItemNums(itemNums);
                }
            }

            List<PosItemDTO> posItemDatas = posItemRpc.findShortItems(queryData.getSystemBookCode());

            com.nhsoft.module.transfer.export.dto.TransferProfitQuery query = (com.nhsoft.module.transfer.export.dto.TransferProfitQuery)
                    ObjectConverter.copy(queryData, new com.nhsoft.module.transfer.export.dto.TransferProfitQuery());

            List<TransferProfitByPosItemData> list = new ArrayList<TransferProfitByPosItemData>();

            List<Object[]> outObjects = transferOutOrderService.findProfitGroupByBranchAndItem(query.getSystemBookCode(), query);

            for (int i = 0; i < outObjects.size(); i++) {
                Object[] objects = outObjects.get(i);
                Integer branchNum = (Integer) objects[0];
                Integer outBranchNum = (Integer) objects[1];
                Integer itemNum = (Integer) objects[2];
                Integer itemMatrixNum = objects[3] == null ? 0 : (Integer) objects[3];
                BigDecimal amount = objects[4] == null ? BigDecimal.ZERO : (BigDecimal) objects[4];
                BigDecimal cost = objects[5] == null ? BigDecimal.ZERO : (BigDecimal) objects[5];
                BigDecimal money = objects[6] == null ? BigDecimal.ZERO : (BigDecimal) objects[6];
                BigDecimal saleMoney = objects[7] == null ? BigDecimal.ZERO : (BigDecimal) objects[7];
                BigDecimal useAmount = objects[8] == null ? BigDecimal.ZERO : (BigDecimal) objects[8];
                BigDecimal amountPr = objects[9] == null ? BigDecimal.ZERO : (BigDecimal) objects[9];
                BigDecimal useAmountPr = objects[10] == null ? BigDecimal.ZERO : (BigDecimal) objects[10];
                BigDecimal moneyTranPr = objects[11] == null ? BigDecimal.ZERO : (BigDecimal) objects[11];
                BigDecimal moneyCostPr = objects[12] == null ? BigDecimal.ZERO : (BigDecimal) objects[12];
                BigDecimal receiveTare = objects[13] == null ? BigDecimal.ZERO : (BigDecimal) objects[13];

                TransferProfitByPosItemData data = new TransferProfitByPosItemData();
                data.setTranferBranchNum(outBranchNum);
                data.setBranchNum(branchNum);
                data.setItemNum(itemNum);
                data.setItemMatrixNum(itemMatrixNum);
                data.setBasicQty(amount);
                data.setOutAmount(useAmount);
                data.setBasicQtyPr(amountPr);
                data.setOutAmountPr(useAmountPr);
                data.setOutAmountPrTranferMoney(moneyTranPr.setScale(2, BigDecimal.ROUND_HALF_UP));
                data.setOutAmountPrCostMoney(moneyCostPr.setScale(2, BigDecimal.ROUND_HALF_UP));
                data.setOutCost(cost.setScale(2, BigDecimal.ROUND_HALF_UP));
                data.setOutMoney(money.setScale(2, BigDecimal.ROUND_HALF_UP));
                data.setSaleMoney(saleMoney.setScale(2, BigDecimal.ROUND_HALF_UP));
                data.setReceiveTare(receiveTare);
                list.add(data);
            }

            List<Object[]> inObjects = transferInOrderService.findProfitGroupByBranchAndItem(query.getSystemBookCode(), query);
            for (int i = 0; i < inObjects.size(); i++) {
                Object[] objects = inObjects.get(i);
                Integer branchNum = (Integer) objects[0];
                Integer inBranchNum = (Integer) objects[1];
                Integer itemNum = (Integer) objects[2];
                Integer itemMatrixNum = objects[3] == null ? 0 : (Integer) objects[3];
                BigDecimal amount = objects[4] == null ? BigDecimal.ZERO : (BigDecimal) objects[4];
                BigDecimal cost = objects[5] == null ? BigDecimal.ZERO : (BigDecimal) objects[5];
                BigDecimal money = objects[6] == null ? BigDecimal.ZERO : (BigDecimal) objects[6];
                BigDecimal saleMoney = objects[7] == null ? BigDecimal.ZERO : (BigDecimal) objects[7];
                BigDecimal useAmount = objects[8] == null ? BigDecimal.ZERO : (BigDecimal) objects[8];
                BigDecimal amountPr = objects[9] == null ? BigDecimal.ZERO : (BigDecimal) objects[9];
                BigDecimal useAmountPr = objects[10] == null ? BigDecimal.ZERO : (BigDecimal) objects[10];
                BigDecimal moneyTranPr = objects[11] == null ? BigDecimal.ZERO : (BigDecimal) objects[11];
                BigDecimal moneyCostPr = objects[12] == null ? BigDecimal.ZERO : (BigDecimal) objects[12];
                BigDecimal receiveTare = objects[12] == null ? BigDecimal.ZERO : (BigDecimal) objects[12];

                TransferProfitByPosItemData data = getTransferProfitByPosItemData(list, inBranchNum, branchNum, itemNum, itemMatrixNum);
                if (data == null) {
                    data = new TransferProfitByPosItemData();
                    data.setTranferBranchNum(inBranchNum);
                    data.setBranchNum(branchNum);
                    data.setItemNum(itemNum);
                    data.setItemMatrixNum(itemMatrixNum);
                    list.add(data);
                }
                data.setBasicQty(data.getBasicQty().subtract(amount));
                data.setOutAmount(data.getOutAmount().subtract(useAmount));
                data.setBasicQtyPr(data.getBasicQtyPr().subtract(amountPr));
                data.setOutAmountPr(data.getOutAmountPr().subtract(useAmountPr));
                data.setOutCost((data.getOutCost().subtract(cost)).setScale(2, BigDecimal.ROUND_HALF_UP));
                data.setOutMoney((data.getOutMoney().subtract(money)).setScale(2, BigDecimal.ROUND_HALF_UP));
                data.setSaleMoney((data.getSaleMoney().subtract(saleMoney)).setScale(2, BigDecimal.ROUND_HALF_UP));
                data.setOutAmountPrTranferMoney(data.getOutAmountPrTranferMoney().subtract(moneyTranPr).setScale(2, BigDecimal.ROUND_HALF_UP));
                data.setOutAmountPrCostMoney(data.getOutAmountPrCostMoney().subtract(moneyCostPr).setScale(2, BigDecimal.ROUND_HALF_UP));
                data.setReceiveTare(data.getReceiveTare().subtract(receiveTare));
            }
            List<BranchDTO> branchs = branchRpc.findInCache(queryData.getSystemBookCode());

            BigDecimal basicQtySum = BigDecimal.ZERO;
            BigDecimal basicQtyPrSum = BigDecimal.ZERO;
            for (int i = 0; i < list.size(); i++) {
                TransferProfitByPosItemData data = list.get(i);
                data.setId(AppUtil.getUUID());
                basicQtySum = basicQtySum.add(data.getBasicQty());
                basicQtyPrSum = basicQtyPrSum.add(data.getBasicQtyPr());
                BranchDTO branch = AppUtil.getBranch(branchs, data.getTranferBranchNum());
                if (branch != null) {
                    data.setTranferBranchNum(branch.getBranchNum());
                    data.setTranferBranchName(branch.getBranchName());
                }

                branch = AppUtil.getBranch(branchs, data.getBranchNum());
                if (branch != null) {
                    data.setBranchNum(branch.getBranchNum());
                    data.setBranchName(branch.getBranchName());
                }

                Integer itemNum = data.getItemNum();
                PosItemDTO posItem = AppUtil.getPosItem(itemNum, posItemDatas);
                if (posItem == null) {
                    continue;
                }
                data.setPosItemData(PosItemConverter.createModelData(posItem, false));
                data.setPosItemTypeCode(posItem.getItemCategoryCode());
                data.setPosItemTypeName(posItem.getItemCategory());
                data.setSpec(posItem.getItemSpec());
                if (queryData.isEnableAssist()) {
                    if (StringUtils.isNotEmpty(posItem.getItemAssistUnit()) && posItem.getItemAssistRate() != null
                            && posItem.getItemAssistRate().compareTo(BigDecimal.ZERO) > 0) {
                        data.setUnit(posItem.getItemAssistUnit());
                    } else {
                        data.setUnit(posItem.getItemTransferUnit());
                        if (unit.equals(AppConstants.UNIT_TRANFER)) {
                            data.setUnit(posItem.getItemTransferUnit());
                        } else if (unit.equals(AppConstants.UNIT_SOTRE)) {
                            data.setUnit(posItem.getItemInventoryUnit());
                        } else if (unit.equals(AppConstants.UNIT_PURCHASE)) {
                            data.setUnit(posItem.getItemPurchaseUnit());
                        } else if (unit.equals(AppConstants.UNIT_BASIC)) {
                            data.setUnit(posItem.getItemUnit());
                        } else if (unit.equals(AppConstants.UNIT_PIFA)) {
                            data.setUnit(posItem.getItemWholesaleUnit());
                        }
                    }
                } else {
                    data.setUnit(posItem.getItemTransferUnit());
                    if (unit.equals(AppConstants.UNIT_TRANFER)) {
                        data.setUnit(posItem.getItemTransferUnit());
                    } else if (unit.equals(AppConstants.UNIT_SOTRE)) {
                        data.setUnit(posItem.getItemInventoryUnit());
                    } else if (unit.equals(AppConstants.UNIT_PURCHASE)) {
                        data.setUnit(posItem.getItemPurchaseUnit());
                    } else if (unit.equals(AppConstants.UNIT_BASIC)) {
                        data.setUnit(posItem.getItemUnit());
                    } else if (unit.equals(AppConstants.UNIT_PIFA)) {
                        data.setUnit(posItem.getItemWholesaleUnit());
                    }
                }
                data.setBasicUnit(posItem.getItemUnit());
                data.setPosItemCode(posItem.getItemCode());
                data.setPosItemName(posItem.getItemName());

                BigDecimal rate = BigDecimal.ZERO;
                if (unit.equals(AppConstants.UNIT_TRANFER)) {
                    rate = posItem.getItemTransferRate();
                } else if (unit.equals(AppConstants.UNIT_SOTRE)) {
                    rate = posItem.getItemInventoryRate();
                } else if (unit.equals(AppConstants.UNIT_PURCHASE)) {
                    rate = posItem.getItemPurchaseRate();
                } else if (unit.equals(AppConstants.UNIT_BASIC)) {
                    rate = BigDecimal.ONE;
                } else if (unit.equals(AppConstants.UNIT_PIFA)) {
                    rate = posItem.getItemWholesaleRate();
                }
                if (rate.compareTo(BigDecimal.ZERO) > 0) {
                    data.setOutAmount(data.getBasicQty().divide(rate, 4, BigDecimal.ROUND_HALF_UP));
                    data.setOutAmountPr(data.getBasicQtyPr().divide(rate, 4, BigDecimal.ROUND_HALF_UP));
                }
                if (data.getItemMatrixNum() > 0) {
                    ItemMatrixDTO itemMatrix = AppUtil.getItemMatrix(posItem.getItemMatrixs(), itemNum, data.getItemMatrixNum());
                    if (itemMatrix != null) {
                        data.setPosItemName(data.getPosItemName().concat(AppUtil.getMatrixName(itemMatrix)));
                    }
                }
                data.setOutProfit((data.getOutMoney().subtract(data.getOutCost())).setScale(2, BigDecimal.ROUND_HALF_UP));
                data.setSaleProfit((data.getSaleMoney().subtract(data.getOutMoney())).setScale(2, BigDecimal.ROUND_HALF_UP));

                if (data.getOutMoney().compareTo(BigDecimal.ZERO) > 0) {
                    data.setOutProfitRate(data.getOutProfit().divide(data.getOutMoney(), 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)).setScale(2));

                } else {
                    data.setOutProfitRate(BigDecimal.ZERO);
                }
                if (data.getSaleMoney().compareTo(BigDecimal.ZERO) != 0) {
                    data.setSaleProfitRate(data.getSaleProfit().divide(data.getSaleMoney(), 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)).setScale(2));

                } else {
                    data.setSaleProfitRate(BigDecimal.ZERO);
                }
                if (data.getOutMoney() == null || data.getBasicQty() == null || data.getBasicQty().compareTo(BigDecimal.ZERO) == 0) {
                    data.setBasicPrice(BigDecimal.ZERO);
                } else {
                    data.setBasicPrice(data.getOutMoney().divide(data.getBasicQty(), 4, BigDecimal.ROUND_HALF_UP));
                }

            }
            if (sortField == null) {
                sortField = "branchNum";
                sortType = "ASC";
            }

            AppUtil.setProperty(list);
            ComparatorAutoGridGroupData comparator = new ComparatorAutoGridGroupData(
                    "tranferBranchNum", sortField, sortType);
            Collections.sort(list, comparator);

            // 总合计
            BigDecimal outAmountSum = BigDecimal.ZERO;
            BigDecimal outAmountPrSum = BigDecimal.ZERO;
            BigDecimal outMoneySum = BigDecimal.ZERO;
            BigDecimal saleMoneySum = BigDecimal.ZERO;
            BigDecimal outCostSum = BigDecimal.ZERO;
            BigDecimal outProfitSum = BigDecimal.ZERO;
            BigDecimal saleProfitSum = BigDecimal.ZERO;
            BigDecimal outAmountPrTranferMoneySum = BigDecimal.ZERO;
            BigDecimal outAmountPrCostMoneySum = BigDecimal.ZERO;
            BigDecimal receiveTareSum = BigDecimal.ZERO;
            BigDecimal totalAmountSum = BigDecimal.ZERO;
            BigDecimal totalMoneySum = BigDecimal.ZERO;
            for (int i = list.size() - 1; i >= 0; i--) {
                TransferProfitByPosItemData data = list.get(i);

                data.setTotalAmount(data.getOutAmount().subtract(data.getInAmount()).add(data.getOutAmountPr()));
                data.setTotalMoney(data.getOutMoney().subtract(data.getInMoney()).add(data.getOutAmountPrTranferMoney()));

                outAmountSum = outAmountSum.add(data.getOutAmount());
                outAmountPrSum = outAmountPrSum.add(data.getOutAmountPr());
                outCostSum = outCostSum.add(data.getOutCost());
                outMoneySum = outMoneySum.add(data.getOutMoney());
                saleMoneySum = saleMoneySum.add(data.getSaleMoney());
                outProfitSum = outProfitSum.add(data.getOutProfit());
                saleProfitSum = saleProfitSum.add(data.getSaleProfit());
                outAmountPrTranferMoneySum = outAmountPrTranferMoneySum.add(data.getOutAmountPrTranferMoney());
                outAmountPrCostMoneySum = outAmountPrCostMoneySum.add(data.getOutAmountPrCostMoney());
                receiveTareSum = receiveTareSum.add(data.getReceiveTare());
                totalAmountSum = totalAmountSum.add(data.getTotalAmount());
                totalMoneySum = totalMoneySum.add(data.getTotalMoney());

            }

            int count = list.size();
            List<TransferProfitByPosItemData> returnList = new ArrayList<TransferProfitByPosItemData>();
            for (int i = offset; i < offset + limit; i++) {
                if (i >= count) {
                    break;
                }
                TransferProfitByPosItemData data = list.get(i);
                if (i == offset) {
                    data.set("saleMoneySum", saleMoneySum);
                    data.set("outMoneySum", outMoneySum);
                    data.set("outCostSum", outCostSum);
                    data.set("outProfitSum", outProfitSum);
                    data.set("saleProfitSum", saleProfitSum);
                    data.set("outAmountSum", outAmountSum);
                    data.set("outAmountPrSum", outAmountPrSum);
                    data.set("basicQtySum", basicQtySum);
                    data.set("basicQtyPrSum", basicQtyPrSum);
                    data.set("outAmountPrTranferMoneySum", outAmountPrTranferMoneySum);
                    data.set("outAmountPrCostMoneySum", outAmountPrCostMoneySum);
                    data.set("receiveTareSum", receiveTareSum);
                    data.set("totalMoneySum", totalMoneySum);
                    data.set("totalAmountSum", totalAmountSum);
                    if (outMoneySum.compareTo(BigDecimal.ZERO) != 0) {
                        data.set("outProfitRateSum", outProfitSum.divide(outMoneySum, 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)).setScale(2).toString() + "%");
                    } else {
                        data.set("outProfitRateSum", "0.00%");
                    }

                    if (saleMoneySum.compareTo(BigDecimal.ZERO) != 0) {
                        data.set("saleProfitRateSum", saleProfitSum.divide(saleMoneySum, 4, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100)).setScale(2).toString() + "%");
                    } else {
                        data.set("saleProfitRateSum", "0.00%");
                    }
                }
                returnList.add(data);
            }

            if (StringUtils.isNotEmpty(exportUuid)) {

                String excelUrl = null;
                Element element = getElementFromCache(exportUuid);
                if (element != null) {
                    ExportCacheUtil exportCacheUtil = (ExportCacheUtil) element.getValue();
                    removeElementFromCache(exportUuid);
                    excelUrl = exportCacheUtil.exportBasicAutoGrid(list);
                }
                List<TransferProfitByPosItemData> returnDatas = new ArrayList<TransferProfitByPosItemData>();
                TransferProfitByPosItemData data = new TransferProfitByPosItemData();
                data.setCacheUrl(excelUrl);
                returnDatas.add(data);
                return new PagingLoadResultBean<TransferProfitByPosItemData>(returnDatas, returnDatas.size(), 0);
            }

            return new PagingLoadResultBean<TransferProfitByPosItemData>(returnList, count, offset);
        } catch (Exception e) {
            throw AppUtil.getException(e);
        }
    }*/



    //AMA-23175  性能优化-直调查询-门店商品汇总

   /* public PagingLoadResult<TransferProfitByPosItemDetailData> findTransferProfitByPosItemDetail(TransferProfitQuery queryData,
                                                                                                 Integer itemFlagNum, String exportUuid, PagingLoadConfig loadConfig) throws ErrorException {
        try {
            int offset = loadConfig.getOffset();
            int limit = loadConfig.getLimit();
            String sortField = null;
            String sortType = null;
            if (loadConfig.getSortInfo().size() > 0) {
                SortInfo sort = loadConfig.getSortInfo().get(0);
                sortField = sort.getSortField();
                sortType = sort.getSortDir().name();
            }

            List<Integer> itemNums = queryData.getItemNums();
            List<String> categoryCodes = queryData.getItemTypeNums();
            List<PosItemDTO> posItems = new ArrayList<PosItemDTO>();
            if (itemFlagNum != null && itemFlagNum > 0) {
                PosItemFlagRpc posItemFlagRpc = (PosItemFlagRpc)AppUtil.createCenterObject(PosItemFlagRpc.class);
                List<ItemFlagDetailDTO> itemDetails = posItemFlagRpc.findDetails(queryData.getSystemBookCode(), itemFlagNum);
                for (int i = 0; i < itemDetails.size(); i++) {
                    posItems.add(itemDetails.get(i).getPosItem());
                }
                if ((itemNums != null && itemNums.size() > 0)
                        || (categoryCodes != null && categoryCodes.size() > 0)) {
                    for (int i = 0; i < posItems.size(); i++) {
                        if (itemNums != null && itemNums.size() > 0) {
                            if (!itemNums.contains(posItems.get(i).getItemNum())) {
                                posItems.remove(i);
                                i--;
                                continue;
                            }
                        }
                        if (categoryCodes != null && categoryCodes.size() > 0) {
                            if (!categoryCodes.contains(posItems.get(i).getItemCategoryCode())) {
                                posItems.remove(i);
                                i--;
                                continue;
                            }
                        }
                    }
                }
                if (posItems.size() == 0) {
                    return new PagingLoadResultBean<TransferProfitByPosItemDetailData>(new ArrayList<TransferProfitByPosItemDetailData>(), 0, 0);
                }else {
                    itemNums = new ArrayList<Integer>();
                    for (int i = 0; i < posItems.size(); i++) {
                        itemNums.add(posItems.get(i).getItemNum());
                    }
                    queryData.setItemNums(itemNums);
                }
            }

            BranchRpc branchRpc = (BranchRpc) AppUtil.createCenterObject(BranchRpc.class);
            PosItemRpc posItemRpc = (PosItemRpc) AppUtil.createCenterObject(PosItemRpc.class);
            TransferOutOrderRpc transferOutOrderService = (TransferOutOrderRpc) AppUtil.createCenterObject(TransferOutOrderRpc.class);
            ItemMatrixRpc itemMatrixRpc = (ItemMatrixRpc) AppUtil.createCenterObject(ItemMatrixRpc.class);
            TransferInOrderRpc transferInOrderService = (TransferInOrderRpc) AppUtil.createCenterObject(TransferInOrderRpc.class);

            List<BranchDTO> branchs = branchRpc.findInCache(queryData.getSystemBookCode());
            List<TransferProfitByPosItemDetailData> list = new ArrayList<TransferProfitByPosItemDetailData>();

            com.nhsoft.module.transfer.export.dto.TransferProfitQuery query = (com.nhsoft.module.transfer.export.dto.TransferProfitQuery)
                    ObjectConverter.copy(queryData, new com.nhsoft.module.transfer.export.dto.TransferProfitQuery());

            //诚信志远 不统计特价商品
            query.setCategoryCodes(categoryCodes);
            List<Object[]> outObjects = transferOutOrderService.findDetails(query.getSystemBookCode(), query);

            List<ItemMatrixDTO> itemMatrixs = new ArrayList<ItemMatrixDTO>();

            // 总合计
            BigDecimal baseAmountSum = BigDecimal.ZERO;
            BigDecimal outAmountSum = BigDecimal.ZERO;
            BigDecimal baseAmountPrSum = BigDecimal.ZERO;
            BigDecimal outAmountPrSum = BigDecimal.ZERO;
            BigDecimal outAmountPrTranferMoneySum = BigDecimal.ZERO;
            BigDecimal outAmountPrCostMoneySum = BigDecimal.ZERO;
            BigDecimal outMoneySum = BigDecimal.ZERO;
            BigDecimal costUnitPriceSum = BigDecimal.ZERO;
            BigDecimal profitMoneySum = BigDecimal.ZERO;
            List<Integer> nums = new ArrayList<Integer>();
            for (int i = 0; i < outObjects.size(); i++) {
                Object[] objects = outObjects.get(i);

                TransferProfitByPosItemDetailData data = new TransferProfitByPosItemDetailData();
                data.setPosOrderNum((String) objects[0]);
                data.setPosOrderType("调出单");
                data.setSaleTime((Date) objects[1]);
                data.setOrderSeller((String) objects[2]);
                data.setOrderMaker((String) objects[3]);
                data.setOrderAuditor((String) objects[4]);
                data.setResponseBranchNum((Integer) objects[5]);
                data.setPosItemCode((String) objects[6]);
                data.setPosItemName((String) objects[7]);
                data.setSpec((String) objects[8]);
                data.setOutUnit((String) objects[9]);
                data.setOutAmount((BigDecimal) objects[10]);
                data.setOutUnitPrice((BigDecimal) objects[11]);
                data.setOutMoney((BigDecimal) objects[12]);
                data.setCostUnitPrice((BigDecimal) objects[13]);
                data.setProfitMoney(data.getOutMoney().subtract(data.getCostUnitPrice()));
                data.setRemark((String) objects[14]);
                data.setDistributionBranchNum((Integer) objects[15]);
                data.setBaseUnit((String) objects[16]);
                Date sendDate = (Date) objects[17];
                data.setBaseAmount(((BigDecimal) objects[18]).setScale(2, BigDecimal.ROUND_HALF_UP));
                if (data.getBaseAmount() == null || data.getBaseAmount().compareTo(BigDecimal.ZERO) == 0 || data.getOutMoney() == null) {
                }else {
                    data.setBasePrice(data.getOutMoney().divide(data.getBaseAmount(), 4, BigDecimal.ROUND_HALF_UP));
                }
                Integer itemMatrixNum = (Integer) objects[19];
                Integer itemNum = (Integer) objects[20];
                if (!nums.contains(itemNum)) {
                    nums.add(itemNum);
                }
                data.setItemNum(itemNum);
                data.setOutUnitPr((String) objects[22]);
                data.setBaseAmountPr(objects[23] == null?BigDecimal.ZERO:(BigDecimal) objects[23]);
                data.setOutAmountPr((objects[24] == null?BigDecimal.ZERO:(BigDecimal) objects[24]).setScale(2, BigDecimal.ROUND_HALF_UP));
                data.setOutAmountPrTranferMoney((objects[25] == null?BigDecimal.ZERO:(BigDecimal) objects[25]).setScale(2, BigDecimal.ROUND_HALF_UP));
                data.setOutAmountPrCostMoney((objects[26] == null?BigDecimal.ZERO:(BigDecimal) objects[26]).setScale(2, BigDecimal.ROUND_HALF_UP));
                data.setProductDate(objects[27] == null?null:(Date) objects[27]);
                if (itemMatrixNum != null && itemMatrixNum > 0) {

                    ItemMatrixDTO itemMatrix = AppUtil.getItemMatrix(itemMatrixs, itemNum, itemMatrixNum);
                    if (itemMatrix == null) {
                        itemMatrix = itemMatrixRpc.read(query.getSystemBookCode(), itemNum, itemMatrixNum);
                        if (itemMatrix != null) {
                            data.setPosItemName(data.getPosItemName().concat(AppUtil.getMatrixName(itemMatrix)));
                            itemMatrixs.add(itemMatrix);
                        }
                    } else {
                        data.setPosItemName(data.getPosItemName().concat(AppUtil.getMatrixName(itemMatrix)));
                    }
                }

                if (sendDate == null) {
                    data.setState("未配货");
                } else {
                    data.setState("已配货");
                }
                BranchDTO branch = AppUtil.getBranch(branchs, data.getDistributionBranchNum());
                if(branch == null){
                    continue;
                }
                data.setDistributionBranchNum(branch.getBranchNum());
                data.setDistributionBranchName(branch.getBranchName());

                branch = AppUtil.getBranch(branchs, data.getResponseBranchNum());
                if(branch == null){
                    continue;
                }
                data.setResponseBranchNum(branch.getBranchNum());
                data.setResponseBranchName(branch.getBranchName());
                list.add(data);

                baseAmountSum = baseAmountSum.add(data.getBaseAmount());
                outAmountSum = outAmountSum.add(data.getOutAmount());
                baseAmountPrSum = baseAmountPrSum.add(data.getBaseAmountPr());
                outAmountPrSum = outAmountPrSum.add(data.getOutAmountPr());
                outMoneySum = outMoneySum.add(data.getOutMoney());
                costUnitPriceSum = costUnitPriceSum.add(data.getCostUnitPrice());
                profitMoneySum = profitMoneySum.add(data.getProfitMoney());
                outAmountPrTranferMoneySum = outAmountPrTranferMoneySum.add(data.getOutAmountPrTranferMoney());
                outAmountPrCostMoneySum = outAmountPrCostMoneySum.add(data.getOutAmountPrCostMoney());
            }

            List<Object[]> inObjects = transferInOrderService.findDetails(query.getSystemBookCode(), query);
            for (int i = 0; i < inObjects.size(); i++) {
                Object[] objects = inObjects.get(i);

                TransferProfitByPosItemDetailData data = new TransferProfitByPosItemDetailData();
                data.setPosOrderNum((String) objects[0]);
                data.setPosOrderType("调入单");
                data.setSaleTime((Date) objects[1]);
                data.setOrderSeller((String) objects[2]);
                data.setOrderMaker((String) objects[3]);
                data.setOrderAuditor((String) objects[4]);
                data.setResponseBranchNum((Integer) objects[5]);
                data.setPosItemCode((String) objects[6]);
                data.setPosItemName((String) objects[7]);
                data.setSpec((String) objects[8]);
                data.setOutUnit((String) objects[9]);
                data.setOutAmount(((BigDecimal) objects[10]).negate());
                data.setOutUnitPrice((BigDecimal) objects[11]);
                data.setOutMoney(((BigDecimal) objects[12]).negate());
                data.setCostUnitPrice(((BigDecimal) objects[13]).negate());
                data.setProfitMoney(data.getOutMoney().subtract(data.getCostUnitPrice()));
                data.setRemark((String) objects[14]);
                data.setDistributionBranchNum((Integer) objects[15]);
                data.setBaseUnit((String) objects[16]);
                data.setBaseAmount(((BigDecimal) objects[17]).negate().setScale(2, BigDecimal.ROUND_HALF_UP));
                Integer itemMatrixNum = (Integer) objects[18];
                Integer itemNum = (Integer) objects[19];
                if (!nums.contains(itemNum)) {
                    nums.add(itemNum);
                }
                data.setItemNum(itemNum);
                data.setOutUnitPr((String) objects[20]);
                data.setOutAmountPr((objects[21] == null?BigDecimal.ZERO:(BigDecimal) objects[21]).negate());
                data.setBaseAmountPr((objects[22] == null?BigDecimal.ZERO:(BigDecimal) objects[22]).negate().setScale(2, BigDecimal.ROUND_HALF_UP));
                data.setOutAmountPrTranferMoney((objects[23] == null?BigDecimal.ZERO:(BigDecimal) objects[23]).negate().setScale(2, BigDecimal.ROUND_HALF_UP));
                data.setOutAmountPrCostMoney((objects[24] == null?BigDecimal.ZERO:(BigDecimal) objects[24]).negate().setScale(2, BigDecimal.ROUND_HALF_UP));
                data.setProductDate(objects[25] == null?null:(Date) objects[25]);
                if (itemMatrixNum != null && itemMatrixNum > 0) {

                    ItemMatrixDTO itemMatrix = AppUtil.getItemMatrix(itemMatrixs, itemNum, itemMatrixNum);
                    if (itemMatrix == null) {
                        itemMatrix = itemMatrixRpc.read(query.getSystemBookCode(), itemNum, itemMatrixNum);
                        if (itemMatrix != null) {
                            data.setPosItemName(data.getPosItemName().concat(AppUtil.getMatrixName(itemMatrix)));
                            itemMatrixs.add(itemMatrix);
                        }
                    } else {
                        data.setPosItemName(data.getPosItemName().concat(AppUtil.getMatrixName(itemMatrix)));

                    }
                }
                data.setState("未配货");

                BranchDTO branch = AppUtil.getBranch(branchs, data.getDistributionBranchNum());
                if(branch == null){
                    continue;
                }
                data.setDistributionBranchNum(branch.getBranchNum());
                data.setDistributionBranchName(branch.getBranchName());

                branch = AppUtil.getBranch(branchs, data.getResponseBranchNum());
                if(branch == null){
                    continue;
                }
                data.setResponseBranchNum(branch.getBranchNum());
                data.setResponseBranchName(branch.getBranchName());
                list.add(data);

                baseAmountSum = baseAmountSum.add(data.getBaseAmount());
                outAmountSum = outAmountSum.add(data.getOutAmount());
                baseAmountPrSum = baseAmountPrSum.add(data.getBaseAmountPr());
                outAmountPrSum = outAmountPrSum.add(data.getOutAmountPr());
                outMoneySum = outMoneySum.add(data.getOutMoney());
                costUnitPriceSum = costUnitPriceSum.add(data.getCostUnitPrice());
                profitMoneySum = profitMoneySum.add(data.getProfitMoney());
                outAmountPrTranferMoneySum = outAmountPrTranferMoneySum.add(data.getOutAmountPrTranferMoney());
                outAmountPrCostMoneySum = outAmountPrCostMoneySum.add(data.getOutAmountPrCostMoney());
            }
            List<PosItemDTO> items = posItemRpc.findByItemNumsInCache(queryData.getSystemBookCode(), nums);
            for (int i = 0; i < list.size(); i++) {
                TransferProfitByPosItemDetailData detailData = list.get(i);
                detailData.setId(AppUtil.getUUID());
                PosItemDTO posItem = AppUtil.getPosItem(detailData.getItemNum(), items);
                if (posItem != null) {
                    detailData.setPosItemData(PosItemConverter.createModelData(posItem, false));
                    detailData.setItemValidPeriod(posItem.getItemValidPeriod());
                    if (detailData.getProductDate() != null && posItem.getItemValidPeriod() != null && posItem.getItemValidPeriod() >= 0) {
                        detailData.setProductPassDate(DateUtil.addDay(detailData.getProductDate(), posItem.getItemValidPeriod()));
                    }
                    detailData.setDepartment(posItem.getItemDepartment());
                }
            }
            AppUtil.setProperty(list);
            if (sortField == null) {
                sortField = "posOrderNum";
                sortType = "ASC";
            }
            ComparatorAutoGridBaseData comparator = new ComparatorAutoGridBaseData(sortField, sortType);
            Collections.sort(list, comparator);

            int count = list.size();
            List<TransferProfitByPosItemDetailData> returnList = new ArrayList<TransferProfitByPosItemDetailData>();
            for (int i = offset; i < offset + limit; i++) {
                if (i >= count) {
                    break;
                }
                TransferProfitByPosItemDetailData data = list.get(i);
                if (i == offset) {
                    data.set("outMoneySum", outMoneySum);
                    data.set("costUnitPriceSum", costUnitPriceSum);
                    data.set("profitMoneySum", profitMoneySum);
                    data.set("outAmountSum", outAmountSum);
                    data.set("baseAmountSum", baseAmountSum);
                    data.set("outAmountPrSum", outAmountPrSum);
                    data.set("baseAmountPrSum", baseAmountPrSum);
                    data.set("outAmountPrCostMoneySum", outAmountPrCostMoneySum);
                    data.set("outAmountPrTranferMoneySum", outAmountPrTranferMoneySum);
                }
                returnList.add(data);
            }
            if(StringUtils.isNotEmpty(exportUuid)) {

                String excelUrl = null;
                Element element = getElementFromCache(exportUuid);
                if (element != null) {
                    ExportCacheUtil exportCacheUtil = (ExportCacheUtil) element.getValue();
                    removeElementFromCache(exportUuid);
                    excelUrl = exportCacheUtil.exportBasicAutoGrid(list);
                }
                List<TransferProfitByPosItemDetailData> returnDatas = new ArrayList<TransferProfitByPosItemDetailData>();
                TransferProfitByPosItemDetailData data = new TransferProfitByPosItemDetailData();
                data.setCacheUrl(excelUrl);
                returnDatas.add(data);
                return new PagingLoadResultBean<TransferProfitByPosItemDetailData>(returnDatas, returnDatas.size(), 0);
            }
            return new PagingLoadResultBean<TransferProfitByPosItemDetailData>(returnList, count, offset);
        } catch (Exception e) {
            throw AppUtil.getException(e);
        }
    }*/

}
